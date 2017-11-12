package de.semkath.symspell.spellcheck

import info.debatty.java.stringsimilarity.{JaroWinkler, NormalizedLevenshtein}

import scala.collection.mutable

class SpellingCorrection(dictionary: SpellingDictionary) {
    private val errors = new SpellingErrors
    private val editDistance = new JaroWinkler

    def correct(word: String): String = {
        getPossibleCorrections(word)
            .map(possibleCorrection => (possibleCorrection, editDistance.distance(possibleCorrection, word)))
            .toMap
            .minBy(_._2)
            ._1
    }

    private def getPossibleCorrections(word: String): Seq[String] = {
        val tokenCorrections = word.split(" ")
            .map(token => {
                val distance = token.length * 0.2f
                val deletions = errors.generateDeletions(token, distance.toInt)
                val corrections = deletions.flatMap(deletion => dictionary.dictionary.get(deletion))
                new mutable.ArrayStack[String]() ++= corrections
            })

        for {
            i <- 0 until tokenCorrections.map(_.size).product
        } yield tokenCorrections.map(corrections => {
            if (corrections.size > 1) corrections.pop else corrections.head
        }).mkString(" ")
    }
}
