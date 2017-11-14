package de.semkath.symspell

import java.util.logging.Logger

import de.semkath.symspell.cleaning.{LegalFormRemoval, NameNormalization}
import de.semkath.symspell.spellcheck.{SpellingCorrection, SpellingDictionary, SpellingErrors}

import scala.collection.mutable

object Main extends App {
    /*
    val companyFile = "data/companies_short_dump.csv"
    val csv = new CSV
    val companyRows = csv.read(companyFile)
    */
    val legalFormRemoval = new LegalFormRemoval
    val normalizer = new NameNormalization
    /*
    val companyNames = companyRows.map(row => {
        val cleaned = normalizer.cleanCompanyName(row(1))
        legalFormRemoval.removeLegalForms(cleaned)
    })
    */

    val companyNames = List("qualcomm", "robert bosch b", "robert bosch a", "siemens network solutions", "siemens networking solutions")

    Logger.getGlobal.info("Creating dictionary")

    val dictionary = new SpellingDictionary(companyNames)
    val correction = new SpellingCorrection(dictionary)

    Logger.getGlobal.info("Creating dictionary finished. Size: " + dictionary.dictionary.size)

    /*
    val patentFile = "data/unique_names_assignee_original.csv"
    val patentRows = csv.read(patentFile)
    val patentNames = patentRows.map(_.head)
    */

    val patentNames = List("qulacomm", "robert bosch b", "simens networki solutions")
    patentNames.foreach(name => {
        val corrected = correction.correct(name)
        println(s"$name -> $corrected")
    })
}
