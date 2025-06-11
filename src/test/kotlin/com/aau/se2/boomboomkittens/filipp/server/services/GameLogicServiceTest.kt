package com.aau.se2.boomboomkittens.filipp.server.services

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.services.GameLogicService
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.messaging.simp.SimpMessagingTemplate

class GameLogicServiceTest {

    private lateinit var service: GameLogicService
    private val messagingTemplate: SimpMessagingTemplate = mock()

    private lateinit var player: Player
    private lateinit var target: Player

    @BeforeEach
    fun setup() {
        service = GameLogicService(messagingTemplate)

        val playerId = java.util.UUID.randomUUID()
        val targetId = java.util.UUID.randomUUID()

        service.joinGame(playerId, "Tester")
        service.joinGame(targetId, "Opfer")

        val game = service.getGameLogic()
        player = game.getPlayerById(playerId)!!
        target = game.getPlayerById(targetId)!!
    }

    @Test
    fun `playCatCombo should steal card with 2 same Cats`() {
        player.playerHand.cards.clear()
        target.playerHand.cards.clear()

        target.playerHand.addCard(Card(CardType.BLANK))

        val cards = listOf(Card(CardType.CAT_TACO), Card(CardType.CAT_TACO))

        service.playCatCombo(player.playerId, cards, target.playerId)

        assertEquals(1, player.playerHand.getCardAmount())
        assertEquals(0, target.playerHand.getCardAmount())
    }

    @Test
    fun `chooseFromDiscard should move card to player hand`() {
        val discardCard = Card(CardType.SHUFFLE)
        service.getGameLogic().discardPile.add(discardCard)

        service.chooseFromDiscard(player.playerId, CardType.SHUFFLE)

        val hand = service.getGameLogic().getPlayerById(player.playerId)?.playerHand
        assertTrue(hand!!.containsCardType(CardType.SHUFFLE))
    }
}
