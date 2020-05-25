package Model.Statistics

import java.io.File

import com.cibo.evilplot.colors.RGB
import com.cibo.evilplot.geometry.{Align, Drawable, Extent, Rect, Text}
import com.cibo.evilplot.plot._
import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultFonts, DefaultTheme}
import com.cibo.evilplot.plot.renderers.BarRenderer

class History {
  private val days: scala.collection.mutable.ListBuffer[DailyData] = new scala.collection.mutable.ListBuffer[DailyData]()

  def addDay(day: DailyData): Unit = {
    days.addOne(day)
  }

  def getHistory: List[DailyData] = {
    this.days.toList
  }

  // methods to draw plots


  def doAllPlots(): Unit = {
    doSpecificPlot("infectedCovidStaff", (dailyData: DailyData) => dailyData.infectedCovidStaff)
    doSpecificPlot("infectedCovidPatients", (dailyData: DailyData) => dailyData.infectedCovidPatients)
    doSpecificPlot("showsCovidSymptoms", (dailyData: DailyData) => dailyData.showsCovidSymptoms)
    doSpecificPlot("deadForCovidStaff", (dailyData: DailyData) => dailyData.deadForCovidStaff)
    doSpecificPlot("diedForCovidPatients", (dailyData: DailyData) => dailyData.diedForCovidPatients)
    doSpecificPlot("newCovidInfectionsStaff", (dailyData: DailyData) => dailyData.newCovidInfectionsStaff)
    doSpecificPlot("newCovidInfectionsPatients", (dailyData: DailyData) => dailyData.newCovidInfectionsPatients)
    doSpecificPlot("curedFromCovidStaff", (dailyData: DailyData) => dailyData.curedFromCovidStaff)
    doSpecificPlot("curedFromCovidPatients", (dailyData: DailyData) => dailyData.curedFromCovidPatients)
    doSpecificPlot("diedForOtherCausesPatients", (dailyData: DailyData) => dailyData.diedForOtherCausesPatients)
    doSpecificPlot("curedFromOtherDiseases", (dailyData: DailyData) => dailyData.curedFromOtherDiseases)
    doSpecificPlot("diedForCovidAndOtherCauses", (dailyData: DailyData) => dailyData.diedForCovidAndOtherCauses)
    doSpecificPlot("patientsInHospital", (dailyData: DailyData) => dailyData.patientsInHospital)
  }

  def doSpecificPlot(title: String, f: DailyData => Int): Unit = {
    val list = getHistory
    val arrayValues = list.map(f).toArray
    drawPlot(arrayValues.length,  arrayValues, title, title + ".png")
  }

  private def drawPlot(howManyDays: Int, values: Array[Int], title: String, filePath: String): Unit = {
    val valuesToPlot:Seq[Double] = values.map(_.toDouble).toSeq
    implicit val theme = DefaultTheme.copy(
      fonts = DefaultFonts.copy(tickLabelSize = 14, legendLabelSize = 14, fontFace = "'Lato', sans-serif")
    )

    val labels = (1 to howManyDays).map(_.toString)

    val labeledByColor = new BarRenderer {
      def render(plot: Plot, extent: Extent, category: Bar): Drawable = {
        val rect = Rect(extent)
        val value = category.values.head.toInt
        val color = RGB(226, 56, 140)

        Align.center(rect filled color, Text(s"$value", size = 20).filled(theme.colors.label)).group
      }
    }

    BarChart
      .custom(valuesToPlot.map(Bar.apply), spacing = Some(1), barRenderer = Some(labeledByColor))
      .standard(xLabels = labels)
      .hline(0)
      .title(title)
      .render()
      .write(new File("plots/" + filePath))
  }
}
