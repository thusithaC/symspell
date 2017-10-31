package de.semkath.symspell

import de.semkath.symspell.csv.CSV
import de.semkath.symspell.spellcheck.{SpellingCorrection, SpellingDictionary}
import de.semkath.symspell.suffixremoval.SuffixRemoval

import scala.collection.mutable

object Main extends App {
    val filename = "data/companies_short_dump.csv"
    val csv = new CSV
    val rows = csv.read(filename)
    val words = rows.map(_(1))

    val removal = new SuffixRemoval
    val cleaned = removal.removeLegalSuffixes("ShenZhen Zero-Seven Co.,limited.")
    println(cleaned)
}
