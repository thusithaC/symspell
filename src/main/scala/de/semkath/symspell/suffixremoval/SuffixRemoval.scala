package de.semkath.symspell.suffixremoval

import java.text.Normalizer

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
        shortenLegalSuffixes(cleaned)
    }

    private def cleanCompanyName(companyName: String): String = {
        Normalizer.normalize(companyName, Normalizer.Form.NFKD)
            .toLowerCase
            .replaceAll("\\p{general_category=Mn}+", "")
            .replaceAll("\\p{Punct}", " ")
            .replaceAll("\\s+", " ")
    }

    def shortenLegalSuffixes(companyName: String): String = {
        companyName.split(" ").map(token => legalSuffixAbbreviations.getOrElse(token, token)).mkString(" ")
    }
}
