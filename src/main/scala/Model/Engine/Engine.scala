package Model.Engine

import Model.Statistics.{DailyData, History}

trait Engine {

  def getHour: Int
  def getMinute: Int
  def startNewDay(): Unit
  def isNewDay: Boolean
  def nextStep(): Unit

//  def addNewPatients(howMany: Int): Unit

  def countPatients(): Unit

  // decide if and where to send staff
  def sendStaffToFloors(): Unit

  def manageStaff(): Unit

  def backToStaffRoom(): Unit

  def sendNewStaff(): Unit

  def sendInfectedStaffToQueue(): Unit

  def putNewPatientsToBeds(): Unit

  def spreadInfection(): Unit

  def killThoseBastards(): Unit

  def revealCovidSymptoms(): Unit

  def curePatients(): Unit

  // last, most important
  def getDailyData: DailyData
}
