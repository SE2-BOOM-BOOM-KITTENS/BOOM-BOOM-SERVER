package com.aau.se2.boomboomkittens.filipp.server.controllers.webSocket

import com.aau.se2.boomboomkittens.filipp.server.services.LobbyService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/lobby")
class LobbyWebSocketController(
    private val lobbyService: LobbyService,
    private val messagingTemplate: SimpMessagingTemplate
) {

    data class ReadyMessage(val lobbyId:String, val playerId: String)
    data class LobbyStatusUpdate(
        val lobbyId: String,
        val playersReady: Int,
        val allReady: Boolean
    )

    @MessageMapping("lobbies")
    fun requestLobbies(){
        val lobbies = lobbyService.getLobbies()
        messagingTemplate.convertAndSend("/topic/lobbies",lobbies)
    }

    @MessageMapping("/ready")
    @SendTo("/topic/lobby/status/{lobbyId}")
    fun handlePlayerReady(message: ReadyMessage): LobbyStatusUpdate {
        println("Player ${message.playerId} is ready in lobby ${message.lobbyId}")

        return LobbyStatusUpdate(
            lobbyId = message.lobbyId,
            playersReady = 3,
            allReady = false
        )
    }

    fun broadcastLobbyUpdate(){
        val lobbies = lobbyService.getLobbies()
        messagingTemplate.convertAndSend("/topic/lobbies", lobbies)
    }
}