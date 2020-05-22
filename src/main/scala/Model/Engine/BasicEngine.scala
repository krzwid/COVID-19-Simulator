package Model.Engine

import Model.Config.Config
import Model.MapSites.Hospital
import Model.People.{Doctor, Patient}
import Model.Statistics.{DailyData, History}

class BasicEngine(
                 config: Config,
                 hospital: Hospital,
                 history: History
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
        val random = scala.util.Random.nextInt(5)
        floor.getPatientRooms(random).goIn(staff)
      })

    })
  }

  override def getBackToStaffRoom: Unit = {
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getStaffList.foreach(staff =>{
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

  override def putWaitingToBeds(patient: Patient): Unit = {
      val potentialRoom = hospital.floors.flatMap(_.getPatientRooms).find(_.canPutPatient)
      potentialRoom match {
        case Some(room) => room.putPatient(patient)
        case None => println("niestety, zabraklo miejsca")
    }
  }

  override def spreadInfection: Int = {
    var countNewInfections:Int = 0
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getPatientList.foreach(patient =>{
          patient.isRecovered(config.getP("probOfInfection"))
          countNewInfections +=1
        })
      })
    })
    countNewInfections
  }

  override def killThoseBastards: Int = {
    var countDead:Int = 0
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getPatientList.foreach(patient =>{
          patient.isDead(config.getP("probOfDeath"))
          countDead +=1
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
        patientRoom.getPatientList.foreach(patient =>{
          patient.isRecovered(config.getP("dayOnWhichRecovered"))
          countRecovered +=1
          patientRoom.removePatient(patient.getID)
        })
      })
    })
    countRecovered
  }
  
  override def writeHistory: Unit = {
    history.addDay(this.dailyData)
    this.dailyData = null
  }

  override def revealCovidSymptoms: Int = {
    var countCovidSymptoms:Int = 0
    hospital.floors.foreach(floor => {
      floor.getPatientRooms.foreach(patientRoom => {
        patientRoom.getPatientList.foreach(patient =>{
          patient.showsCovidSymptoms(config.getP("dayToShowSymptoms"))
          countCovidSymptoms +=1
          patientRoom.removePatient(patient.getID)
        })
      })
    })
    countCovidSymptoms
  }
}
