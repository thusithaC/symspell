package de.semkath.symspell

object Main extends App {
    val filename = "data/companies_short_dump.csv"
    val csv = new CSV
    val rows = csv.read(filename)
    rows.foreach(row => println(row))
}
