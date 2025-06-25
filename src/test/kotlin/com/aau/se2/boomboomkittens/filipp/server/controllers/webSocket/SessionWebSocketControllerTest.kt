package com.aau.se2.boomboomkittens.filipp.server.controllers.webSocket

import com.aau.se2.boomboomkittens.filipp.server.controllers.clientImpl.StompFrameClient
import com.aau.se2.boomboomkittens.filipp.server.services.PlayerService
import com.aau.se2.boomboomkittens.filipp.server.services.TimeoutService
import com.aau.se2.boomboomkittens.game.player.Player
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.given
import org.mockito.kotlin.willReturn
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.messaging.converter.StringMessageConverter
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import java.util.UUID
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.TimeUnit

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SessionWebSocketControllerTest {

    @LocalServerPort
    private val port = 0

    @MockBean
    lateinit var timeoutService: TimeoutService

    @MockBean
    lateinit var playerService: PlayerService

    private var testUUID: UUID = UUID.randomUUID()
    private val testPlayer: Player = Player(playerId = testUUID, name = "Dummy")
    private val WEBSOCKET_URI = "ws://localhost:%d/game?id=$testUUID"
    private val WEBSOCKET_TOPIC = "/topic/session"

    private val messages:BlockingQueue<String> = LinkedBlockingDeque()

    @Test
    fun testWebSocketConnection(){

        given(playerService.getPlayer(testUUID)).willReturn(testPlayer)
        val session = initStompSession()

        val message = "Hello, WebSocket"
        session.send("/app/session", message)

        val expectedResponse = "WebSocket works!"
        assertThat(messages.poll(1,TimeUnit.SECONDS)).isEqualTo(expectedResponse)
    }

    private fun initStompSession(): StompSession{
        val stompClient = WebSocketStompClient(StandardWebSocketClient())
        stompClient.messageConverter = StringMessageConverter()

        val session = stompClient.connectAsync(
            String.format(WEBSOCKET_URI, port),
            object : StompSessionHandlerAdapter(){}
        ).get(1, TimeUnit.SECONDS)

        session.subscribe(WEBSOCKET_TOPIC, object: StompFrameClient(messages){})

        return session
    }
}