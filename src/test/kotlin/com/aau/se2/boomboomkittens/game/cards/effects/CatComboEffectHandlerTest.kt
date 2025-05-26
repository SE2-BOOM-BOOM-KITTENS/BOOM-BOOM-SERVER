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
    fun `Cat-Kombos funktionieren korrekt`() {
        // 2 gleiche Karten - Ziel hat 1 Karte
        target.playerHand.addCard(Card(CardType.BLANK))
        val twoSame = listOf(Card(CardType.CAT_TACO), Card(CardType.CAT_TACO))
        handler.applyCombo(player, twoSame, target)
        assertEquals(1, player.playerHand.getCardAmount())

        // 3 gleiche Karten - Ziel hat DEFUSE
        player.playerHand.cards.clear()
        target.playerHand.cards.clear()
        target.playerHand.addCard(Card(CardType.DEFUSE))
        val threeSame = List(3) { Card(CardType.CAT_BEARD) }
        handler.applyCombo(player, threeSame, target)
        assertTrue(player.playerHand.containsCardType(CardType.DEFUSE))
    }

    @Test
    fun `Cat-Kombos ohne Zielspieler werfen Exception`() {
        val two = listOf(Card(CardType.CAT_TACO), Card(CardType.CAT_TACO))
        val three = List(3) { Card(CardType.CAT_BEARD) }

        assertThrows<IllegalArgumentException> { handler.applyCombo(player, two, null) }
        assertThrows<IllegalArgumentException> { handler.applyCombo(player, three, null) }
    }

    @Test
    fun `Kombos bringen nichts wenn Zielspieler keine passende Karte hat`() {
        // 2 gleiche Karten - Ziel leer
        target.playerHand.cards.clear()
        val two = listOf(Card(CardType.CAT_TACO), Card(CardType.CAT_TACO))
        handler.applyCombo(player, two, target)
        assertEquals(0, player.playerHand.getCardAmount())

        // 3 gleiche Karten - Ziel hat BLANK
        target.playerHand.cards.clear()
        target.playerHand.addCard(Card(CardType.BLANK))
        val three = List(3) { Card(CardType.CAT_BEARD) }
        handler.applyCombo(player, three, target)
        assertFalse(player.playerHand.containsCardType(CardType.DEFUSE))
    }

    @Test
    fun `5 verschiedene Cat-Karten triggern discard Auswahl`() {
        val cards = listOf(
            Card(CardType.CAT_TACO),
            Card(CardType.CAT_BEARD),
            Card(CardType.CAT_HAIRY_POTATO),
            Card(CardType.CAT_CATERMELON),
            Card(CardType.CAT_RAINBOW_RALPHING)
        )

        var called = 0
        val handlerMock = CatComboEffectHandler(game) { _, _ -> called++ }

        // leerer Ablagestapel
        handlerMock.applyCombo(player, cards, null)
        // Stapel mit Karten
        game.discardPile.add(Card(CardType.DEFUSE))
        handlerMock.applyCombo(player, cards, null)

        assertEquals(2, called)
    }

    @Test
    fun `Ungueltige Cat-Kombination wird ignoriert`() {
        val badCombo = listOf(
            Card(CardType.CAT_TACO),
            Card(CardType.CAT_BEARD),
            Card(CardType.CAT_TACO),
            Card(CardType.CAT_TACO)
        )

        assertDoesNotThrow {
            handler.applyCombo(player, badCombo, null)
        }
    }

    @Test
    fun `FeralCat mit aliasType funktioniert`() {
        target.playerHand.addCard(Card(CardType.BLANK))
        val cards = listOf(
            Card(CardType.FERAL_CAT, aliasType = CardType.CAT_TACO),
            Card(CardType.CAT_TACO)
        )

        handler.applyCombo(player, cards, target)

        assertEquals(1, player.playerHand.getCardAmount())
        assertEquals(0, target.playerHand.getCardAmount())
    }

    @Test
    fun `FeralCat ohne aliasType wirft Exception`() {
        val cards = listOf(
            Card(CardType.FERAL_CAT),
            Card(CardType.CAT_TACO)
        )

        assertThrows<IllegalArgumentException> {
            handler.applyCombo(player, cards, null)
        }
    }
}