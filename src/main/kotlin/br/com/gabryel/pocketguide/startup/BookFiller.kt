package br.com.gabryel.pocketguide.startup

import br.com.gabryel.pocketguide.model.Book
import br.com.gabryel.pocketguide.repository.BookRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component


@Component
class BookFiller(
    private val bookStreamProvider: BookStreamProvider,
    private val bookRepository: BookRepository
): ApplicationListener<ApplicationReadyEvent> {

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        val doc = bookStreamProvider.getDocument()

        var start = doc.selectFirst("article[role=main]").children().first()

        val sequence = generateSequence {
            if (start == null) return@generateSequence null

            val name = start.text()

            val languageNode = start.nextElementSibling()
            val language = languageNode.text()

            val photoNode = languageNode.nextElementSibling()

            var currentParagraph = photoNode.nextElementSibling()

            val paragraphs = mutableListOf<String>()

            while (currentParagraph?.tagName() == "p") {
                paragraphs += currentParagraph.text()
                currentParagraph = currentParagraph.nextElementSibling()
            }

            start = currentParagraph

            Book(name, paragraphs.joinToString("\n"), language)
        }


        sequence.forEach { bookRepository.save(it) }
    }
}