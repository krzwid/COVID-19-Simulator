package Model.Engine

trait Engine {





  def startNewDay: Unit

  def isNewDay: Boolean

  def nextStep: Unit

  def addNewPatients: Unit

  // decide if and where to send staff
  def manageStaff: Unit

  def removePatient: Unit

  def sendNewStaff: Unit

  def sendInfectedStaffToBed: Unit

  // last, most important
  def writeStory: Unit
}
