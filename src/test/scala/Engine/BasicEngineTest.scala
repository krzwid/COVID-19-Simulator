package Engine

import Model.Config.{BasicConfig, Config}
import Model.Engine.BasicEngine
import Model.MapSites.Hospital
import Model.Statistics.History
import org.scalatest.FunSuite

abstract class BasicEngineTest extends FunSuite {
  val config: BasicConfig
  val hospital = new Hospital(5,3, 6)
  val history = new History
  val basicEngine = new BasicEngine(config, hospital)

  test("BasicEngineTest.startNewDay") {
    basicEngine.startNewDay()
    assertResult(8) {
      basicEngine.getHour
    }
    assertResult(0) {
      basicEngine.getMinute
    }
  }

  test("BasicEngineTest.nextStep") {
    var hour: Int = 8
    var minute: Int = 30
    for(x <- 0 to 18) {
      basicEngine.nextStep
      assertResult(hour) {
        basicEngine.getHour
      }
      assertResult(minute) {
        basicEngine.getMinute
      }
      if(x%2==0) hour += 1
      minute = (minute + 30 ) % 60
    }

    basicEngine.nextStep()
    assertResult(8) {
      basicEngine.getHour
    }
    assertResult(0) {
      basicEngine.getMinute
    }
  }
}
