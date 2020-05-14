import Model.People.{Doctor, Nurse}

object Application {
  import Model.Simulation
  def main(args: Array[String]): Unit = {
    val simulation = new Simulation

    simulation.configure("src\\main\\resources\\test.txt")
    simulation.getData

    simulation.simulate // it should return History
  }
}
