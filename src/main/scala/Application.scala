import Model.Config.BasicConfig
import Model.Simulation

object Application {
  def main(args: Array[String]): Unit = {
    val simulation = new Simulation
    simulation.configure("src\\main\\resources\\parameters.txt", "src\\main\\resources\\patients.txt")

//    simulation.simulate // it should return History
//    config.printMap

  }
}
