package br.com.gabryel.pocketguide.startup.parsers

import org.jsoup.nodes.Document

class PacktPubParser: IsbnParser {
    override fun getIsbnFrom(document: Document): String? {
        return document.selectFirst("span[itemprop=isbn]")?.text()
    }
}