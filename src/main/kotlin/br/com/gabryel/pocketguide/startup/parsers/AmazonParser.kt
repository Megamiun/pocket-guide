package br.com.gabryel.pocketguide.startup.parsers

import org.jsoup.nodes.Document

class AmazonParser: IsbnParser {

    override fun getIsbnFrom(document: Document): String? {
        val infos = document
            .selectFirst("#productDetailsTable ul")
            ?.children()?.asSequence()
            ?: return null

        return infos
            .map { it.text() }
            .find { it.startsWith("ISBN") }
            ?.split(":")
            ?.getOrNull(1)
            ?.trim()
    }
}