package de.semkath.symspell.cleaning

import java.text.Normalizer

import scala.annotation.tailrec
import scala.collection.mutable
import scala.io.Source

final class SuffixRemoval {
    private val legalSuffixes: Set[String] = {
        Source.fromResource("legal_suffixes").getLines().toSet
    }

    private val legalSuffixAbbreviations: Map[String, String] = {
        Source.fromResource("legal_suffix_abbreviations").getLines()
            .map(line => line.split(":") match {
                case Array(suffix, abbreviation) => (suffix, abbreviation)
            }).toMap
    }

    def removeLegalSuffixes(companyName: String): String = {
        val cleaned = cleanCompanyName(companyName)
        val shortened = shortenLegalSuffixes(cleaned)
        shortened.split(" ").filterNot(legalSuffixes.contains).mkString(" ")
    }

    private def cleanCompanyName(companyName: String): String = {
        Normalizer.normalize(companyName, Normalizer.Form.NFKD)
            .toLowerCase
            .replaceAll("\\p{general_category=Mn}+", "")
            .replaceAll("\\.", "")
            .replaceAll("\\p{Punct}", " ")
            .replaceAll("\\s+", " ")
            .trim
    }

    private def shortenLegalSuffixes(companyName: String): String = {
        val queue = new mutable.Queue[(String, String, String)]()
        queue ++= getNGrams(companyName).reverse

        @tailrec
        def shortenLegalSuffixes(companyName: String, queue: mutable.Queue[(String, String, String)]): String = {
            val nGram = queue.dequeue()
            val shortenedName = List(nGram._1, legalSuffixAbbreviations.getOrElse(nGram._2, nGram._2), nGram._3).mkString(" ").trim

            // If the name was successfully shortened, use it to generate new NGrams
            if (shortenedName.length < companyName.length) {
                queue.clear
                queue ++= getNGrams(shortenedName).reverse
            }

            if (queue.nonEmpty) {
                shortenLegalSuffixes(shortenedName, queue)
            } else {
                shortenedName
            }
        }

        shortenLegalSuffixes(companyName, queue)
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
