package de.semkath.symspell.spellcheck

import scala.collection.mutable

class SpellingErrors {
    def generate(words: List[String]): Map[String, String] = {
        words.view.map(word => word -> word).toMap
    }

    def generateDeletions(word: String, distance: Int): Set[String] = {
        val maxDist = if (distance > word.length) word.length else distance

        def accumulateDeletions(word: String, distance: Int, accumulator: mutable.Set[String]): Set[String] = {
            for (pos <- word.indices) {
                val deletion = word.patch(pos, Nil, 1)
                if (accumulator.add(deletion) && distance > 1) {
                    accumulateDeletions(deletion, distance - 1, accumulator)
                }
            }
            accumulator += word
            accumulator.toSet
        }

        accumulateDeletions(word, maxDist, new mutable.HashSet[String]())
    }

    private[this] def getFunctionalDeletions(word: String, distance: Int, accumulator: mutable.Set[String]): Set[String] = {
        def generateSingleDeletions(word: String): Set[String] = {
            word.view.indices.map(pos => word.patch(pos, Nil, 1)).toSet
        }

        if (distance > 0) {
            val deletions = generateSingleDeletions(word)
            accumulator ++= deletions
            deletions.flatMap(deletion => getFunctionalDeletions(deletion, distance - 1, accumulator))
        } else {
            accumulator.toSet
        }
    }
}
