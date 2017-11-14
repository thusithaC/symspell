package de.semkath.symspell.spellcheck

import com.rklaehn.radixtree.RadixTree

class SpellingDictionary(words: Iterable[String]) {
    private val errors = new SpellingErrors
    private val _radixTree = buildRadixTree()
    private val _dictionary = buildDictionary(_radixTree)

    def radixTree: RadixTree[String, String] = _radixTree

    def dictionary: Map[String, String] = _dictionary

    private def buildDictionary(radixTree: RadixTree[String, String]): Map[String, String] = {
        val tokens = radixTree.values.flatMap(_.split(" ")).toIndexedSeq
        tokens.flatMap(token => {
            val distance = token.length * 0.2f
            errors.generateDeletions(token, distance.toInt).map(deletion => deletion -> token)
        })
        .toMap.seq
    }

    private def buildRadixTree(): RadixTree[String, String] = {
        val pairs = words.map(word => word -> word).toSeq
        RadixTree(pairs: _*)
    }
}
