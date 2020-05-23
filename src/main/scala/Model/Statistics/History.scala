package Model.Statistics

class History {
  private val days: scala.collection.mutable.ListBuffer[DailyData] = new scala.collection.mutable.ListBuffer[DailyData]()

  def addDay(day: DailyData): Unit = {
    days.addOne(day)
  }

  def getHistory: List[DailyData] = {
    this.days.toList
  }

  def printGraphs(): Unit = {
    println("Here will be a lot of statistics")
  }
}
