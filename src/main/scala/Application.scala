import Model.Simulation

object Application {
  def main(args: Array[String]): Unit = {
    try {
      val rscPath = "src\\main\\resources\\"
      val simulation = new Simulation()
      simulation.configure(rscPath + "parameters.txt", rscPath + "patients.txt")
      val history = simulation.simulate


//      //test of drawing plot
//      val infections: Array[Int] = Array(1,2,3,4,5)
//      history.printInfection(5, infections)


    } catch {
      case e: Exception => e.printStackTrace()
    } finally {
      println("Shutdown - I hope you enjoyed the results!")
    }
  }
}
