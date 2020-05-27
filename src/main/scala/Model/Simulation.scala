package Model

import java.util.Objects

import Model.Config.{BasicConfig, Config}
import Model.Engine.{BasicEngine, Engine}
import Model.MapSites.Hospital
import Model.People.{Doctor, Nurse, Patient}
import Model.Statistics.History

import scala.collection.mutable

class Simulation(var config: Config,
                 var engine: Engine
                 ) {

  // database with data of future patients stored as Strings
  private val PotentialPatientsDatabase: mutable.Queue[Array[String]] = new mutable.Queue[Array[String]]()

  private val history: History = new History
  private var hospital: Hospital = _

  private var maxStaffID = 0
  var day:Int  = 0

  def this() {
    this(null:Config, null:Engine)
  }

  def this(parametersSrc: String, patientsSrc: String) {
    this()
    this.configure(parametersSrc, patientsSrc)
  }

  //the most main function of simulation
  def simulate: History = {
    Objects.requireNonNull(config, "Null configuration")

    this.fillHospital()

    for (_ <- (1 to this.config.getParametersInt("DurationInDays"))) {
      day = day + 1
      println("Dzień numer: " + this.day)

      engine.startNewDay()

      for(_ <- (1 to this.config.getParameters("newPatientsEachDay"))) {
        hospital.addPatientToQueue(this.getNewPatient)
      }

      engine.putNewPatientsToBeds()
      engine.countPatients()
      engine.sendStaffToFloors()

      while (!engine.isNewDay) {
        engine.manageStaff()
        engine.spreadInfection()
        engine.backToStaffRoom()

        engine.nextStep()
      }

      engine.revealCovidSymptoms()
      engine.sendInfectedStaffToQueue()
      engine.killPatients()
      engine.curePatients()

      val dailyData = engine.getDailyData
      println("zginelo: " + dailyData.deadForCovidAndOtherCauses)
      history.addDay(dailyData)
//      println("Pacjentowe nowe infekcje:" + dailyData.newCovidInfectionsPatients)
    }
    // return history of disease
    this.history
  }

  def configure(parametersSrc: String, patientsSrc: String): Unit = {
    this.config = new BasicConfig(parametersSrc, patientsSrc)

    // parse patients database
    this.config.getPatientsData.filter(_.length == 6).foreach(this.PotentialPatientsDatabase.enqueue)

    this.hospital = new Hospital(this.config.getParametersInt("numberOfFloors"), this.config.getParametersInt("howManyRoomsOnFloor"), this.config.getParametersInt("patientRoomCapacity"))
    this.engine = new BasicEngine(this.config, this.hospital)
  }

  private def fillHospital(): Unit = {
    for (_ <- (0 to this.config.getParametersInt("startingPatientsCount")).toList)
      this.hospital.addPatientToQueue(getNewPatient)

    for (_ <- (0 until this.config.getParametersInt("startingDoctorsCount")).toList) {
      this.hospital.doctors.append( new Doctor(maxStaffID) )
      this.maxStaffID += 1
    }

    for (_ <- (0 until this.config.getParametersInt("startingNursesCount")).toList) {
      this.hospital.nurses.append( new Nurse(maxStaffID) )
      this.maxStaffID += 1
    }
  }

  //getters
  private def getNewPatient: Patient = {
    if (this.PotentialPatientsDatabase.nonEmpty) new Patient(this.PotentialPatientsDatabase.dequeue())
    else throw new IndexOutOfBoundsException("Set of random patients has been exhausted")
  }
}
