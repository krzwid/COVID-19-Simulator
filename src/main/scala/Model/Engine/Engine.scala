package Model.Engine

import Model.MapSites.Hospital
import Model.People.Patient
import Model.Statistics.{DailyData, History}

trait Engine {

  def getHour: Int
  def getMinute: Int
  def startNewDay: Unit
  def isNewDay: Boolean
  def nextStep: Unit

  def addNewPatients: Unit

  // decide if and where to send staff
  def manageStaff: Unit

  def getBackToStaffRoom: Unit

  def sendNewStaff: Unit

  def sendInfectedStaffToQueue: Unit

  def putWaitingToBeds(): Unit

  def spreadInfection: Unit

  def killThoseBastards: Unit

  def revealCovidSymptoms: Unit

  def curePatients: Unit

  // last, most important
  def getDailyData: DailyData
}
