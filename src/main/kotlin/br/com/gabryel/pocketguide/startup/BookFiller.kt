package br.com.gabryel.pocketguide.startup

import br.com.gabryel.pocketguide.model.Book
import br.com.gabryel.pocketguide.repository.BookRepository
import br.com.gabryel.pocketguide.startup.parsers.*
import org.jsoup.nodes.Element
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component


@Component
class BookFiller(private val bookRepository: BookRepository): ApplicationListener<ApplicationReadyEvent> {

    private val parsers = mapOf(
        "manning.com" to ManningParser(),
        "packtpub.com" to PacktPubParser(),
        "amazon.com" to AmazonParser(),
        "fundamental-kotlin.com" to FundamentalKotlinParser(),
        "kuramkitap.com" to KuramkitapParser()
    )

    private val provider = DocumentProvider()

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        val doc = provider.fromUrl("https://kotlinlang.org/docs/books.html")

        var start = doc.selectFirst("article[role=main]").children().first()

        val sequence = generateSequence {
            if (start == null) return@generateSequence null

            val languageNode = start.nextElementSibling()
            val photoNode = languageNode.nextElementSibling()

            val name = start.text()
            val language = languageNode.text()

            val (currentParagraph, description) = photoNode.nextElementSibling().getDescription()
            start = currentParagraph

            val book = Book(name, description, language)

            photoNode.getIsbn()?.let {
                return@generateSequence book.copy(isbn = it)
            }

            book
        }


        sequence.forEach { bookRepository.save(it) }
    }

    private fun Element.getDescription(): Pair<Element?, String> {
        var currentParagraph: Element? = this

        val paragraphs = mutableListOf<String>()

        while (currentParagraph?.tagName() == "p") {
            paragraphs += currentParagraph.text()
            currentParagraph = currentParagraph.nextElementSibling()
        }

        val description = paragraphs.joinToString("\n")
        return Pair(currentParagraph, description)
    }

    private fun Element.getIsbn(): String? {
        val link = attr("href")

        val parser = parsers
            .entries.find { link.contains(it.key) }
            ?.value

       return parser?.getIsbnFrom(provider.fromUrl(link))
    }
}