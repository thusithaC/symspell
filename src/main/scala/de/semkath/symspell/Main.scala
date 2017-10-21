package de.semkath.symspell

import scala.collection.mutable

object Main extends App {
    val filename = "data/companies_short_dump.csv"
    val csv = new CSV
    val rows = csv.read(filename)
    val dictionary = new SpellingDictionary()
    val words = dictionary.getWords(rows.map(_(1)))
    val errors = new SpellingErrors()
    println(words.head)
    println(errors.generateDeletions(words.head, 3).size)
}
