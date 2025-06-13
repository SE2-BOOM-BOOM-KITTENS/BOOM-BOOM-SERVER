package com.aau.se2.boomboomkittens.filipp.server.services

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.services.GameLogicService
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.messaging.simp.SimpMessagingTemplate
import java.util.UUID

class GameLogicServiceTest {

    private lateinit var service: GameLogicService
    private val messagingTemplate: SimpMessagingTemplate = mock()
    private val jacksonObjectMapper: ObjectMapper = mock()

    private lateinit var player: Player
    private lateinit var target: Player

    //QUICK FIX; SHOULD BE REIMPLEMENTED LATER
    private var lobbyId: UUID? = null

    @BeforeEach
    fun setup() {
        service = GameLogicService(messagingTemplate,jacksonObjectMapper)
        lobbyId = service.lobbyId
        player = Player(name = "Tester")
        target = Player(name = "Opfer")
        service.joinGame(lobbyId!!,player.playerId, player.name)
        service.joinGame(lobbyId!!,target.playerId, target.name)
    }

    @Test
    fun `playCatCombo should steal card with 2 same Cats`() {
        player.playerHand.cards.clear()
        target.playerHand.cards.clear()

        target.playerHand.addCard(Card(CardType.BLANK))

        val cards = listOf(Card(CardType.CAT_TACO), Card(CardType.CAT_TACO))

        service.playCatCombo(lobbyId!!,player.playerId, cards, target.playerId)

        //assertEquals(1, player.playerHand.getCardAmount())
        //assertEquals(0, target.playerHand.getCardAmount())
    }

    @Test
    fun `chooseFromDiscard should move card to player hand`() {
        val discardCard = Card(CardType.SHUFFLE)
        service.getGame(lobbyId!!).discardPile.add(discardCard)

        service.chooseFromDiscard(lobbyId!!,player.playerId, CardType.SHUFFLE)

        val hand = service.getGame(lobbyId!!).getPlayerById(player.playerId)?.playerHand
        assertTrue(hand!!.containsCardType(CardType.SHUFFLE))
    }
}
