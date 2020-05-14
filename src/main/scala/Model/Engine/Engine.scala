package Model.Engine

import Model.MapSites.Hospital
import Model.Statistics.History

trait Engine {





  def startNewDay: Unit

  def isNewDay: Boolean

  def nextStep: Unit

  def addNewPatients(hospital: Hospital): Unit

  // decide if and where to send staff
  def manageStaff(hospital: Hospital): Unit

  def removePatient: Unit

  def sendNewStaff(hospital: Hospital): Unit

  def sendInfectedStaffToQueue(hospital: Hospital): Unit

  def putWaitingToBeds(hospital: Hospital): Unit

  // last, most important
  def writeStory(history: History): Unit
}
