package de.semkath.symspell

import de.semkath.symspell.csv.CSV
import de.semkath.symspell.spellcheck.{SpellingCorrection, SpellingDictionary}
import de.semkath.symspell.cleaning.{AddressRemoval, LegalFormRemoval}

import scala.collection.mutable

object Main extends App {
    val filename = "data/unique_names_assignee_original.csv"
    val csv = new CSV
    val rows = csv.read(filename)
    val companyNames = rows.map(_(0))

    val suffix = new LegalFormRemoval

    companyNames.foreach(companyName => println(s"$companyName -> " + suffix.removeLegalForms(companyName)))

}
