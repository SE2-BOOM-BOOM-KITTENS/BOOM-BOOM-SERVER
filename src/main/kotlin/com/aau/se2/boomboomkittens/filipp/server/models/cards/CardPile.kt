package com.aau.se2.boomboomkittens.filipp.server.models.cards

class CardPile {
    private val pile:MutableList<Card> = mutableListOf()

    val size: Int
    get() = pile.size

    fun draw():Card{
        return pile.removeFirst()
    }

    fun drawAt(index:Int):Card{
        return pile.removeAt(index)
    }

    fun insertAt(index:Int, card: Card) {
        pile.add(index, card)
    }

    fun getPileList(): MutableList<Card>{
        return pile
    }

    fun shuffle(){
        pile.shuffle()
    }

    fun isEmpty(): Boolean = pile.isEmpty()
}