package br.com.gabryel.pocketguide.startup.parsers

import org.jsoup.nodes.Document

class ManningParser: IsbnParser {

    override fun getIsbnFrom(document: Document): String? {
        val infos = document.selectFirst(".product-info").children().asSequence()

        return infos
            .map { it.text() }
            .firstOrNull { it.startsWith("ISBN") }
            ?.split(" ")?.drop(1)?.firstOrNull()
    }
}