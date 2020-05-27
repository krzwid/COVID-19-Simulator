package Model.People

import Model.MapSites.Room

class Patient(
             private val ID: Int,
             private var infected: Boolean,
             private var daysSinceInfected: Int,
             private var covidSymptoms: Boolean,
             private var otherDisease: Boolean,
             private var otherDiseaseSince: Int
             ) extends Person {

  def this(id: Int) {
    this(id, infected = true, daysSinceInfected = 0, covidSymptoms = true, otherDisease = false, otherDiseaseSince = 0)
  }

  def this(a: Array[String]) = {
    this(a(0).toInt, a(1).toBoolean, a(2).toInt, a(3).toBoolean, a(4).toBoolean, a(5).toInt)
}

  private var daysSinceTreated: scala.collection.mutable.Map[Class[_], Int] = scala.collection.mutable.Map(
    classOf[Doctor] -> 0,
    classOf[Nurse] -> 0
  )

  private var room: Room = _

  // implemented interface
  override def goTo(room: Room): Unit = {
    throw new UnsupportedOperationException("Patient cannot move anywhere")
  }

  override def revealCovidSymptoms(): Unit = {
    this.covidSymptoms = true
  }

  override def setInfection(infected: Boolean): Unit = {
    this.infected = true
  }

  override def isInfected: Boolean = {
    this.infected
  }

  override def getdaysSinceInfected: Int = {
    this.daysSinceInfected
  }

  override def getID: Int = {
    this.ID
  }

  override def getRoom: Room = {
    this.room
  }

  // new functions
  def isDead(par: Int): Boolean = {
    val p = par.toDouble / 100
    this.infected && scala.util.Random.nextDouble() < p
  }

  def isRecovered(limit : Int): Boolean = {
    this.getdaysSinceInfected > limit
  }

  def endOtherDisease(): Unit = {
    this.otherDisease = false
    this.otherDiseaseSince = 0
  }

  def incrementDaysCounters(): Unit = {
    if (this.isInfected) this.daysSinceInfected += 1
    if (this.otherDisease) this.otherDiseaseSince += 1
  }

  def revealCovidSymptoms(dayToShowSymptoms: Int): Boolean = {
    if(this.daysSinceInfected >= dayToShowSymptoms) {
      this.covidSymptoms = true
      return true
    }
    false
  }

  //getters
  def getCovidSymptoms: Boolean = {
    this.covidSymptoms
  }

  def hasOtherDisease: Boolean = {
    this.otherDisease
  }

  def getOtherDiseaseSince: Int = {
    this.otherDiseaseSince
  }

}
