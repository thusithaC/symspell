package de.semkath.symspell.cleaning

import java.util.Locale

class AddressRemoval {
    def removeAddresses(companyName: String): String = {
        val countryCodes = Locale.getISOCountries
        companyName.replaceAll(" .+" + countryCodes.mkString("(", ")|(", ")") +  ".*", "")
    }
}
