import Model.Simulation

object Application {
  def main(args: Array[String]): Unit = {
    try {
      val rscPath = "src\\main\\resources\\"
      val simulation = new Simulation()
      simulation.configure(rscPath + "parameters.txt", rscPath + "patients.txt", rscPath + "strategies.txt")
      val history = simulation.simulate
      history.doAllPlots()
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      println("Shutdown - I hope you enjoyed the results!")
    }
  }
}
