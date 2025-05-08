package com.aau.se2.boomboomkittens.filipp.server.models.player.playerCircle

import com.aau.se2.boomboomkittens.filipp.server.models.cards.Card
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

class PlayerHandTest {

    private lateinit var playerHand: PlayerHand
    private lateinit var card1: Card
    private lateinit var card2: Card

    @BeforeEach
    fun setUp() {
        val playerId = UUID.randomUUID()
        playerHand = PlayerHand(playerId)
        card1 = Card("Card 1")
        card2 = Card("Card 2")
    }

    @Test
    fun addCardTest(){
        playerHand.addCard(card1)
        assertEquals(1, playerHand.getCardAmount())
        assertTrue(playerHand.cards.contains(card1))
    }

    @Test
    fun removeCardTest(){
        playerHand.addCard(card1)
        playerHand.removeCard(card1)
        assertEquals(0, playerHand.getCardAmount())
        assertFalse(playerHand.cards.contains(card1))
    }

    @Test
    fun getRandomCardsTest(){
        playerHand.addCard(card1)
        playerHand.addCard(card2)

        val randomCard = playerHand.getRandomCard()
        assertTrue(randomCard == card1 || randomCard == card2)
    }

    @Test
    fun getCardAmountTest(){
        assertEquals(0,playerHand.getCardAmount())
        playerHand.addCard(card1)
        assertEquals(1,playerHand.getCardAmount())
        }
}