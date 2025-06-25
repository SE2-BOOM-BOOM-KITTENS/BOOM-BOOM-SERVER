package com.aau.se2.boomboomkittens.filipp.server.controllers.webSocket

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.controllers.webSocket.GameLogicController
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.messages.PlayerMessage
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.playerHandshake.UserPrincipal
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.services.GameLogicService
import com.aau.se2.boomboomkittens.filipp.server.networkPacket.CardNetworkPacket
import com.aau.se2.boomboomkittens.filipp.server.services.LobbyService
import com.aau.se2.boomboomkittens.filipp.server.services.PlayerService
import com.aau.se2.boomboomkittens.filipp.server.services.TimeoutService
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.kotlin.argThat
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.eq
import org.mockito.kotlin.given
import org.mockito.kotlin.isNull
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import java.util.UUID
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameLogicControllerTest {

    private lateinit var controller: GameLogicController
    private lateinit var service: GameLogicService
    private lateinit var principal: UserPrincipal
    private val playerId = UUID.randomUUID()
    private val lobbyId = UUID.randomUUID()

    @MockBean
    lateinit var timeoutService: TimeoutService

    @MockBean
    lateinit var playerService: PlayerService

    @MockBean
    lateinit var lobbyService: LobbyService

    @BeforeEach
    fun setup(){
        service = mock()

        controller= GameLogicController(service, lobbyService)
        principal = mock {
            on { name } doReturn playerId.toString()
        }

    }

    @Test
    fun passTest(){
        val msg = PlayerMessage("Player","PASS",null,null,lobbyId)
        controller.processAction(msg, principal)
        verify(service).pass(lobbyId, playerId)
    }

    @Test
    fun playCardsTest(){
        val msg = PlayerMessage("Player","PLAY_CARDS","payload",null,lobbyId)
        controller.processAction(msg, principal)
        verify(service).playCards(lobbyId, playerId,"payload")
    }

    @Test
    fun cheatTest(){
        val msg = PlayerMessage("Player","CHEAT","payload",null,lobbyId)
        controller.processAction(msg, principal)
        verify(service).cheatDuplicate(lobbyId, playerId,"payload")
    }

    @Test
    fun checkCheatTest(){
        val msg = PlayerMessage("Player","CHECK_CHEAT","payload",null,lobbyId)
        controller.processAction(msg, principal)
        verify(service).checkIfDuplicate(lobbyId, playerId,"payload")
    }

    @Test
    fun exitTest(){
        val msg = PlayerMessage("Player","EXIT","payload",null,lobbyId)
        controller.processAction(msg, principal)
        verify(service).exitPlayer(lobbyId, playerId)
    }

    @Test
    fun handTest(){
        val msg = PlayerMessage("Player","HAND","payload",null,lobbyId)
        controller.processAction(msg, principal)
        verify(service).getPlayerHand(lobbyId, playerId)
    }

    @Test
    fun initTest(){
        val msg = PlayerMessage("Player","INIT","payload",null,lobbyId)
        controller.processAction(msg, principal)
        verify(service).getInitState(lobbyId, playerId)
    }

    @Test
    fun explodeTest(){
        val msg = PlayerMessage("Player","EXPLODE","payload",null,lobbyId)
        controller.processAction(msg, principal)
        verify(service).explodePlayer(lobbyId, playerId)
    }

    @Test
    fun catComboTest(){
        val networkCards = listOf(CardNetworkPacket("Feral", CardType.FERAL_CAT))
        val msg = PlayerMessage("Player","CAT_COMBO",networkCards, null,lobbyId)

        controller.processAction(msg, principal)

        verify(service).playCatCombo(
            eq(lobbyId),
            eq(playerId),
            argThat { this[0].type == CardType.FERAL_CAT },
            isNull()
        )
    }

    @Test
    fun chooseFromDiscard(){
        val card = CardNetworkPacket("Feral", CardType.FERAL_CAT)
        val msg = PlayerMessage("Player","CHOOSE_FROM_DISCARD",listOf(card), null,lobbyId)

        controller.processAction(msg, principal)
        verify(service).chooseFromDiscard(lobbyId,playerId,CardType.FERAL_CAT)
    }

    @Test
    fun chooseFromDiscardNullTest(){
        val msg = PlayerMessage("Player","CHOOSE_FROM_DISCARD",emptyList<CardNetworkPacket>(),null,lobbyId)

        controller.processAction(msg, principal)
        verify(service).sendUserError(eq(lobbyId),eq(playerId),any())
    }

    @Test
    fun unknownActionTest(){
        val msg = PlayerMessage("Player","UNKNOWN",null,null,lobbyId)

        controller.processAction(msg, principal)
        verify(service).sendUserError(eq(lobbyId), eq(playerId),any())
    }

    @LocalServerPort
    private var port: Int = 0

    private val messageQueue = LinkedBlockingQueue<String>()

    private fun connectWithName(id: String): StompSession {
        val stompClient = WebSocketStompClient(StandardWebSocketClient())
        stompClient.messageConverter = MappingJackson2MessageConverter()

        val url = "ws://localhost:$port/game?id=$id"

        val session = stompClient.connectAsync(url, object : StompSessionHandlerAdapter(){})
            .get(1, TimeUnit.SECONDS)
        return session
    }

    @Test
    fun addPlayerTest(){
        given(playerService.getPlayer(playerId)).willReturn(Player(playerId=playerId,name="Dummy"))
        val session = connectWithName(playerId.toString())

        session.subscribe("/topic/session", object: StompSessionHandlerAdapter() {
            override fun handleFrame(headers: StompHeaders, payload: Any?) {
                messageQueue.offer(payload.toString())
            }
        })

        val playerMessage = mapOf(
            "playerName" to "Dummy",
            "action" to "PASS",
            "cardsPlayed" to emptyList<String>()
        )
        session.send("app/addPlayer",playerMessage)

        // fixme missing assertion
    }

}