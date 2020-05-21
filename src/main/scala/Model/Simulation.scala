package Model

import Model.Config.{BasicConfig, Config}
import Model.Engine.{BasicEngine, Engine}
import Model.MapSites.Hospital
import Model.People.{Patient, Staff}
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

  def this(configPath: String) {
    this()
    this.configure(configPath)
  }

  def simulate: History = {
    if (config == null) {
      println("First configure simulation, then try to run it")
      return null
    }

    // HERE USE YOUR FUCKING ENGINE TO DO STUFF


    // return history of disease
    this.history
  }

  def configure(parametersSrc: String, patientsSrc: String): Unit = {
    this.config = new BasicConfig(parametersSrc, patientsSrc)

    // here create Hospital, Engine, History -- depended on config
    this.hospital = new Hospital
    this.history = new History
    this.engine = new BasicEngine(this.config, this.hospital, this.history)
  }

  def getData: String = {
//    this.config.getData
    null
  }

}
