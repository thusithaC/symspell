package de.semkath.symspell

import java.io.Reader

import com.univocity.parsers.csv.{CsvParser, CsvParserSettings}

import scala.collection.JavaConverters._
import scala.io.Source

class CSV {
    def read(filename: String): Rows = {
        val reader = getReader(filename)
        val settings = new CsvParserSettings()
        settings.setHeaderExtractionEnabled(true)
        val parser = new CsvParser(settings)
        parser.iterate(reader).asScala.map(_.toList)
    }

    private def getReader(filename: String): Reader = {
        Source.fromFile(filename).bufferedReader()
    }
}
