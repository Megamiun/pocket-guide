package br.com.gabryel.pocketguide.controller

import br.com.gabryel.pocketguide.Application
import br.com.gabryel.pocketguide.model.Book
import br.com.gabryel.pocketguide.repository.BookRepository
import br.com.gabryel.pocketguide.service.BookService
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.nio.file.Paths


@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [Application::class])
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var bookRepository: BookRepository

    @BeforeEach
    fun clean() {
        bookRepository.deleteAll()
    }

    @Test
    fun `given a json book, when sending a post to books, then should return an id`() {
        mockMvc.perform(
            post("/books")
                .jsonFromFile("book-post.json")
        ).andExpect(jsonPath("id").exists())
    }

    @Test
    fun `given a json book, when sending a post to books, then should persist the book`() {
        mockMvc.perform(
            post("/books")
                .jsonFromFile("book-post.json")
        ).andReturn()

        assertThat(bookRepository.findAll().first().title, equalTo("Book title example"))
    }

    @Test
    fun `given a xml book, when sending a post to books, then should return UnsupportedMediaType`() {
        mockMvc.perform(
            post("/books")
                .xmlFromFile("book-post.json")
        ).andExpect(status().isUnsupportedMediaType)
    }

    @Test
    fun `given an book already registered, when sending a get for the book, then should return the book data`() {

        val book = bookRepository.save(Book("title", "isbn", "desc", "BR"))

        mockMvc.perform(
            get("/books/${book.id}")
        ).andExpect(jsonPath("title").value("title"))
    }

    @Test
    fun `given there are books registered, when sending a get for all the books, then should return a count`() {
        bookRepository.save(Book("title1", "isbn1", "desc1", "BR"))
        bookRepository.save(Book("title2", "isbn2", "desc2", "BR"))

        mockMvc.perform(
            get("/books")
        ).andExpect(jsonPath("numberBooks").value("2"))
    }

    @Test
    fun `given there are books registered, when sending a get for all the books, then should return all items on books`() {
        val book0 = bookRepository.save(Book("title1", "isbn1", "desc1", "BR"))
        val book1 = bookRepository.save(Book("title2", "isbn2", "desc2", "BR"))

        mockMvc.perform(get("/books"))
            .andExpect(jsonPath("books[0].id").value(book0.id))
            .andExpect(jsonPath("books[1].id").value(book1.id))
    }

    @Test
    fun `given there are no books registered, when sending a get for all the books, then should return an empty list`() {
        mockMvc.perform(get("/books"))
            .andExpect(jsonPath("books").isEmpty)
    }

    private fun MockHttpServletRequestBuilder.jsonFromFile(fileName: String) =
        fromFile(fileName, "application/json")

    private fun MockHttpServletRequestBuilder.xmlFromFile(fileName: String) =
        fromFile(fileName, "application/xml")


    private fun MockHttpServletRequestBuilder.fromFile(
        fileName: String,
        contentType: String
    ): MockHttpServletRequestBuilder {
        val uri = BookController::class.java.getResource(fileName).toURI()
        val fileContent = Paths.get(uri).toFile().readText()

        return contentType(contentType).content(fileContent)
    }
}