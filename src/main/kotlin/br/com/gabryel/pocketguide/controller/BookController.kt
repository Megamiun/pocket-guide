package br.com.gabryel.pocketguide.controller

import br.com.gabryel.pocketguide.service.BookService
import org.springframework.web.bind.annotation.RestController

@RestController
class BookController(private val bookService: BookService)