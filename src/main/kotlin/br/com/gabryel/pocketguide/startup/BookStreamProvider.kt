package br.com.gabryel.pocketguide.startup

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component

@Component
class BookStreamProvider {
    fun getDocument(): Document {
        return Jsoup.connect("https://kotlinlang.org/docs/books.html").get()
    }
}