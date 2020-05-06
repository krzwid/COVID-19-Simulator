package Model

import Model.Engine.Engine
import Model.MapSites.Hospital
import Model.Statistics.History

class Simulation(var config: String,
                 hospital: Hospital,
                 engine: Engine,
                 history: History) {
  def simulate(days: Int): Unit = {
    println("config: \"" + config + "\"")
    println("it will last for " + days + " days")
  }

  def setConfig(config: String): Unit = {
    this.config = config
  }
}
