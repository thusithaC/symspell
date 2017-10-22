package de.semkath.symspell

class SpellingDictionary(words: Iterable[String], distance: Int) {
    private val errors = new SpellingErrors
    private val _dictionary = buildDictionary()

    def dictionary: Map[String, String] = _dictionary

    private def buildDictionary(): Map[String, String] = {
        words.flatMap(word => errors.generateDeletions(word, distance).map(deletion => (deletion, word))).toMap
    }


}
