package Model.Config

import scala.collection.immutable.HashMap
import scala.collection.mutable

class BasicConfig extends Config {
  private var parametersSrc: String = _
  var parametersLines: List[String] = _

  private var patientsSrc: String = _
  var patientsLines: List[Array[String]] = _

  private var parameters: Map[String, Int] = _

//  private var functions: Map[String, (_) => (_)] = new HashMap[String, (_) => (_)](
//    "f1" -> ((a: Int): => a * a),
//
//  )

  def this(parametersSrc: String, patientsSrc: String) = {
    this()
    if (parametersSrc == null || patientsSrc == null) throw new NullPointerException("Null sources paths")
    this.parametersSrc = parametersSrc
    this.patientsSrc = patientsSrc
    this.readData()

    // parse parameters to Int's
    val mapBuilder = new mutable.HashMap[String, Int]()
    for (line <- parametersLines) {
      val split = line.split('=')
      if (split(0).equals("") || split(1).equals("")) throw new IllegalArgumentException("Invalid file")
      if (!(split(1) forall Character.isDigit)) throw new IllegalArgumentException("Non-numerical parameter")
      mapBuilder.put(split(0), split(1).toInt)
    }
    this.parameters = mapBuilder.toMap
  }

  private def readData(): Unit = {
    val sourceParams = scala.io.Source.fromFile(this.parametersSrc)
    this.parametersLines = try sourceParams.getLines().toList finally sourceParams.close()

    val sourcePatients = scala.io.Source.fromFile(this.patientsSrc)
    val patientsLines = try sourcePatients.getLines().toList finally sourcePatients.close()
    this.patientsLines = patientsLines.map(e => e.split(','))
  }

  def printParameters(): Unit = {
    for ((key, value) <- this.parameters) {
      println(key + ": " + value.toString)
    }
  }

  override def getParameters: Map[String, Int] = {
    this.parameters
  }

  override def getPatientsData: List[Array[String]] = {
    this.patientsLines
  }

  override def getParametersInt(key: String): Int = {
    if (this.parameters.keySet.contains(key)) this.parameters(key)
    else throw new NoSuchElementException("There is no such parameter: \"" + key + "\"")
  }

  override def getF(key: String): Int => Int = {
    (a: Int) => a
  }
}
