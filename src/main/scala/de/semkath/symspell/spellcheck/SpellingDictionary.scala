package de.semkath.symspell.spellcheck

class SpellingDictionary(words: Iterable[String]) {
    private val errors = new SpellingErrors
    private val _dictionary = buildDictionary()

    def dictionary: Map[String, String] = _dictionary

    private def buildDictionary(): Map[String, String] = {
        words.par.flatMap(word => {
            word.split(" ").flatMap(token => {
                val distance = token.length * 0.2f
                errors.generateDeletions(token, distance.toInt).map(deletion => (deletion, token))
            })
        }).toMap.seq
    }
}
