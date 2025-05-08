package com.aau.se2.boomboomkittens.filipp.server.models.cards

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CardPileTest {

    private lateinit var cardPile: CardPile

    @BeforeEach
    fun setUp() {
        cardPile = CardPile()
    }

    @Test
    fun drawTest(){
        val card1 = Card("Card 1")
        val card2 = Card("Card 2")
        cardPile.insertAt(0,card1)
        cardPile.insertAt(1,card2)

        val drawn = cardPile.draw()
        assertEquals(card1, drawn)
        assertEquals(1,cardPile.size)
    }

    @Test
    fun drawAtTest(){
        val card1 = Card("Card 1")
        val card2 = Card("Card 2")
        cardPile.insertAt(0,card1)
        cardPile.insertAt(1,card2)

        val drawn = cardPile.drawAt(1)
        assertEquals(card2, drawn)
        assertEquals(1,cardPile.size)
    }

    @Test
    fun insertAtTest(){
        val card1 = Card("Card 1")
        val card2 = Card("Card 2")
        val card3 = Card("Card 3")

        cardPile.insertAt(0, card1)
        cardPile.insertAt(1, card2)
        cardPile.insertAt(2, card3)

        val pile = cardPile.getPileList()
        assertEquals(card1, pile[0])
        assertEquals(card2, pile[1])
        assertEquals(card3, pile[2])
        assertEquals(3, pile.size)
    }

//    @Test
//    fun shuffleTest(){
//        val card1 = Card("Card 1")
//        val card2 = Card("Card 2")
//        val card3 = Card("Card 3")
//        val card4 = Card("Card 4")
//        val card5 = Card("Card 5")
//        val card6 = Card("Card 6")
//
//        cardPile.insertAt(0, card1)
//        cardPile.insertAt(1, card2)
//        cardPile.insertAt(2, card3)
//        cardPile.insertAt(3, card4)
//        cardPile.insertAt(4, card5)
//        cardPile.insertAt(5, card6)
//
//        val originalOrder = cardPile.getPileList().toList()
//
//        cardPile.shuffle()
//        val shuffledOrder = cardPile.getPileList()
//
//        assertTrue(originalOrder != shuffledOrder)
//    }

    @Test
    fun isEmptyTest(){
        assertTrue(cardPile.isEmpty())
    }
}