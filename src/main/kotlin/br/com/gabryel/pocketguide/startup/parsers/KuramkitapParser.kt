package br.com.gabryel.pocketguide.startup.parsers

import org.jsoup.nodes.Document


class KuramkitapParser: IsbnParser {

    override fun getIsbnFrom(document: Document): String? {
        return document
            .select(".prd-features-label")
            .find { it.text() == "Stok Kodu" }
            ?.lastElementSibling()
            ?.text()
    }
}