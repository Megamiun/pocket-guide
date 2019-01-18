package br.com.gabryel.pocketguide.exception

class RepeatedItemException(item: String, conditions: Map<String, String>)
    : Exception("The $item with id $conditions already exists")