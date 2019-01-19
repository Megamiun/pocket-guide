package br.com.gabryel.pocketguide.startup

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.io.File
import java.nio.charset.StandardCharsets

class DocumentProvider {

    private val userAgent = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:64.0) Gecko/20100101 Firefox/64.0"

    fun fromUrl(url: String): Document {
        return Jsoup.connect(url).header("User-Agent", userAgent).get()
    }

    fun fromString(content: String): Document = Jsoup.parse(content)
}