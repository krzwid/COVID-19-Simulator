object Application {
  import Model.Simulation
  def main(args: Array[String]): Unit = {
    val simulation = new Simulation("TestConfig")
    simulation.simulate(10)
    simulation.simulate(142)

    // after changes
    simulation.setConfig("NewConfig")
    simulation.simulate(12)
  }
}
