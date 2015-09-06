package com.codacy.parsers

import java.io.File

import com.codacy.api.{CoverageFileReport, CoverageReport, Language}
import com.codacy.parsers.implementation.CoberturaParser
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpec}

class CoberturaParserTest extends WordSpec with BeforeAndAfterAll with Matchers {

  "CoberturaParser" should {

    "identify if report is invalid" in {
      val reader = new CoberturaParser(Language.Scala, new File("."), new File("src/test/resources/test_jacoco.xml"))

      reader.isValidReport shouldBe false
    }

    "identify if report is valid" in {
      val reader = new CoberturaParser(Language.Scala, new File("."), new File("src/test/resources/test_cobertura.xml"))

      reader.isValidReport shouldBe true
    }

    "return a valid report" in {
      val reader = new CoberturaParser(Language.Scala, new File("."), new File("src/test/resources/test_cobertura.xml"))

      val testReport = CoverageReport(Language.Scala, 87, List(
        CoverageFileReport("src/test/resources/TestSourceFile.scala", 87,
          Map(5 -> 1, 10 -> 1, 6 -> 2, 9 -> 1, 4 -> 1)),
        CoverageFileReport("src/test/resources/TestSourceFile2.scala", 87,
          Map(1 -> 1, 2 -> 1, 3 -> 1))))

      reader.generateReport() shouldEqual testReport
    }

  }

}
