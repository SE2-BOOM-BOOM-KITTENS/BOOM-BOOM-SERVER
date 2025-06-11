package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID


class CatComboEffectHandlerTest {

    private lateinit var game: GameLogic
    private lateinit var player: Player
    private lateinit var target: Player
    private lateinit var handler: CatComboEffectHandler

    @BeforeEach
    fun setup() {
        player = Player(name = "Tester")
        target = Player(name = "Opfer")
        game = GameLogic(UUID.randomUUID(), mutableListOf(player, target))
        player.playerHand.cards.clear()
        target.playerHand.cards.clear()
        handler = CatComboEffectHandler(game) { _, _ -> }
    }

    @Test
    fun `2 gleiche Cat Karten stehlen zufaellige Karte`() {
        target.playerHand.addCard(Card(CardType.BLANK))
        val cards = listOf(Card(CardType.CAT_TACO), Card(CardType.CAT_TACO))

        handler.applyCombo(player, cards, target)

        assertEquals(1, player.playerHand.getCardAmount())
        assertEquals(0, target.playerHand.getCardAmount())
    }

    @Test
    fun `3 gleiche Cat Karten stehlen Defuse`() {
        target.playerHand.addCard(Card(CardType.DEFUSE))
        val cards = List(3) { Card(CardType.CAT_BEARD) }

        handler.applyCombo(player, cards, target)

        assertTrue(player.playerHand.containsCardType(CardType.DEFUSE))
    }

    @Test
    fun `5 verschiedene Cat Karten triggern Ablagestapel Auswahl`() {
        val discardTypes = listOf(
            CardType.CAT_TACO,
            CardType.CAT_BEARD,
            CardType.CAT_HAIRY_POTATO,
            CardType.CAT_RAINBOW_RALPHING,
            CardType.CAT_CATERMELON
        )
        discardTypes.forEach { game.discardPile.add(Card(it)) }
        val cards = discardTypes.map { Card(it) }

        var messageSent = false
        handler = CatComboEffectHandler(game) { _, _ -> messageSent = true }

        handler.applyCombo(player, cards, null)

        assertTrue(messageSent)
    }

    @Test
    fun `Ungueltige Cat-Kombination wird ignoriert`() {
        val player = Player(name = "Tester")
        val gameLogic = GameLogic(UUID.randomUUID(), mutableListOf(player))
        val handler = CatComboEffectHandler(gameLogic) { _, _ -> }

        val cards = listOf(
            Card(CardType.CAT_TACO),
            Card(CardType.CAT_BEARD),
            Card(CardType.CAT_TACO),
            Card(CardType.CAT_TACO)
        )

        assertDoesNotThrow {
            handler.applyCombo(player, cards, null)
        }
    }

    @Test
    fun `2er Cat-Kombo ohne targetId wirft Exception`() {
        val cards = listOf(
            Card(CardType.CAT_TACO),
            Card(CardType.CAT_TACO)
        )

        assertThrows<IllegalArgumentException> {
            handler.applyCombo(player, cards, null)
        }
    }

    @Test
    fun `3er Cat-Kombo ohne targetId wirft Exception`() {
        val cards = listOf(
            Card(CardType.CAT_BEARD),
            Card(CardType.CAT_BEARD),
            Card(CardType.CAT_BEARD)
        )

        assertThrows<IllegalArgumentException> {
            handler.applyCombo(player, cards, null)
        }
    }

    @Test
    fun `2er Cat-Kombo mit leerer Zielhand stiehlt keine Karte`() {
        target.playerHand.cards.clear() // Zielspieler hat keine Karte
        val cards = listOf(Card(CardType.CAT_TACO), Card(CardType.CAT_TACO))

        handler.applyCombo(player, cards, target)

        assertEquals(0, player.playerHand.getCardAmount())
    }

    @Test
    fun `3er Cat-Kombo ohne DEFUSE beim Ziel bringt nichts`() {
        target.playerHand.cards.clear()
        target.playerHand.addCard(Card(CardType.BLANK))

        val cards = listOf(Card(CardType.CAT_BEARD), Card(CardType.CAT_BEARD), Card(CardType.CAT_BEARD))

        handler.applyCombo(player, cards, target)

        assertFalse(player.playerHand.containsCardType(CardType.DEFUSE))
    }

