package com.aau.se2.boomboomkittens.filipp.server.services

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.services.GameLogicService
import com.aau.se2.boomboomkittens.game.Lobby
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.springframework.messaging.simp.SimpMessagingTemplate
import java.util.*

class GameLogicServiceTest {

    private lateinit var service: GameLogicService
    private val messagingTemplate: SimpMessagingTemplate = mock()
    private val jacksonObjectMapper: ObjectMapper = mock()

    private lateinit var player: Player
    private lateinit var target: Player
    private lateinit var lobby: Lobby

    @BeforeEach
    fun setup() {
        val timeoutService = TimeoutService(service)
        service = GameLogicService(messagingTemplate,jacksonObjectMapper, timeoutService)

        player = Player(name = "Tester")
        target = Player(name = "Opfer")
        lobby = Lobby(creator = player, maxPlayers = 4)
        service.createGame(lobby)
        service.joinGame(lobby.id,player.playerId, player.name)
        service.joinGame(lobby.id,target.playerId, target.name)
    }

    @Test
    fun getGameThrowTest(){

        assertThrows(IllegalArgumentException::class.java){
            service.getGame(UUID.randomUUID())
        }
    }

    @Test
    fun passTest(){
    }

    @Test
    fun playCardsTest(){

    }

    @Test
    fun cheatDuplicateTest(){

    }

    @Test
    fun checkIfDuplicateTest(){
    }

    @Test
    fun exitPlayerTest(){

    }

    @Test
    fun getInitState(){

    }

    @Test
    fun explodePlayerTest(){

    }

    @Test
    fun endTurnTest(){

    }

    @Test
    fun sendGameStateTest(){

    }

    @Test
    fun sendErrorTest(){

    }

    @Test
    fun `playCatCombo should steal card with 2 same Cats`() {
        player.playerHand.cards.clear()
        target.playerHand.cards.clear()

        target.playerHand.addCard(Card(CardType.BLANK))

        val cards = listOf(Card(CardType.CAT_TACO), Card(CardType.CAT_TACO))

        service.playCatCombo(lobby.id,player.playerId, cards, target.playerId)

        //assertEquals(1, player.playerHand.getCardAmount())
        //assertEquals(0, target.playerHand.getCardAmount())
    }

    @Test
    fun `chooseFromDiscard should move card to player hand`() {
        val discardCard = Card(CardType.SHUFFLE)

        service.getGame(lobby.id).cardLogic.discardPile.add(discardCard)

        service.chooseFromDiscard(lobby.id,player.playerId, CardType.SHUFFLE)

        val hand = service.getGame(lobby.id).getPlayerById(player.playerId)?.playerHand
        assertTrue(hand!!.containsCardType(CardType.SHUFFLE))
    }
}
