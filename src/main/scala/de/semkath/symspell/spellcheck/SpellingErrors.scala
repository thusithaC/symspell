package de.semkath.symspell.spellcheck

import scala.collection.mutable

class SpellingErrors {
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
}
