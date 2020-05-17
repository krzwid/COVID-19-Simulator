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

  private var dailyData: DailyData = null

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
    for (i <- (1 to howMany).toList) {
      hospital.addPatientToQueue(new Patient(1, false, 0, false, false, 0))
    }
  }

  override def manageStaff: Unit = {
    // do something
  }

  override def removePatient: Unit = ???

  override def sendNewStaff: Unit = {
    val howMany = 0
    for (i <- (0 until howMany).toList) {
      hospital.addStaff(new Doctor(1, false, 0, false))
    }
  }

  override def sendInfectedStaffToQueue: Unit = {
    val getInfected = hospital.getStaff.filter(_.showsCovidSymptoms).toList
    hospital.getStaff --= getInfected

    for (s <- getInfected) {
      hospital.addPatientToQueue(s.transformToPatient)
    }
  }

  override def putWaitingToBeds: Unit = {
    while (hospital.getQueue.nonEmpty) {
      val p = hospital.getQueue.dequeue
      if (hospital.freeBeds > 0) {
        println("tu znajdz pacjentowi lozko")
      } else {
        println("niestety, zabraklo miejsca")
      }
    }
  }

  override def writeStory: Unit = {
    history.addDay(this.dailyData)
    this.dailyData = null
  }
}
