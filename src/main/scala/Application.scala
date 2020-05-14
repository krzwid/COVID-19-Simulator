object Application {
  import Model._

  def main(args: Array[String]): Unit = {
    val simulation = new Simulation

    simulation.configure("src\\main\\resources\\strategies.txt")
    println(simulation.getData)

    simulation.simulate // it should return History
  }
}
