package br.com.gabryel.pocketguide.startup.parsers

import org.jsoup.nodes.Document

class FundamentalKotlinParser: IsbnParser {

    override fun getIsbnFrom(document: Document): String? {
        return document.selectFirst(".scondary_content h2 span")?.text()
    }
}