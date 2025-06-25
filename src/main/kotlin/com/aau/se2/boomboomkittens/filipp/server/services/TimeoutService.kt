package com.aau.se2.boomboomkittens.filipp.server.services

import TimeoutLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.messages.ServerMessage
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.services.GameLogicService
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.player.Player
import org.slf4j.LoggerFactory
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.*

@Service
class TimeoutService(
    val messagingTemplate: SimpMessagingTemplate
) {
    private val timers = mutableMapOf<UUID, TimeoutLogic>()
    private val games = mutableMapOf<UUID, GameLogic>()
    private val logger = LoggerFactory.getLogger(GameLogicService::class.java)

    fun createTimeout(lobbyId: UUID, gameLogic: GameLogic) {
        games[lobbyId] = gameLogic
        val player = gameLogic.playerLogic.getCurrentPlayer()
        if(player != null) {
            startTimeout(lobbyId, player.playerId)
        } else {
            logger.info("Player not found for lobby id $lobbyId")
        }
    }

    fun killPlayer(lobbyId: UUID) {
        val game = games[lobbyId]
        val player = game!!.playerLogic.getCurrentPlayer()
        game.removePlayer(player!!.playerId)
        sendResponse(player, lobbyId)
        createTimeout(lobbyId,game)
    }

    fun sendResponse(player: Player, lobbyId: UUID){
            val serverMessage = ServerMessage("TIMEOUT","Player ${player.name} TimedOut",player.playerId)
            messagingTemplate.convertAndSend("/topic/lobby/${lobbyId}", serverMessage)

    }

    fun startTimeout(lobbyId: UUID, playerId: UUID) {
        val timeoutLogic = TimeoutLogic(
            ejectPlayer = { lobbyId, playerId ->
                killPlayer(lobbyId)
            }
        )

        timers[lobbyId] = timeoutLogic

        timeoutLogic.start(lobbyId, playerId)
    }

    fun cancelTimeout(lobbyId: UUID) {
        val timeoutLogic = timers[lobbyId]

        timeoutLogic!!.cancel(lobbyId)
    }
}
