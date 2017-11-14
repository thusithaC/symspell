package de.semkath.symspell.spellcheck

import scala.collection.mutable

class SpellingErrors {
    def generateDeletions(token: String, distance: Int): Set[String] = {
        val maxDist = if (distance > token.length) token.length else distance

        def accumulateDeletions(token: String, distance: Int, accumulator: mutable.Set[String]): Set[String] = {
            for (pos <- token.indices) {
                val deletion = token.patch(pos, Nil, 1)
                if (accumulator.add(deletion) && distance > 1) {
                    accumulateDeletions(deletion, distance - 1, accumulator)
                }
            }
            accumulator += token
            accumulator.toSet
        }

        accumulateDeletions(token, maxDist, new mutable.HashSet[String]())
    }
}
