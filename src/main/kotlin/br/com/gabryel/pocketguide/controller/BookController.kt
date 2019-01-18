package br.com.gabryel.pocketguide.controller

import br.com.gabryel.pocketguide.model.Book
import br.com.gabryel.pocketguide.model.BookSummary
import br.com.gabryel.pocketguide.service.BookService
import org.springframework.web.bind.annotation.*

@RestController
class BookController(private val bookService: BookService) {

    @GetMapping("books")
    fun getAllBooks(): BookSummary = bookService.getAllBooks()

    @PostMapping("books")
    fun saveBook(@RequestBody book: Book): Book = bookService.saveBook(book)

    @GetMapping("books/{id}")
    fun getBookById(@PathVariable("id") id: String): Book = bookService.getBookById(id)
}