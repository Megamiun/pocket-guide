package br.com.gabryel.pocketguide.starter.parsers

import br.com.gabryel.pocketguide.startup.DocumentProvider
import br.com.gabryel.pocketguide.startup.parsers.*
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class FundamentalKotlinParserTest {

    @Test
    fun `when asking for a page that contains an isbn, then should return it`() {
        val uri = this::class.java.getResource("Fundamental Kotlin.html").toURI()
        val fileContent = Paths.get(uri).toFile().readText()

        val doc = DocumentProvider().fromString(fileContent)

        assertThat(FundamentalKotlinParser().getIsbnFrom(doc), equalTo("9788692030710"))
    }

    @Test
    fun `when asking for a page that doesn't follow structure, then should return null`() {
        val doc = DocumentProvider().fromString("<html></html>")

        assertThat(FundamentalKotlinParser().getIsbnFrom(doc), nullValue())
    }
}