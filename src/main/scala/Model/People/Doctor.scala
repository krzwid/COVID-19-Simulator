package Model.People

class Doctor(
              ID: Int,
              infected: Boolean = false,
              infectionSince: Int = 0,
              covidSymptoms: Boolean = false
            ) extends Staff(ID, infected, infectionSince, covidSymptoms) {
}
