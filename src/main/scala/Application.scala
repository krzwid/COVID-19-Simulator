import Model.Config.BasicConfig
import Model.People.Doctor
import Model.Simulation

object Application {
  def main(args: Array[String]): Unit = {
    try {
      val rscPath = "src\\main\\resources\\"
      val simulation = new Simulation()
      simulation.configure(rscPath + "parameters.txt", rscPath + "patients.txt")
      val history = simulation.simulate
      history.printGraphs()
    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      println("Shutdown - I hope you enjoyed the results!")
    }
  }
}
