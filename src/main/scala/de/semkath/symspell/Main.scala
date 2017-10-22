package de.semkath.symspell

import scala.collection.mutable

object Main extends App {
    val filename = "data/companies_short_dump.csv"
    val csv = new CSV
    val rows = csv.read(filename)
    val words = rows.map(_(1))

    val dictionary = new SpellingDictionary(words, 2)
    val correction = new SpellingCorrection(dictionary)

    val corrected = correction.correct("China High-Speed Railway Technology Co., Ltd.")
    println(corrected)
}
