package de.semkath.symspell.cleaning

import scala.annotation.tailrec
import scala.collection.mutable
import scala.io.Source

final class LegalFormRemoval {
    private val normalizer = new NameNormalization

    private val legalSuffixes: Set[String] = {
        Source.fromResource("legal_forms").getLines().toSet
    }

    private val legalSuffixAbbreviations: Map[String, String] = {
        Source.fromResource("legal_form_abbreviations").getLines()
            .map(line => line.split(":") match {
                case Array(suffix, abbreviation) => (normalizer.normalize(suffix), abbreviation)
            }).toMap
    }

    def removeLegalForms(companyName: String): String = {
        val shortened = shortenLegalSuffixes(companyName)
        shortened.split(" ").filterNot(legalSuffixes.contains).mkString(" ")
    }

    private def shortenLegalSuffixes(companyName: String): String = {
        val nGrams = new mutable.Queue[(String, String, String)]()
        nGrams ++= getNGrams(companyName).reverse

        @tailrec
        def shortenLegalSuffixes(companyName: String, nGrams: mutable.Queue[(String, String, String)]): String = {
            val nGram = nGrams.dequeue()
            val shortenedName = List(nGram._1, legalSuffixAbbreviations.getOrElse(nGram._2, nGram._2), nGram._3).mkString(" ").trim

            // If the name was successfully shortened, use it to generate new NGrams
            if (shortenedName.length < companyName.length) {
                nGrams.clear
                nGrams ++= getNGrams(shortenedName).reverse
            }

            if (nGrams.nonEmpty) {
                shortenLegalSuffixes(shortenedName, nGrams)
            } else {
                shortenedName
            }
        }

        shortenLegalSuffixes(companyName, nGrams)
    }

    private def getNGrams(companyName: String): IndexedSeq[(String, String, String)] = {
        val tokens = companyName.split(" ")
        (1 to tokens.length).flatMap(i => getNGrams(tokens, i))
    }

    private def getNGrams(tokens: Array[String], size: Integer): IndexedSeq[(String, String, String)] = {
        for {
            i <- 0 until tokens.length - (size - 1)
        } yield (
            tokens.view.slice(0, i).mkString(" "),
            tokens.view.slice(i, i + size).mkString(" "),
            tokens.view.slice(i + size, tokens.length).mkString(" ")
        )
    }
}
