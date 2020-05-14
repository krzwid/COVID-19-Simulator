package Model.Statistics

// graphs to draw for each day:
// 1) infected
// 2) dead
// 3) dead (with other diseases)
// 4) alive staff
// 5) alive patients

class DailyData {
  private var countDead: Int = 0
  private var countDeadStaff: Int = 0
  private var countDeadNonStaff: Int = 0

  private var countNewInfected: Int = 0
  private var countNewInfectedStaff: Int = 0
  private var countNewInfectedNonStaff: Int = 0

  private var countCured: Int = 0
  private var countCuredStaff: Int = 0
  private var countCuredNonStaff: Int = 0

  private var newStaff: Int = 0

}
