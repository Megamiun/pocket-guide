package br.com.gabryel.pocketguide.service

import br.com.gabryel.pocketguide.exception.NotFoundException
import br.com.gabryel.pocketguide.exception.RepeatedItemException
import br.com.gabryel.pocketguide.model.Book
import br.com.gabryel.pocketguide.model.BookSummary
import br.com.gabryel.pocketguide.repository.BookRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class BookService(private val bookRepository: BookRepository) {

    fun getAllBooks(): BookSummary {
        val all = bookRepository.findAll()

        return BookSummary(all.count(), all)
    }

    fun getBookById(id: String): Book {
        return bookRepository.findByIdOrNull(id)
            ?: throw NotFoundException("book", mapOf("id" to id))
    }

    fun saveBook(book: Book): Book {
        if (bookRepository.existsById(book.id)) {
            throw RepeatedItemException("book", mapOf("id" to book.id))
        }

        return bookRepository.save(book)
    }
}