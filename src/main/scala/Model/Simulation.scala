package Model

import Model.Config.{BasicConfig, Config}
import Model.Engine.{BasicEngine, Engine}
import Model.MapSites.Hospital
import Model.People.{Doctor, Nurse, Patient, Staff}
import Model.Statistics.History

import scala.collection.mutable

class Simulation(var config: Config,
                 var engine: Engine
                 ) {

  // database with data of future patients stored as Strings
  private val PotentialPatientsDatabase: mutable.Queue[Array[String]] = new mutable.Queue[Array[String]]()

  private val history: History = new History
  private var hospital: Hospital = _

  private var maxPatientID = 0
  private var maxStaffID = 0
  var day:Int  = 0

  def this() {
    this(null:Config, null:Engine)
  }

  def this(parametersSrc: String, patientsSrc: String) {
    this()
    this.configure(parametersSrc, patientsSrc)
  }

  def simulate: History = {
    if (config == null) throw new Exception("Empty config -- it's unacceptable")

    // HERE USE YOUR FUCKING ENGINE TO DO STUFF
    this.fillHospital()

    for (_ <- (1 to this.config.getP("DurationInDays"))) {
      engine.startNewDay
      while (!engine.isNewDay) {
        engine.nextStep
        println(engine.getHour + ":" + engine.getMinute)
      }
      day = day + 1
      println("Dzień numer: " + this.day)

    }

    // return history of disease
    this.history
  }

  def configure(parametersSrc: String, patientsSrc: String): Unit = {
    this.config = new BasicConfig(parametersSrc, patientsSrc)

    // parse patients database
    this.config.getPatientsData.filter(_.length == 6).foreach(this.PotentialPatientsDatabase.enqueue)

    this.hospital = new Hospital(this.config.getParameters.getOrElse("numberOfFloors", 0))
    this.engine = new BasicEngine(this.config, this.hospital, this.history)
  }

  private def getNewPatient: Patient = {
    if (this.PotentialPatientsDatabase.nonEmpty) new Patient(this.PotentialPatientsDatabase.dequeue())
    else throw new Exception("Set of random patients has been exhausted")
  }

  private def fillHospital(): Unit = {
    for (_ <- (0 to this.config.getP("startingPatientsCount")).toList)
      this.hospital.addPatientToQueue(getNewPatient)

    for (_ <- (0 until this.config.getP("startingDoctorsCount")).toList) {
      this.hospital.doctors.append(new Doctor(maxStaffID))
      this.maxStaffID += 1
    }
    for (_ <- (0 until this.config.getP("startingNursesCount")).toList) {
      this.hospital.nurses.append(new Nurse(maxStaffID))
      this.maxStaffID += 1
    }
  }
}
