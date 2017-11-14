package de.semkath.symspell.spellcheck

import info.debatty.java.stringsimilarity.{JaroWinkler, NormalizedLevenshtein}

import scala.collection.mutable

class SpellingCorrection(dictionary: SpellingDictionary) {
    private val errors = new SpellingErrors
    private val editDistance = new JaroWinkler

    def correct(word: String): Seq[String] = {
            getBestCorrection(word)
            .map(correction => dictionary.radixTree.filterPrefix(correction).values.toList)
            .getOrElse(List.empty)
    }

    def getBestCorrection(word: String): Option[String] = {
        val possibleCorrections = getPossibleCorrections(word)

        possibleCorrections.headOption.map(_ => {
            possibleCorrections.map(possibleCorrection => possibleCorrection -> editDistance.distance(possibleCorrection, word))
                .minBy { case (a, b) => b }
                ._1
        })
    }

    private def getPossibleCorrections(word: String): Set[String] = {
        word.split(" ")
            .map(token => {
                val distance = token.length * 0.2f
                val deletions = errors.generateDeletions(token, distance.toInt)
                deletions.flatMap(deletion => dictionary.dictionary.get(deletion))
            })
            .reduceLeft((x, y) => cartesian(x, y))
    }

    private def cartesian(xs: Set[String], ys: Set[String]): Set[String] = for {x <- xs; y <- ys} yield List(x, y).mkString(" ")
}
