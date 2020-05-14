package Model.Engine

import Model.MapSites.Hospital
import Model.People.{Doctor, Patient}
import Model.Statistics.{DailyData, History}

class BasicEngine extends Engine {
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

  override def addNewPatients(hospital: Hospital): Unit = {
    val howMany = 10
    for (i <- (1 to howMany).toList) {
      hospital.addPatient(new Patient(1, false, 0, false, false, 0))
    }
  }

  override def manageStaff(hospital: Hospital): Unit = {
    // do something
  }

  override def removePatient: Unit = ???

  override def sendNewStaff(hospital: Hospital): Unit = {
    val howMany = 0
    for (i <- (0 until howMany).toList) {
      hospital.addStaff(new Doctor(1, false, 0, false))
    }
  }

  override def sendInfectedStaffToQueue(hospital: Hospital): Unit = {
    val getInfected = hospital.getStaff.filter(_.showsCovidSymptoms).toList
    hospital.getStaff --= getInfected

    for (s <- getInfected) {
      hospital.addPatientToQueue(s.transformToPatient)
    }
  }

  override def putWaitingToBeds(hospital: Hospital): Unit = {
    for (p <- hospital.getQueue) {
      if (hospital.freeBeds > 0) {
        println("tu znajdz pacjentowi lozko")
      } else {
        println("niestety, zabraklo miejsca")
      }
    }
  }

  override def writeStory(history: History): Unit = {
    history.addDay(this.dailyData)
    this.dailyData = null
  }
}
