package Model.MapSites

import Model.People.Patient

import scala.collection.mutable

class Hospital {
  private val floors = List[Floor]()
  private val newPatients = new mutable.Queue()

  def freeBeds: Int ={
    floors.map(_.freeBeds).sum
  }

  def addPatient(patient: Patient): Unit = {
  }

}
