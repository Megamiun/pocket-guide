package br.com.gabryel.pocketguide.startup.parsers

import org.jsoup.nodes.Document

class ManningParser: IsbnParser {

    override fun getIsbnFrom(document: Document): String? {
        val infos = document
            .select(".product-info ul")
            .asSequence()
            .flatMap { it.children().asSequence() }

        return infos
            .map { it.text() }
            .find { it.startsWith("ISBN") }
            ?.split(" ")
            ?.getOrNull(1)
    }
}