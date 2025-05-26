package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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
}