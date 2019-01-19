package br.com.gabryel.pocketguide.startup.parsers

import org.jsoup.nodes.Document

interface IsbnParser {

    fun getIsbnFrom(document: Document): String?
}