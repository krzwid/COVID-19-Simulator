package Model.Config

trait Config {
  // funkcje do zwracania wartosci / strategii
//  def getData: String
  def getPatientsData: List[Array[String]]
  def getParameters: Map[String, Int]
  def getParametersInt(key: String): Int
  def getF(key: String): Int => Int
}
