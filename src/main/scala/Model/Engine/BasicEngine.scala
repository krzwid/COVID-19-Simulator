package Model.Engine

import Model.Config.Config
import Model.MapSites.{Floor, Hospital, PatientRoom, Room}
import Model.People.{Doctor, Patient, Person, Staff, StaffPatient}
import Model.Statistics.DailyData

class BasicEngine(
                 config: Config,
                 hospital: Hospital
                 ) extends Engine {
  private var hour: Int = 0
  private var minute: Int = 0

  private var dailyData: DailyData = _

  override def getHour: Int = {
    this.hour
  }

  override def getMinute: Int = {
    this.minute
  }

  override def startNewDay(): Unit = {
    val startHour = 8
    this.hour = startHour
    this.minute = 0
    this.dailyData = new DailyData
  }

  override def isNewDay: Boolean = {
    this.hour >= this.config.getP("endHour")
  }

  override def nextStep(): Unit = {
    val step = 30 // in minutes
    this.hour += (this.minute + step) / 60
    this.minute = (this.minute + step) % 60
  }

  // ---

  override def countPatients(): Unit = {
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getPatientList.foreach(_ => this.dailyData.patientsInHospital += 1)
      })
    })
  }

  // ---

  private val howToSendStaffToFloors: () => Unit = () => {
    for (i <- this.hospital.doctors.indices.toList) {
      this.hospital.floors(i % this.hospital.floors.length).addStaffToStaffRoom( this.hospital.doctors(i) )
    }
    this.hospital.doctors.clear()

    for (i <- this.hospital.nurses.indices.toList) {
      this.hospital.floors(i % this.hospital.floors.length).addStaffToStaffRoom( this.hospital.nurses(i) )
    }
    this.hospital.nurses.clear()
  }

  override def sendStaffToFloors(): Unit = {
    howToSendStaffToFloors()
  }

  // ---

  private val findNextRoomForStaff: (Staff, Floor) => Room = (_, floor) => {
    floor.getPatientRooms(scala.util.Random.nextInt( this.config.getP("howManyRoomsOnFloor") ))
  }

  override def manageStaff(): Unit = {
    hospital.floors.foreach(floor => {
      floor.getStaffRooms.head.getStaffList.foreach(staff => {
        floor.getStaffRooms.head.goOut(staff)
        findNextRoomForStaff(staff, floor).goIn(staff)
      })
    })
  }

  // ---

  override def backToStaffRoom(): Unit = {
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getStaffList.foreach(staff => {
          patientRoom.goOut(staff)
          floor.getStaffRooms.head.goIn(staff)
        })
      })
    })
  }

  // ---

  override def sendNewStaff(): Unit = {
    val lackingStaff = this.config.getP("minimalStaffLevel") - (this.hospital.nurses.length + this.hospital.doctors.length)
    if (lackingStaff > 0) {
      val doctor = new Doctor(1, false, 0, false)
      for (_ <- (0 until lackingStaff).toList) {
        hospital.addStaff(doctor)
      }
    }
  }

  // ---

  override def sendInfectedStaffToQueue(): Unit = {
    val infectedDoctors = hospital.doctors.filter(_.showsCovidSymptoms).toList
    hospital.doctors --= infectedDoctors
    val infectedNurses = hospital.nurses.filter(_.showsCovidSymptoms).toList
    hospital.nurses --= infectedNurses

    for (s <- infectedDoctors ++ infectedNurses) {
      hospital.addPatientToQueue(s.transformToPatient)
    }
  }

  // ---

  private val findRoomForPatient: Patient => Option[PatientRoom] = _ => {
    hospital.floors.flatMap(_.getPatientRooms).find(_.canPutPatient)
  }

  override def putNewPatientsToBeds(): Unit = {
    while (this.hospital.getQueue.nonEmpty) {
      val patient = this.hospital.getQueue.dequeue()
      val potentialRoom = findRoomForPatient(patient)
      potentialRoom match {
        case Some(room) => room.putPatient(patient)
        case None => throw new IllegalStateException("Niestety, zabraklo miejsca dla nowych chorych")
      }
    }
  }

  // ---

  private val calculateProbabilityOfInfection: (Config, Room, Person) => Double = (config, room, _) => {
    (config.getP("probOfInfection") * room.getAllPeople.count(_.isInfected)).toDouble / 100
  }

  override def spreadInfection(): Unit = {
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => patientRoom.getAllPeople
        .filter( !_.isInfected )
        .filter( scala.util.Random.nextDouble() < calculateProbabilityOfInfection(config, patientRoom, _) ).foreach(person => {
          person match {
            case _: Staff => dailyData.newCovidInfectionsStaff += 1
            case _: Patient => dailyData.newCovidInfectionsPatients += 1
          }
          person.setInfection(true)
      }))
    })
  }

  // ---

  private val calculateProbabilityOfDeath: Patient => Double = _ => {
    this.config.getP("probOfDeath").toDouble / 100
  }

  override def killThoseBastards(): Unit = {
    // kill unlucky losers
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getPatientList.filter( scala.util.Random.nextDouble() < calculateProbabilityOfDeath(_) ).foreach(patient => {
          if (patient.isInfected) {
            if (patient.getClass == classOf[StaffPatient]) dailyData.deadForCovidStaff += 1
            else if (patient.haveOtherDisease) dailyData.diedForCovidAndOtherCauses += 1
            else dailyData.diedForCovidPatients += 1
          }
          else dailyData.diedForOtherCausesPatients += 1

          patientRoom.removePatient(patient.getID)
        })
      })
    })

    // increment counter for lucky survivors
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getPatientList.foreach(_.incrementDaysCounters())
      })
    })
  }

  // ---

  private val calculateProbabilityOfRecoveryFromCovid: Person => Double = person => {
    if (person.infectedSince > this.config.getP("dayOnWhichRecovered")) 1.0
    else 0.0
  }

  private val calculateProbabilityOfRecoveryFromOtherDiseases: Patient => Double = patient => {
    if (patient.getOtherDiseaseSince > this.config.getP("dayOnWhichRecovered")) 1.0
    else 0.0
  }

  override def curePatients(): Unit = {
    // doctors and nurses which don't show symptoms
    (this.hospital.doctors ++ this.hospital.nurses)
      .filter(person => {person.isInfected && scala.util.Random.nextDouble() < calculateProbabilityOfRecoveryFromCovid(person)})
      .foreach(person => {
        dailyData.curedFromCovidStaff += 1
        person.setInfection(false)
      })

    // cure from other diseases
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getPatientList
          .filter(patient => { patient.haveOtherDisease && scala.util.Random.nextDouble() < calculateProbabilityOfRecoveryFromOtherDiseases(patient) })
          .foreach(patient => {
            dailyData.curedFromOtherDiseases += 1
            patient.endOtherDisease()
        })
      })
    })

    // cure from Covid
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getPatientList
          .filter(patient => { patient.haveOtherDisease && scala.util.Random.nextDouble() < calculateProbabilityOfRecoveryFromCovid(patient) })
          .foreach(patient => {
            if (patient.getClass == classOf[StaffPatient]) dailyData.curedFromCovidStaff += 1
            else dailyData.curedFromCovidPatients += 1
        })
      })
    })

    // remove cured patients from hospital
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getPatientList.filter(patient => {!patient.getCovidSymptoms && !patient.haveOtherDisease})
          .foreach(patient => {
            if (patient.getClass == classOf[StaffPatient])
              this.hospital.addStaff(patient.asInstanceOf[StaffPatient].transformToStaff())
            patientRoom.removePatient(patient.getID)
          })
      })
    })
  }

  // ---

  private val calculateProbabilityOfShowingSymptoms: Person => Double = person => {
//    if (person.isInfected) this.config.getP("probabilityOfShowingSymptoms").toDouble / 100
//    else 0.0
    if (person.infectedSince > this.config.getP("dayToShowSymptoms")) 1.0
    else 0.0
  }

  override def revealCovidSymptoms(): Unit = {
    (hospital.doctors ++ hospital.nurses)
      .filter( _.isInfected )
      .filter( scala.util.Random.nextDouble() < calculateProbabilityOfShowingSymptoms(_) ).foreach(staff => {
        dailyData.showsCovidSymptoms += 1
        staff.revealCovidSymptoms()
    })

    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getPatientList
          .filter( _.isInfected )
          .filter( scala.util.Random.nextDouble() < calculateProbabilityOfShowingSymptoms(_) ).foreach(patient => {
            dailyData.showsCovidSymptoms += 1
            patient.revealCovidSymptoms()
        })
      })
    })
  }

  // ---

  override def getDailyData: DailyData = {
    if (dailyData == null) throw new IllegalStateException("Cannot return non-existing story' object")
    val toReturn = dailyData
    dailyData = null
    toReturn
  }
}
