package Model.Engine

import Model.Config.Config
import Model.MapSites.Hospital
import Model.People.{Doctor, Patient, StaffPatient}
import Model.Statistics.{DailyData, History}

class BasicEngine(
                 config: Config,
                 hospital: Hospital,
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

  override def putWaitingToBeds(): Unit = {
    while (this.hospital.getQueue.nonEmpty) {
      val patient = this.hospital.getQueue.dequeue()
      val potentialRoom = hospital.floors.flatMap(_.getPatientRooms).find(_.canPutPatient)
      potentialRoom match {
        case Some(room) => room.putPatient(patient)
        case None => throw new IllegalStateException("Niestety, zabraklo miejsca dla nowych chorych")
      }
    }
  }

  override def spreadInfection: Int = {
    var countNewInfections:Int = 0
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        val p = (config.getP("probOfInfection") * (patientRoom.getPatientList.count(_.isInfected) + patientRoom.getStaffList.count(_.isInfected))).toDouble / 100
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
    countNewInfections
  }

  override def killThoseBastards: Int = {
    var countDead:Int = 0
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getPatientList.filter(_.isDead(config.getP("probOfDeath"))).foreach(patient =>{
//          if (patient.getClass == classOf[StaffPatient])
//          patient match {
//            case staffPatient: StaffPatient => dailyData.
//          }
          patientRoom.removePatient(patient.getID)
        })
      })
    })
    countDead
  }

  override def curePatients: Int = {
    var countRecovered:Int = 0
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getPatientList.filter(_.isRecovered(config.getP("dayOnWhichRecovered"))).foreach(patient => {
          countRecovered +=1
          patientRoom.removePatient(patient.getID)
        })
      })
    })
    countRecovered
  }

  override def revealCovidSymptoms: Int = {
    var countCovidSymptoms:Int = 0
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getPatientList.filter(_.showsCovidSymptoms(config.getP("dayToShowSymptoms"))).foreach(patient =>{
          countCovidSymptoms +=1
          patientRoom.removePatient(patient.getID)
        })
      })
    })
    countCovidSymptoms
  }

  override def getDailyData: DailyData = {
    if (dailyData == null) throw new IllegalStateException("Cannot return non-existing story' object")
    val toReturn = dailyData
    dailyData = null
    toReturn
  }
}
