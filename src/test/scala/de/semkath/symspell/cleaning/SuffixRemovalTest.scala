package de.semkath.symspell.cleaning

import org.scalatest.FlatSpec

class SuffixRemovalTest extends FlatSpec {
    private val suffixRemoval = new SuffixRemoval

    behavior of "SuffixRemoval"

    it should "removeLegalSuffixes" in {
        val shortened = suffixRemoval.removeLegalSuffixes("rose series limited liability company")
        assert(shortened == "rose")
    }

}
