package Model

import Model.Config.{BasicConfig, Config}
import Model.Engine.{BasicEngine, Engine}
import Model.MapSites.Hospital
import Model.People.{Doctor, Nurse, Patient, Staff}
import Model.Statistics.History

class Simulation(var config: Config,
                 var hospital: Hospital,
                 var engine: Engine,
                 var history: History) {

  private var PotentialPatientsDatabase: List[Patient] = null
  private var maxPatientID = 0
  private var maxStaffID = 0

  def this() {
    this(null, null, null, null)
  }

  def this(parametersSrc: String, patientsSrc: String) {
    this()
    this.configure(parametersSrc, patientsSrc)
  }

  def simulate: History = {
    if (config == null) throw new Exception("Empty config -- it's unacceptable")

    // HERE USE YOUR FUCKING ENGINE TO DO STUFF
    

    // return history of disease
    this.history
  }

  def configure(parametersSrc: String, patientsSrc: String): Unit = {
    this.config = new BasicConfig(parametersSrc, patientsSrc)
    // parse patients database
    this.PotentialPatientsDatabase = this.config.getPatientsData.filter(a => a.length == 6).map(a => new Patient(
      a(0).toInt, a(1).toBoolean, a(2).toInt, a(3).toBoolean, a(4).toBoolean, a(5).toInt
    ))

    // here create Hospital, Engine, History -- depended on config
    this.hospital = new Hospital(10, this.PotentialPatientsDatabase, 100)
    for (_ <- (0 until 50).toList) {
      this.hospital.doctors.append(new Doctor(maxStaffID, false, 0, false))
      this.maxStaffID += 1
    }
    for (_ <- (0 until 100).toList) {
      this.hospital.nurses.append(new Nurse(maxStaffID, false, 0, false))
      this.maxStaffID += 1
    }

    this.history = new History
    this.engine = new BasicEngine(this.config, this.hospital, this.history)
  }

}
