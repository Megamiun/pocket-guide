package br.com.gabryel.pocketguide.service

import br.com.gabryel.pocketguide.repository.BookRepository
import org.springframework.stereotype.Component

@Component
class BookService(private val bookRepository: BookRepository)