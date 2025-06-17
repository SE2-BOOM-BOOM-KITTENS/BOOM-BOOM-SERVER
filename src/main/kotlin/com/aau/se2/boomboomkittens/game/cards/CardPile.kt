package com.aau.se2.boomboomkittens.game.cards

class CardPile {
    private val pile:MutableList<Card> = mutableListOf()

    val size: Int
    get() = pile.size

    fun add(card: Card) {
        pile.add(card)
    }

    fun draw(): Card {
        return pile.removeFirst()
    }

    fun drawAt(index:Int): Card {
        return pile.removeAt(index)
    }

    fun insertAt(index:Int, card: Card) {
        pile.add(index, card)
    }

    fun getCardsSnapshot(): List<Card> = pile.toList()

    fun getPileList(): MutableList<Card> {
        return pile
    }

    fun take(n: Int):List<Card>{
        val cards: List<Card> = pile.take(n)
        return cards
    }

    fun removeCard(card: Card): Boolean {
        return pile.remove(card)
    }

    fun clear() {
        pile.clear()
    }

    fun addAll(cards: List<Card>) {
        pile.addAll(cards)
    }

    fun removeFirst(): Card{
        return pile.removeFirst()
    }

    fun shuffle(){
        pile.shuffle()
    }

    fun clear(){
        pile.clear()
    }

    fun isEmpty(): Boolean = pile.isEmpty()
}