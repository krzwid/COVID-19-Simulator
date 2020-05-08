package Model.MapSites

import Model.People.Person

trait Room {
  def goIn(person: Person): Unit
  def canGoIn(person: Person): Boolean
  def goOut(person: Person): Unit
  def getPerson(ID: Int): Person
}
