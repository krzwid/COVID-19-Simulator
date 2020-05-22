package Model

import java.util.Objects

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
    Objects.requireNonNull(config, "Empty configuration")

    // HERE USE YOUR FUCKING ENGINE TO DO STUFF
    this.fillHospital()

    for (_ <- (1 to this.config.getP("DurationInDays"))) {
      engine.startNewDay

      // porozsylaj pacjentow z kolejki do lozek
      // -- mozliwe kilka wersji
      engine.putWaitingToBeds

      while (!engine.isNewDay) {
        println(engine.getHour + ":" + engine.getMinute)
        // wyslij personel do odpowiednich pomieszczen
        // -- mozliwe kilka wersji
        engine.manageStaff

        // okresl kto sie zaraza
        // -- mozliwe kilka wersji
        val countNewInfections = engine.spreadInfection

        engine.nextStep
      }
      day = day + 1
      println("Dzień numer: " + this.day)

      // okresl kto umarl           - do kostnicy
      // -- mozliwe kilka wersji
      val countNewDeaths = engine.killThoseBastards

      // okresl kto dostal objawow  - (jesli personel, to do kolejki dla chorych)
      // -- mozliwe kilka wersji
      val countNewDiagnosed = engine.revealCovidSymptoms

      // okresl kto wyzdrowial      - wyrzuc ze szpitala (ewentualnie przywroc personel)
      // -- mozliwe kilka wersji
      val countCured = engine.curePatients

      // zapisz historie
      // -- jedna jedyna i niezmienna
      engine.writeHistory
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
    else throw new IndexOutOfBoundsException("Set of random patients has been exhausted")
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
