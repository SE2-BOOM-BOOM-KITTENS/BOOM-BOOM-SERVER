package com.aau.se2.boomboomkittens.game.model.player

import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.PlayerHand
import org.junit.jupiter.api.Assertions
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
        card1 = Card(CardType.BLANK)
        card2 = Card(CardType.BLANK)
    }

    @Test
    fun addCardTest(){
        playerHand.addCard(card1)
        Assertions.assertEquals(1, playerHand.getCardAmount())
        Assertions.assertTrue(playerHand.cards.contains(card1))
    }

    @Test
    fun removeCardTest(){
        playerHand.addCard(card1)
        playerHand.removeCard(card1)
        Assertions.assertEquals(0, playerHand.getCardAmount())
        Assertions.assertFalse(playerHand.cards.contains(card1))
    }

    @Test
    fun getRandomCardsTest(){
        playerHand.addCard(card1)
        playerHand.addCard(card2)

        val randomCard = playerHand.getRandomCard()
        Assertions.assertTrue(randomCard == card1 || randomCard == card2)
    }

    @Test
    fun getCardAmountTest(){
        Assertions.assertEquals(0, playerHand.getCardAmount())
        playerHand.addCard(card1)
        Assertions.assertEquals(1, playerHand.getCardAmount())
        }
}