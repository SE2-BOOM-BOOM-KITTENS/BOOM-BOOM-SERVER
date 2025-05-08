package com.aau.se2.boomboomkittens.filipp.server.controllers.webSocket

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import jakarta.websocket.Session
import java.util.concurrent.ConcurrentHashMap
import org.json.JSONObject

@Controller
class SessionWebSocketController {

    @MessageMapping("/session")
    @SendTo("/topic/session")
    fun sendResponse(): String {
        return "WebSocket works!"
    }

    companion object {
        private val sessionMap: MutableMap<String, Session> = ConcurrentHashMap()
    }

    fun sendToClient(playerId: String, payload: Map<String, Any>) {
        val json = JSONObject(payload).toString()
        sendJsonToClient(playerId, json)
    }

    fun sendJsonToClient(playerId: String, message: String) {
        val session = sessionMap[playerId]
        session?.basicRemote?.sendText(message)
    }
}