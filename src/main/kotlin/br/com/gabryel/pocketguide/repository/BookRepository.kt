package br.com.gabryel.pocketguide.repository

import br.com.gabryel.pocketguide.model.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository: JpaRepository<Book, String>