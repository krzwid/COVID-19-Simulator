package Model.Engine

import Model.MapSites.Hospital
import Model.People.Patient
import Model.Statistics.History

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

  def spreadInfection: Int

  def killThoseBastards: Int

  def revealCovidSymptoms: Int

  def curePatients: Int

  // last, most important
  def writeHistory: Unit
}
