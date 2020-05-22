package Model.Engine

import Model.MapSites.Hospital
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

  def removePatient: Unit

  def sendNewStaff: Unit

  def sendInfectedStaffToQueue: Unit

  def putWaitingToBeds: Unit

  // last, most important
  def writeStory: Unit
}
