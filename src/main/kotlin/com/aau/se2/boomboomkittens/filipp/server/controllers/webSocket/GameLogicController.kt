package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.controllers.webSocket

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.messages.PlayerMessage
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.services.GameLogicService
import com.aau.se2.boomboomkittens.filipp.server.networkPacket.CardNetworkPacket
import com.aau.se2.boomboomkittens.game.cards.Card
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import java.security.Principal
import java.util.UUID

@Controller
@RequestMapping("/game")
class GameLogicController(
    private val gameLogicService: GameLogicService
) {


    @MessageMapping("/action")
    fun processAction(playerMessage: PlayerMessage, principal: Principal) {
        val playerID = UUID.fromString(principal.name)
        val action = playerMessage.action
        val payload = playerMessage.payload
        when(action){
            "CHEAT" -> gameLogicService.cheatDuplicate(playerID,payload)
            "CHECK_CHEAT" -> gameLogicService.checkIfDuplicate(playerID,payload)
            "PASS" -> gameLogicService.pass(playerID)
            "PLAY_CARDS" ->  gameLogicService.playCards(playerID, payload)
            "EXIT" -> gameLogicService.exitPlayer(playerID)
            "INIT" -> gameLogicService.getInitState(playerID)
            "EXPLODE" -> gameLogicService.explodePlayer(playerID)
            "CAT_COMBO" -> {
                val targetId = playerMessage.targetId?.let { UUID.fromString(it) }
                val networkCards = (payload as? List<*>)?.filterIsInstance<CardNetworkPacket>()

                if(networkCards != null && networkCards.isNotEmpty()) {
                    val cards = networkCards.map { Card(type = it.type, name = it.name, aliasType = it.aliasType) }
                    gameLogicService.playCatCombo(playerID, cards, targetId)
                }
            }
            "CHOOSE_FROM_DISCARD" -> {
                val cards = (payload as? List<*>)?.filterIsInstance<CardNetworkPacket>()
                val chosenType = cards?.firstOrNull()?.type
                if (chosenType != null) {
                    gameLogicService.chooseFromDiscard(playerID, chosenType)
                } else {
                    gameLogicService.sendUserError(playerID, "Keine Karte ausgewählt.")
                }
            }
            else -> gameLogicService.sendUserError(playerID,"Invalid action")
        }
    }

    @MessageMapping("/getHand")
    fun getHand(principal: Principal) {
        val playerID = UUID.fromString(principal.name)
        gameLogicService.getPlayerHand(playerID)
    }

    @MessageMapping("/test")
    fun testGameLogicService(playerMessage: PlayerMessage, principal: Principal) {
        gameLogicService.sendDebugBroadcast()
    }

    /** TODO()
     * A temporary way to add players to game
     * This should be later implemented into lobby functionality
     */
    @MessageMapping("/addPlayer")
    fun addPlayer(playerMessage: PlayerMessage, principal: Principal) {
        val playerId = UUID.fromString(principal.name)
        lateinit var playerName: String
        if(playerMessage.playerName != null) {
            playerName = playerMessage.playerName
        }else{
            playerName = ""
        }
        gameLogicService.joinGame(playerId, playerName)
    }
}