package de.semkath.symspell

import java.util.logging.Logger

import de.semkath.symspell.cleaning.{LegalFormRemoval, NameNormalization}
import de.semkath.symspell.spellcheck.{SpellingDictionary, SpellingErrors}

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

    val companyNames = List("qualcomm", "robert bosch", "siemens networking solutions", "nokia network")

    Logger.getGlobal.info("Creating dictionary")

    val dictionary = new SpellingDictionary(companyNames)
    val errors = new SpellingErrors

    Logger.getGlobal.info("Creating dictionary finished. Size: " + dictionary.dictionary.size)

    /*
    val patentFile = "data/unique_names_assignee_original.csv"
    val patentRows = csv.read(patentFile)
    val patentNames = patentRows.map(_.head)
    */

    //    val patentNames = List("qulacomm", "robert bosch", "simens networki solutions")

    val combinations = "simens networki solutions".split(" ")
        .map(token => {
            val distance = token.length * 0.2f
            val deletions = errors.generateDeletions(token, distance.toInt)
            val corrections = deletions.flatMap(deletion => dictionary.dictionary.get(deletion))
            new mutable.ArrayStack[String]() ++= corrections
        })

    val cardinality = combinations.map(_.size).product
    val possibleCorrections = for {
        i <- 0 until cardinality
    } yield combinations.map(corrections => {
        if (corrections.size > 1) {
            corrections.pop
        } else {
            corrections.head
        }
    }).mkString(" ")

    println(possibleCorrections)
}
