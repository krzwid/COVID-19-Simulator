package Model.Config

class BasicConfig(var configPath: String) extends Config {
  private var dataString: String = ""

  def load(): Unit = {
    this.readData()
  }

  def load(source: String): Unit = {
    this.configPath = source
    this.readData()
  }

  private def readData(): Unit = {
    //reading data from file
    if (this.configPath == null) return
    val builder = new StringBuilder()
    val source = scala.io.Source.fromFile(this.configPath)
    val lines = try source.mkString finally source.close()
    lines.foreach(e => {
      builder.append(e)
    })
    this.dataString = builder.toString()
    println("Successful reading!\n")
  }

  override def getData: String = {
    this.dataString
  }

}
