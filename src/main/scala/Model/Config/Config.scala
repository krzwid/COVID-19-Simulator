package Model.Config

trait Config {
  def load(): Unit
  def load(source: String): Unit
  // funkcje do zwracania wartosci / strategii
  def getData: String


}
