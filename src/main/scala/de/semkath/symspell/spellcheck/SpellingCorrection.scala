package de.semkath.symspell.spellcheck

import info.debatty.java.stringsimilarity.{JaroWinkler, NormalizedLevenshtein}

import scala.collection.mutable

class SpellingCorrection(dictionary: SpellingDictionary) {
    private val errors = new SpellingErrors
    private val editDistance = new JaroWinkler

    def correct(name: String): Seq[String] = {
            getBestCorrection(name)
            .map(correction => dictionary.radixTree.filterPrefix(correction).values.toList)
            .getOrElse(List.empty)
    }

    def getBestCorrection(name: String): Option[String] = {
        val possibleCorrections = getPossibleCorrections(name)

        possibleCorrections.headOption.map(_ => {
            possibleCorrections.map(possibleCorrection => possibleCorrection -> editDistance.distance(possibleCorrection, name))
                .minBy { case (a, b) => b }
                ._1
        })
    }

    private def getPossibleCorrections(name: String): Set[String] = {
        name.split(" ")
            .map(token => {
                val distance = token.length * 0.2f
                val deletions = errors.generateDeletions(token, distance.toInt)
                deletions.flatMap(deletion => dictionary.dictionary.get(deletion))
            })
            .reduceLeft((x, y) => cartesian(x, y))
    }

    private def cartesian(xs: Set[String], ys: Set[String]): Set[String] = for {x <- xs; y <- ys} yield List(x, y).mkString(" ")
}
