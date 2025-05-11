package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.controllers.webSocket

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.messages.PlayerMessage
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.services.GameLogicService
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import java.security.Principal
import java.util.UUID

@Controller
@RequestMapping("/game")
class GameLogicController {
    private val gameLogicService = GameLogicService()


    @MessageMapping("/action")
    fun processAction(playerMessage: PlayerMessage, principal: Principal) {
        val playerID = UUID.fromString(principal.name)
        val action = playerMessage.action
        val cardsPlayed = playerMessage.cardsPlayed
        when(action){
            "PASS" -> gameLogicService.pass(playerID)
            "PLAY_CARDS" ->  gameLogicService.playCards(playerID, cardsPlayed)
            "EXIT" -> gameLogicService.exitPlayer(playerID)
            else -> gameLogicService.sendUserError(playerID,"Invalid action")
        }
    }

    /** TODO()
     * A temporary way to add players to game
     * This should be later implemented into lobby functionality
     */
    @MessageMapping("/addPlayer")
    fun addPlayer(playerMessage: PlayerMessage, principal: Principal) {
        val playerId = UUID.fromString(principal.name)
        val playerName = playerMessage.playerName
        gameLogicService.addPlayer(playerId, playerName)
    }

}