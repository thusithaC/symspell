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

    def shortenLegalSuffixes(companyName: String) = {
        println(getNGrams(companyName).reverse)
        getNGrams(companyName).reverse.map(token => legalSuffixAbbreviations.getOrElse(token, token))
    }

    def getNGrams(companyName: String): List[String] = {
        val tokens = companyName.split(" ")
        (1 to tokens.length).flatMap(i => tokens.sliding(i).toList.map(_.mkString(" "))).toList
    }
}
