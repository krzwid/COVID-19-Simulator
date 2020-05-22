package Engine

import Model.Config.Config
import Model.Engine.BasicEngine
import Model.MapSites.Hospital
import Model.Statistics.History
import org.scalatest.FunSuite

//class BasicEngineTest extends FunSuite {
//  val config: Config = new Config {
//    override def load(): Unit = ???
//
//    override def load(source: String): Unit = ???
//
//    override def getData: String = ???
//  }
//  val hospital = new Hospital
//  val history = new History
//  val basicEngine = new BasicEngine(config, hospital, history)
//
//  test("BasicEngineTest.startNewDay") {
//    basicEngine.startNewDay
//    assertResult(8) {
//      basicEngine.getHour
//    }
//    assertResult(0) {
//      basicEngine.getMinute
//    }
//  }
//
//  test("BasicEngineTest.nextStep") {
//    var hour: Int = 8
//    var minute: Int = 30
//    for(x <- 0 to 18) {
//      basicEngine.nextStep
//      assertResult(hour) {
//        basicEngine.getHour
//      }
//      assertResult(minute) {
//        basicEngine.getMinute
//      }
//      if(x%2==0) hour += 1
//      minute = (minute + 30 ) % 60
//    }
//
//    basicEngine.nextStep
//    assertResult(8) {
//      basicEngine.getHour
//    }
//    assertResult(0) {
//      basicEngine.getMinute
//    }
//  }
//}
