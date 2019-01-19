package br.com.gabryel.pocketguide.model

import com.fasterxml.jackson.annotation.JsonAlias
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Lob

@Entity
data class Book(
    val title: String,
    @Column(columnDefinition="TEXT")
    val description: String,
    val language: String,
    @JsonAlias("ISBN") val isbn: String = "Unavailable",
    @Id val id: String = UUID.randomUUID().toString()
)