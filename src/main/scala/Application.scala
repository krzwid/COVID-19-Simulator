import Model.People.{Doctor, Nurse}

object Application {
  import Model.Simulation
  def main(args: Array[String]): Unit = {
//    val simulation = new Simulation("TestConfig")

    //reading data from file
    val source = scala.io.Source.fromFile("C:\\Users\\Lenovo\\Desktop\\simulationData.txt")
    val lines = try source.mkString finally source.close()
    println("Czytam cyferki z pliku:")
    lines.foreach(e => print(e))
    println("Sukces!\n")

    val staff = new Nurse(12, false, 0, false)
    val patientStaff = staff.transformToPatient
    val comeBack = patientStaff.transformToStaff

    if (staff.getID == comeBack.getID && staff.isInfected == comeBack.isInfected) {
      println("Pelen sukces")
    }

    println("classOf[comeBack] = " + comeBack.getClass.toString)
  }
}
