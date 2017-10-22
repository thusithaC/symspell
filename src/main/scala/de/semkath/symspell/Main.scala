package de.semkath.symspell

import scala.collection.mutable

object Main extends App {
    val filename = "data/companies_short_dump.csv"
    val csv = new CSV
    val rows = csv.read(filename)
    val dictionary = new SpellingDictionary()
    val words = dictionary.getWords(rows.map(_(1)))
    val errors = new SpellingErrors()
    val start = System.currentTimeMillis()
    println(errors.generateDeletions("China High-Speed Railway Technology", 3).size)
    println(System.currentTimeMillis() - start + "ms")
}
