package Model.People

class Nurse(
             ID: Int,
             infected: Boolean,
             infectionSince: Int,
             covidSymptoms: Boolean
           ) extends Staff(ID, infected, infectionSince, covidSymptoms) {
}
