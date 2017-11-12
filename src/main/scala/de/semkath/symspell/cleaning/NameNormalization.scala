package de.semkath.symspell.cleaning

import java.text.Normalizer

class NameNormalization {
    def cleanCompanyName(companyName: String): String = {
        normalize(companyName)
            .replaceAll("\\.", "")
            .replaceAll("\\p{Punct}", " ")
            .replaceAll("\\s+", " ")
            .toLowerCase
            .trim
    }

    def normalize(name: String): String = {
        Normalizer.normalize(name, Normalizer.Form.NFKD)
            .replaceAll("\\p{general_category=Mn}+", "")
    }
}
