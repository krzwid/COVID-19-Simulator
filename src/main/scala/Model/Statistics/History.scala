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
    doSpecificPlot("Infected COVID (Staff)", (dailyData: DailyData) => dailyData.infectedCovidStaff)
    doSpecificPlot("Infected COVID (Patients)", (dailyData: DailyData) => dailyData.infectedCovidPatients)
    doSpecificPlot("Shows COVID symptoms", (dailyData: DailyData) => dailyData.showsCovidSymptoms)
    doSpecificPlot("Daily new COVID deaths (Staff)", (dailyData: DailyData) => dailyData.deadForCovidStaff)
    doSpecificPlot("Daily new COVID deaths (Patients)", (dailyData: DailyData) => dailyData.deadForCovidPatients)
    doSpecificPlot("Daily new COVID cases (Staff)", (dailyData: DailyData) => dailyData.newCovidInfectionsStaff)
    doSpecificPlot("Daily new COVID cases (Patients)", (dailyData: DailyData) => dailyData.newCovidInfectionsPatients)
    doSpecificPlot("Daily new COVID recovered (Staff)", (dailyData: DailyData) => dailyData.curedFromCovidStaff)
    doSpecificPlot("Daily new COVID recovered (Patients)", (dailyData: DailyData) => dailyData.curedFromCovidPatients)
    doSpecificPlot("Daily new other illnesses deaths (Patients)", (dailyData: DailyData) => dailyData.diedForOtherCausesPatients)
    doSpecificPlot("Daily new other illnesses recovered (Staff)", (dailyData: DailyData) => dailyData.curedFromOtherDiseases)
    doSpecificPlot("Daily new deaths (COVID and other)", (dailyData: DailyData) => dailyData.deadForCovidAndOtherCauses)
    doSpecificPlot("Number of patients hospital", (dailyData: DailyData) => dailyData.patientsInHospital)
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
      .custom(valuesToPlot.map(Bar.apply), spacing = Some(5), barRenderer = Some(labeledByColor))
      .standard(xLabels = labels)
      .hline(0)
      .title(title)
      .render()
      .write(new File("plots/" + filePath))
  }
}
