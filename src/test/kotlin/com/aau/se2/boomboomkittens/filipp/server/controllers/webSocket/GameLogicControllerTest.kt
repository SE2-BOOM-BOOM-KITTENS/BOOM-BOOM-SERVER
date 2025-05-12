package com.aau.se2.boomboomkittens.filipp.server.controllers.webSocket

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSession
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit


@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GameLogicControllerTest {

    @LocalServerPort
    private var port: Int = 0

    private val messageQueue = LinkedBlockingQueue<String>()

    private fun connectWithName(name: String): StompSession {
        val stompClient = WebSocketStompClient(StandardWebSocketClient())
        stompClient.messageConverter = MappingJackson2MessageConverter()

        val url = "ws://localhost:$port/game?name=$name"

        val session = stompClient.connectAsync(url, object : StompSessionHandlerAdapter(){})
            .get(1, TimeUnit.SECONDS)
        return session
    }

    @Test
    fun addPlayerTest(){
        val session = connectWithName("Filipp")

        session.subscribe("/topic/session", object: StompSessionHandlerAdapter() {
            override fun handleFrame(headers: StompHeaders, payload: Any?) {
                messageQueue.offer(payload.toString())
            }
        })

        val playerMessage = mapOf(
            "playerName" to "Filipp",
            "action" to "PASS",
            "cardsPlayed" to emptyList<String>()
        )
        session.send("app/addPlayer",playerMessage)
    }

}