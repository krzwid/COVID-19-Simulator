package Model

import Model.Config.{BasicConfig, Config}
import Model.Engine.{BasicEngine, Engine}
import Model.MapSites.Hospital
import Model.Statistics.History

class Simulation(var config: Config,
                 var hospital: Hospital,
                 var engine: Engine,
                 var history: History) {

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

  def configure(configPath: String): Unit = {
    this.config = new BasicConfig(configPath)
    this.config.load()

    // here create Hospital, Engine, History -- depended on config
    this.hospital = new Hospital
    this.history = new History
    this.engine = new BasicEngine(this.config, this.hospital, this.history)
  }

  def getData: String = {
    this.config.getData
  }

}
