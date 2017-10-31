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
        companyName.split(" ").map(token => legalSuffixAbbreviations.getOrElse(token, token)).mkString(" ")
    }
}
