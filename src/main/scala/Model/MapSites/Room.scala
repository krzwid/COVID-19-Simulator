package Model.MapSites

import Model.People.{Patient, Person, Staff}

trait Room {
  def goIn(staff: Staff): Unit
  def goOut(staff: Staff): Unit
  def canGoIn(staff: Staff): Boolean
  def getPerson(ID: Int): Person
}