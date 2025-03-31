package com.aau.se2.boomboomkittens.filipp.server.controllers

import com.aau.se2.boomboomkittens.filipp.server.controllers.clientImpl.StompFrameClient
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.messaging.converter.StringMessageConverter
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.TimeUnit

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SessionControllerTest {

    @LocalServerPort
    private val port = 0

    private val WEBSOCKET_URI = "ws://localhost:%d/game"
    private val WEBSOCKET_TOPIC = "/topic/session"

    private val messages:BlockingQueue<String> = LinkedBlockingDeque()

    @Test
    fun testWebSocketConnection(){
        val session = initStompSession()

        val message = "Hello, WebSocket"
        session.send("/app/session", message)

        val expectedResponse = "WebSocket works!"
        assertThat(messages.poll(1,TimeUnit.SECONDS)).isEqualTo(expectedResponse    )
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