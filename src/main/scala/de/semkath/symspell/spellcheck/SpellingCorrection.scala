package de.semkath.symspell.spellcheck

class SpellingCorrection(dictionary: SpellingDictionary) {
    def correct(word: String): Option[String] = {
        dictionary.dictionary.get(word)
    }
}
