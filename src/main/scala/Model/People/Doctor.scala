package Model.People

class Doctor(
              ID: Int,
              infected: Boolean,
              infectionSince: Int,
              covidSymptoms: Boolean
            ) extends Staff(ID, infected, infectionSince, covidSymptoms) {
}
