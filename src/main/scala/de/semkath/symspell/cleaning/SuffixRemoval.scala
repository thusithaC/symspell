package de.semkath.symspell.cleaning

import java.text.Normalizer

import scala.collection.immutable
import scala.io.Source

class SuffixRemoval {
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
//        shortened.split(" ").filterNot(legalSuffixes.contains).mkString(" ")
        ""
    }

    private def cleanCompanyName(companyName: String): String = {
        Normalizer.normalize(companyName, Normalizer.Form.NFKD)
            .toLowerCase
            .replaceAll("\\p{general_category=Mn}+", "")
            .replaceAll("\\.", "")
            .replaceAll("\\p{Punct}", " ")
            .replaceAll(raw"\s+", " ")
            .trim
    }

    def shortenLegalSuffixes(companyName: String): Vector[String] = {
        getNGrams(companyName)
            .reverse
            .map(tokens => (tokens._1, legalSuffixAbbreviations.getOrElse(tokens._2, tokens._2), tokens._3).productIterator.mkString(" "))
    }

    def getNGrams(companyName: String): Vector[(String, String, String)] = {
        val tokens = companyName.split(" ")
        (1 to tokens.length).flatMap(i => getNGrams(companyName, i)).toVector
    }

    private def getNGrams(companyName: String, size: Integer): IndexedSeq[(String, String, String)] = {
        val tokens = companyName.split(" ")
        for {
            i <- 0 until tokens.length - (size - 1)
        } yield (
            tokens.slice(0, i).mkString(" "),
            tokens.slice(i, i + size).mkString(" "),
            tokens.slice(i + size, tokens.length).mkString(" ")
        )
    }
}
