package br.com.gabryel.pocketguide.model

import com.fasterxml.jackson.annotation.JsonAlias
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Book(
    val title: String,
    @JsonAlias("ISBN") val isbn: String,
    val description: String,
    val language: String,
    @Id val id: String = UUID.randomUUID().toString()
)