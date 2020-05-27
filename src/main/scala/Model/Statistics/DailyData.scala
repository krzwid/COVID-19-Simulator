package Model.Statistics

// graphs to draw for each day:

//1) infected covid
//2) dead covid
//3) dead other
//4) dead all
//5) cured covid
//6) active covid
//7) alive patients
//8) alive staff
//9) mortality

class DailyData {

  // -----
  // STATYSTYKI Z DANEGO DNIA:
  // - liczba wszystkich zarazonych
  // - liczba pokazujacych objawy
  // - ilosc osob ktore umarly na covid (pacjenci/personel)
  // - ilosc osob ktore zostaly zarazone covidem (pacjenci/personel)
  // - ilosc osob ktore wyzdrowialy z covidu (pacjenci/personel)

  // - ilosc osob ktore umarly na inne choroby (pacjenci/personel)
  // - ilosc osob ktore umarly na to i to (pacjenci/personel)
  // - ilosc osob ktore wyzdrowialy z innych chorob (pacjenci)
  // - liczba chorych w szpitalu (na poczatku dnia)
  // -----

  var infectedCovidStaff: Int = 0
  var infectedCovidPatients: Int = 0

  var showsCovidSymptoms: Int = 0

  var deadForCovidStaff: Int = 0
  var deadForCovidPatients: Int = 0

  var newCovidInfectionsStaff: Int = 0
  var newCovidInfectionsPatients: Int = 0

  var curedFromCovidStaff: Int = 0
  var curedFromCovidPatients: Int = 0

  var diedForOtherCausesPatients: Int = 0

  var curedFromOtherDiseases: Int = 0

  var deadForCovidAndOtherCauses: Int = 0

  var patientsInHospital: Int = 0
}
