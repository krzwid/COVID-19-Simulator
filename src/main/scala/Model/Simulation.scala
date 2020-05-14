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

  def simulate: Unit = {
    if (config == null) {
      println("First configure simulation, then try to run it")
      return
    }

    // HERE USE YOUR FUCKING ENGINE TO DO STUFF
  }

  def configure(configPath: String): Unit = {
    this.config = new BasicConfig(configPath)
    this.config.load()

    // here create Hospital, Engine, History -- depended on config
    this.hospital = new Hospital
    this.engine = new BasicEngine
    this.history = new History
  }

  def getData: String = {
    this.config.getData
  }

  def getHistory: History = {
    this.history
  }
}
