package Model.Engine

import Model.Config.Config
import Model.MapSites.{Hospital, PatientRoom, Room}
import Model.People.{Doctor, Patient, StaffPatient}
import Model.Statistics.{DailyData, History}

class BasicEngine(
                 config: Config,
                 hospital: Hospital
//                 history: History
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

  override def startNewDay: Unit = {
    val startHour = 8
    this.hour = startHour
    this.minute = 0
    this.dailyData = new DailyData
  }

  override def isNewDay: Boolean = {
    val endHour = 18
    this.hour >= endHour
  }

  override def nextStep: Unit = {
    val step = 30 // in minutes
    this.hour += (this.minute + step) / 60
    this.minute = (this.minute + step) % 60
  }

  override def addNewPatients: Unit = {
    val howMany = 10
    for (_ <- (1 to howMany).toList) {
      val patient = new Patient(1, false, 0, false, false, 0)
      hospital.addPatientToQueue(patient)
    }
  }

  override def manageStaff: Unit = {
    hospital.floors.foreach(floor => {
      floor.getStaffRooms.head.getStaffList.foreach(staff => {
        floor.getStaffRooms.head.goOut(staff)
        val random = scala.util.Random.nextInt(5)
        floor.getPatientRooms(random).goIn(staff)
      })
    })
  }

  override def getBackToStaffRoom: Unit = {
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getStaffList.foreach(staff => {
          patientRoom.goOut(staff)
          floor.getStaffRooms.head.goIn(staff)
        })
      })
    })
  }

  override def sendNewStaff: Unit = {
    val howMany = 0
    val doctor = new Doctor(1, false, 0, false)
    for (_ <- (0 until howMany).toList) {
      hospital.addStaff(doctor)
    }
  }

  override def sendInfectedStaffToQueue: Unit = {
    val infectedDoctors = hospital.doctors.filter(_.showsCovidSymptoms).toList
    hospital.doctors --= infectedDoctors
    val infectedNurses = hospital.nurses.filter(_.showsCovidSymptoms).toList
    hospital.nurses --= infectedNurses

    for (s <- (infectedDoctors ++ infectedNurses)) {
      hospital.addPatientToQueue(s.transformToPatient)
    }
  }

  private val findRoomForPatient: (Patient) => Option[PatientRoom] = (patient) => {
    hospital.floors.flatMap(_.getPatientRooms).find(_.canPutPatient)
  }

  override def putWaitingToBeds(): Unit = {
    while (this.hospital.getQueue.nonEmpty) {
      val patient = this.hospital.getQueue.dequeue()
      val potentialRoom = findRoomForPatient(patient)
      potentialRoom match {
        case Some(room) => room.putPatient(patient)
        case None => throw new IllegalStateException("Niestety, zabraklo miejsca dla nowych chorych")
      }
    }
  }

  private val calculatePropabilityOfInfection: (Config, PatientRoom) => Double = (config, room) => {
    (config.getP("probOfInfection") * (room.getPatientList.count(_.isInfected) + room.getStaffList.count(_.isInfected))).toDouble / 100
  }

  override def spreadInfection: Unit = {
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        val p = calculatePropabilityOfInfection(config, patientRoom)
        patientRoom.getStaffList.filter(!_.isInfected).foreach(staff => if(util.Random.nextDouble() < p) {
          dailyData.newCovidInfectionsStaff += 1
          staff.setInfection()
        })
        patientRoom.getPatientList.filter(!_.isInfected).foreach(staff => if(util.Random.nextDouble() < p) {
          dailyData.newCovidInfectionsPatients += 1
          staff.setInfection()
        })
      })
    })
  }

  override def killThoseBastards: Unit = {
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getPatientList.filter(_.isDead(config.getP("probOfDeath"))).foreach(patient =>{
          if (patient.getClass == classOf[StaffPatient] && patient.isInfected) dailyData.deadForCovidStaff += 1
          else if (patient.isInfected && patient.haveOtherDisease) dailyData.diedForCovidAndOtherCauses += 1
          else if (patient.isInfected) dailyData.diedForCovidPatients += 1
          else dailyData.diedForOtherCausesPatients += 1

          patientRoom.removePatient(patient.getID)
        })
      })
    })
  }

  override def curePatients: Unit = {
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getPatientList.filter(_.isRecovered(config.getP("dayOnWhichRecovered"))).foreach(patient => {
          if (patient.getClass == classOf[StaffPatient] && patient.isInfected) dailyData.curedFromCovidStaff += 1
          else if (patient.isInfected) dailyData.curedFromCovidPatients += 1
          else dailyData.curedFromOtherDiseases += 1
          // DO POPRAWY

          patientRoom.removePatient(patient.getID)
        })
      })
    })
  }

  override def revealCovidSymptoms: Unit = {
    var countCovidSymptoms:Int = 0
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getPatientList.filter(_.showsCovidSymptoms(config.getP("dayToShowSymptoms"))).foreach(patient =>{
          dailyData.showsCovidSymptoms += 1
          patientRoom.removePatient(patient.getID)
        })
      })
    })
  }

  override def getDailyData: DailyData = {
    if (dailyData == null) throw new IllegalStateException("Cannot return non-existing story' object")
    val toReturn = dailyData
    dailyData = null
    toReturn
  }
}