    @Test
    fun `5 verschiedene Cat-Karten zeigen leeren Ablagestapel`() {
        val cards = listOf(
            Card(CardType.CAT_TACO),
            Card(CardType.CAT_BEARD),
            Card(CardType.CAT_HAIRY_POTATO),
            Card(CardType.CAT_CATERMELON),
            Card(CardType.CAT_RAINBOW_RALPHING)
        )

        // discardPile ist leer, aber Methode sollte trotzdem aufgerufen werden
        var messageSent = false
        val handlerWithMockSend = CatComboEffectHandler(game) { _, _ -> messageSent = true }

        handlerWithMockSend.applyCombo(player, cards, null)

        assertTrue(messageSent)
    }

    @Test
    fun `resolvedTypes hat 1 Typ aber nicht genau 2 Karten`() {
        val cards = listOf(
            Card(CardType.CAT_TACO),
            Card(CardType.CAT_TACO),
            Card(CardType.CAT_TACO)
        ) // size == 3, toSet().size == 1

        assertDoesNotThrow {
            handler.applyCombo(player, cards, target)
        }

        assertEquals(0, player.playerHand.getCardAmount())
    }

    @Test
    fun `3er Kombo mit unterschiedlichen Typen wird ignoriert`() {
        val cards = listOf(
            Card(CardType.CAT_TACO),
            Card(CardType.CAT_TACO),
            Card(CardType.CAT_BEARD) // !=
        )

        assertDoesNotThrow {
            handler.applyCombo(player, cards, target)
        }

        assertEquals(0, player.playerHand.getCardAmount())
    }

    @Test
    fun `resolvedTypes hat gleichen Typ aber nicht genau 3 Karten`() {
        val cards = listOf(
            Card(CardType.CAT_BEARD),
            Card(CardType.CAT_BEARD)
        ) // size = 2

        assertDoesNotThrow {
            handler.applyCombo(player, cards, target)
        }

        assertEquals(0, player.playerHand.getCardAmount())
    }

    @Test
    fun `5er Kombo mit doppeltem Typ wird ignoriert`() {
        val cards = listOf(
            Card(CardType.CAT_TACO),
            Card(CardType.CAT_BEARD),
            Card(CardType.CAT_TACO), // doppelt
            Card(CardType.CAT_CATERMELON),
            Card(CardType.CAT_HAIRY_POTATO)
        )

        var triggered = false
        val handlerWithSend = CatComboEffectHandler(game) { _, _ -> triggered = true }

        handlerWithSend.applyCombo(player, cards, null)

        assertFalse(triggered)
    }

    @Test
    fun `zu wenige Karten fuer 5er Kombo werden ignoriert`() {
        val cards = listOf(
            Card(CardType.CAT_TACO),
            Card(CardType.CAT_BEARD),
            Card(CardType.CAT_CATERMELON),
            Card(CardType.CAT_HAIRY_POTATO)
        ) // nur 4 Karten

        var triggered = false
        val handlerWithSend = CatComboEffectHandler(game) { _, _ -> triggered = true }

        handlerWithSend.applyCombo(player, cards, null)

        assertFalse(triggered)
    }

    @Test
    fun `Feral Cat mit aliasType funktioniert in Combo`() {
        val cards = listOf(
            Card(CardType.FERAL_CAT, aliasType = CardType.CAT_TACO),
            Card(CardType.CAT_TACO)
        )
        target.playerHand.addCard(Card(CardType.BLANK))

        handler.applyCombo(player, cards, target)

        assertEquals(1, player.playerHand.getCardAmount())
        assertEquals(0, target.playerHand.getCardAmount())
    }

    @Test
    fun `FeralCatEffect gibt Hinweis bei direktem Ausspielen`() {
        val player = Player(name = "Tester")
        val playerList = mutableListOf(player)
        val gameLogic = GameLogic(UUID.randomUUID(), playerList)

        gameLogic.addPlayer(player.playerId, player.name)

        gameLogic.getPlayerHand(player.playerId)!!.addCard(Card(CardType.FERAL_CAT))

        assertThrows<IllegalStateException> {
            gameLogic.playCard(player.playerId, CardType.FERAL_CAT)
        }
    }

    @Test
    fun `FeralCat ohne aliasType wirft Exception`() {
        val player = Player(name = "Tester")
        val gameLogic = GameLogic(UUID.randomUUID(), mutableListOf(player))
        val handler = CatComboEffectHandler(gameLogic) { _, _ -> }

        val cards = listOf(
            Card(CardType.FERAL_CAT), // kein aliasType
            Card(CardType.CAT_TACO)
        )

        assertThrows<IllegalArgumentException> {
            handler.applyCombo(player, cards, null)
        }
    }
}