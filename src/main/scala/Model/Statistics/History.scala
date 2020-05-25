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

//  // methods to draw plots
//
//  private def drawPlot(howManyDays: Int, values: Array[Int], title: String, filePath: String): Unit = {
//    val data = Seq.tabulate(howManyDays) { i =>
//      Point(i.toDouble, values(i))
//    }
//    ScatterPlot(data)
//      .xAxis()
//      .yAxis()
//      .frame()
//      .xLabel("x")
//      .yLabel("y")
//      .title(title)
//      .render()
//      .write(new File(filePath))
//  }
//
//  def printInfection(howManyDays: Int, infectedEachDay: Array[Int]): Unit = {
//    drawPlot(howManyDays, infectedEachDay, "infected each day", "abcd.png")
//  }
//
//  def printDeadFromCovidStaff(): Unit = {
//    val list = getHistory
//    val arrayValues = list.map(_.deadForCovidStaff).toArray
//    drawPlot(arrayValues.length, arrayValues, "Dead from Covid", "deadForCovid.jpg")
//  }
}
