package Model

import java.util.Objects

import Model.Config.{BasicConfig, Config}
import Model.Engine.{BasicEngine, Engine}
import Model.MapSites.{Hospital, PatientRoom, StaffRoom}
import Model.People.{Doctor, Nurse, Patient, Staff}
import Model.Statistics.{DailyData, History}

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

      for(_ <- (1 to 100)) {
        engine.putWaitingToBeds()
      }


      var countNewInfections: Int = 0
      while (!engine.isNewDay) {
        println(engine.getHour + ":" + engine.getMinute)
        // wyslij personel do odpowiednich pomieszczen
        // -- mozliwe kilka wersji
        engine.manageStaff
        println("Staff managed")

        // okresl kto sie zaraza
        // -- mozliwe kilka wersji
        countNewInfections += engine.spreadInfection
        println("Nowe infekcje:" + countNewInfections)
        //powrot staffu do kanciapy
        engine.getBackToStaffRoom
        println("Staff returned to StaffRoom")
        engine.nextStep
      }
      day = day + 1
      println("Dzień numer: " + this.day)


      // okresl kto umarl           - do kostnicy
      // -- mozliwe kilka wersji
      val countNewDeaths = engine.killThoseBastards
      println("Zginelo:" + countNewDeaths)

      // okresl kto dostal objawow  - (jesli personel, to do kolejki dla chorych)
      // -- mozliwe kilka wersji
      val countNewDiagnosed = engine.revealCovidSymptoms

      // okresl kto wyzdrowial      - wyrzuc ze szpitala (ewentualnie przywroc personel)
      // -- mozliwe kilka wersji
      val countCured = engine.curePatients

      // zapisz historie
      // -- jedna jedyna i niezmienna
      val dailyData = engine.getDailyData
      history.addDay(dailyData)
      // print to console
      // ................
    }

    // return history of disease
    this.history
  }

  def configure(parametersSrc: String, patientsSrc: String): Unit = {
    this.config = new BasicConfig(parametersSrc, patientsSrc)

    // parse patients database
    this.config.getPatientsData.filter(_.length == 6).foreach(this.PotentialPatientsDatabase.enqueue)

    this.hospital = new Hospital(this.config.getParameters.getOrElse("numberOfFloors", 0))
    this.hospital.floors.foreach(floor => {
      for ( _ <- (1 to 5)) {
        floor.addPatientRoom(new PatientRoom(6))
      }
      floor.addStaffRoom(new StaffRoom)
    })
    this.engine = new BasicEngine(this.config, this.hospital)
  }

  private def getNewPatient: Patient = {
    if (this.PotentialPatientsDatabase.nonEmpty) new Patient(this.PotentialPatientsDatabase.dequeue())
    else throw new IndexOutOfBoundsException("Set of random patients has been exhausted")
  }

  private def fillHospital(): Unit = {
    for (_ <- (0 to this.config.getP("startingPatientsCount")).toList)
      this.hospital.addPatientToQueue(getNewPatient)

    for (i <- (0 until this.config.getP("startingDoctorsCount")).toList) {
      val doctor = new Doctor(maxStaffID)
      this.hospital.doctors.append(doctor)
      this.maxStaffID += 1
      this.hospital.floors(i%5).addStaffToStaffRoom(doctor)
    }
    for (i <- (0 until this.config.getP("startingNursesCount")).toList) {
      val nurse = new Nurse(maxStaffID)
      this.hospital.nurses.append(nurse)
      this.hospital.floors(i%5).addStaffToStaffRoom(nurse)
      this.maxStaffID += 1
    }
  }
}
