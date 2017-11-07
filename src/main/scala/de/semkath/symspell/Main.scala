package de.semkath.symspell

import de.semkath.symspell.csv.CSV
import de.semkath.symspell.spellcheck.{SpellingCorrection, SpellingDictionary}
import de.semkath.symspell.cleaning.{AddressRemoval, SuffixRemoval}

import scala.collection.mutable

object Main extends App {
    val filename = "data/companies_short_dump.csv"
    val csv = new CSV
    val rows = csv.read(filename)
    val words = rows.map(_(1))

    val address = new AddressRemoval
    val suffix = new SuffixRemoval
    val source = "Sunny Wheel Industrial Co. Ltd. Hsiu Shui Hsiang Changhua TW,0053049546TW"

//    val noAddress = address.removeAddresses(source)
//    val cleaned = suffix.removeLegalSuffixes(noAddress)

    println(suffix.shortenLegalSuffixes("rose series limited liability company"))
}
