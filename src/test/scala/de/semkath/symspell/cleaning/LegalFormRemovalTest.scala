package de.semkath.symspell.cleaning

import org.scalatest.{FlatSpec, Matchers}

class LegalFormRemovalTest extends FlatSpec with Matchers {
    private val legalFormRemoval = new LegalFormRemoval

    behavior of "LegalFormRemoval"

    it should "remove limited" in {
        val shortened = legalFormRemoval.removeLegalForms("rose limited")
        shortened shouldBe "rose"
    }

    it should "remove GMBH" in {
        val shortened = legalFormRemoval.removeLegalForms("BOSCH GMBH ROBERT")
        shortened shouldBe "bosch robert"
    }

    it should "remove Company" in {
        val shortened = legalFormRemoval.removeLegalForms("Matsushita Solution Technology Company")
        shortened shouldBe "matsushita solution technology"
    }

    it should "remove Corporation" in {
        val shortened = legalFormRemoval.removeLegalForms("United Technologies Corporation")
        shortened shouldBe "united technologies"
    }

    it should "remove Incorporated" in {
        val shortened = legalFormRemoval.removeLegalForms("Qualcomm Incorporated")
        shortened shouldBe "qualcomm"
    }

    it should "remove gmbh" in {
        val shortened = legalFormRemoval.removeLegalForms("herberts gmbh")
        shortened shouldBe "herberts"
    }

    it should "remove Gesellschaft mit beschränkter Haftung" in {
        val shortened = legalFormRemoval.removeLegalForms("Robert Bosch Gesellschaft mit beschränkter Haftung")
        shortened shouldBe "robert bosch"
    }

    it should "remove AG" in {
        val shortened = legalFormRemoval.removeLegalForms("Bayer AG")
        shortened shouldBe "bayer"
    }

    it should "remove GmbH & Co. KG" in {
        val shortened = legalFormRemoval.removeLegalForms("Behr GmbH & Co. KG")
        shortened shouldBe "behr"
    }

    it should "remove S.A.S" in {
        val shortened = legalFormRemoval.removeLegalForms("Airbus S.A.S.")
        shortened shouldBe "airbus"
    }
}
