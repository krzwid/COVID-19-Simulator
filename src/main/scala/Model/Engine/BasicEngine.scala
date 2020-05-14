package Model.Engine

class BasicEngine extends Engine {
  private var hour: Int = 0
  private var minute: Int = 0


  override def startNewDay: Unit = {
    val startHour = 8
    this.hour = startHour
    this.minute = 0
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

  override def addNewPatients: Unit = ???

  override def manageStaff: Unit = ???

  override def removePatient: Unit = ???

  override def sendNewStaff: Unit = ???

  override def sendInfectedStaffToBed: Unit = ???

  override def writeStory: Unit = ???
}
