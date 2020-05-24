package Model.Statistics

import java.io.File

import com.cibo.evilplot.plot.ScatterPlot
import com.cibo.evilplot.plot._
import com.cibo.evilplot.numeric.Point
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._

class History {
  private val days: scala.collection.mutable.ListBuffer[DailyData] = new scala.collection.mutable.ListBuffer[DailyData]()

  def addDay(day: DailyData): Unit = {
    days.addOne(day)
  }

  def getHistory: List[DailyData] = {
    this.days.toList
  }

  def printInfection(howManyDays: Int, infectedEachDay: Array[Int]): Unit = {
    val data = Seq.tabulate(howManyDays) { i =>
      Point(i.toDouble, infectedEachDay(i))
    }
    ScatterPlot(data)
      .xAxis()
      .yAxis()
      .frame()
      .xLabel("x")
      .yLabel("y")
      .render()
      .write(new File("abcd.png"))
  }
}
