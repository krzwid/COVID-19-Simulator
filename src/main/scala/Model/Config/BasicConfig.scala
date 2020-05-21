package Model.Config

import scala.collection.mutable

class BasicConfig() extends Config {
  private var parametersSrc: String = null
  var parametersLines: List[String] = null

  private var patientsSrc: String = null
  var patientsLines: List[Array[String]] = null

//  private var staffSrc: String = null
//  var staffLines: List[Array[String]] = null

  val parameters: mutable.Map[String, Int] = new mutable.HashMap[String, Int]()

  def this(parametersSrc: String, patientsSrc: String) = {
    this()
    if (parametersSrc == null || patientsSrc == null) throw new Exception("Null parameters")
    this.parametersSrc = parametersSrc
    this.patientsSrc = patientsSrc
//    this.staffSrc = staffSrc
    this.readData()

    // parse parameters to Int's
    for (line <- parametersLines) {
      val split = line.split('=')
      this.parameters.put(split(0), split(1).toInt)
    }
  }

  private def readData(): Unit = {
    val sourceParams = scala.io.Source.fromFile(this.parametersSrc)
    this.parametersLines = try sourceParams.getLines().toList finally sourceParams.close()

    val sourcePatients = scala.io.Source.fromFile(this.patientsSrc)
    val patientsLines = try sourcePatients.getLines().toList finally sourcePatients.close()
    this.patientsLines = patientsLines.map(e => e.split(','))

//    val sourceStaff = scala.io.Source.fromFile(this.staffSrc)
//    val staffLines = try sourceStaff.getLines().toList finally sourceStaff.close()
//    this.staffLines = staffLines.map(e => e.split(",")).filter(e => !e.contains(null))
  }

  def printMap: Unit = {
    for ((key, value) <- this.parameters) {
      println(key + ": " + value.toString)
    }
  }
}
