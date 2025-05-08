package com.aau.se2.boomboomkittens.game.model

class Hand {
    private val cards = mutableListOf<Card>()

    fun add(card: Card) {
        cards.add(card)
    }

    fun addAll(newCards: List<Card>) {
        cards.addAll(newCards)
    }

    fun remove(card: Card) {
        cards.remove(card)
    }

    fun removeAll(toRemove: List<Card>) {
        cards.removeAll(toRemove)
    }

    fun isEmpty(): Boolean = cards.isEmpty()

    fun size(): Int = cards.size

    fun getCards(): List<Card> = cards.toList()

    fun randomCard(): Card? = cards.randomOrNull()

    fun findByType(type: CardType): List<Card> =
        cards.filter { it.type == type }

    fun hasComboOfSameType(count: Int): Boolean =
        cards.groupBy { it.type }.any { it.value.size >= count }

    fun hasComboOfDifferentTypes(count: Int): Boolean =
        cards.map { it.type }.distinct().size >= count
}