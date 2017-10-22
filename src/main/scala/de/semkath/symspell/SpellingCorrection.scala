package de.semkath.symspell

class SpellingCorrection(dictionary: SpellingDictionary) {
    def correct(word: String): Option[String] = {
        dictionary.dictionary.get(word)
    }
}
