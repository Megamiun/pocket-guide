package br.com.gabryel.pocketguide.exception

class NotFoundException(item: String, conditions: Map<String, String>)
    : Exception("The given $item with $conditions was not found")

//private fun Map<String, String>.listConditions() =
//    map { "[${it.key}:${it.value}]" }
//        .joinToString()