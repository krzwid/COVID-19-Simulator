package Model.MapSites

import Model.People.{Patient, Person, Staff}

trait Room {
  def goIn(staff: Staff): Boolean
  def goOut(staff: Staff): Unit
  def canGoIn(staff: Staff): Boolean
  def getPerson(ID: Int): Person
  def getAllPeople: List[Person]
}