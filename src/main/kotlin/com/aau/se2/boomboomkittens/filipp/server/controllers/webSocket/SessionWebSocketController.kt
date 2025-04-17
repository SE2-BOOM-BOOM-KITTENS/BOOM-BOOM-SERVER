package com.aau.se2.boomboomkittens.filipp.server.controllers.webSocket

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class SessionWebSocketController {

    @MessageMapping("/session")
    @SendTo("/topic/session")
    fun sendResponse(): String {
        return "WebSocket works!"
    }
}