package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.controllers.webSocket

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.messages.PlayerMessage
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.playerHandshake.UserPrincipal
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.services.GameLogicService
import com.aau.se2.boomboomkittens.filipp.server.networkPacket.CardNetworkPacket
import com.aau.se2.boomboomkittens.filipp.server.services.LobbyService
import com.aau.se2.boomboomkittens.game.cards.Card
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import java.util.UUID

@Controller
@RequestMapping("/game")
class GameLogicController(
    private val gameLogicService: GameLogicService,
    private val lobbyService: LobbyService
) {

    @MessageMapping("/action")
    fun processAction(playerMessage: PlayerMessage, principal: UserPrincipal) {
        val playerID = UUID.fromString(principal.name)
        val lobbyID = playerMessage.lobbyId!!
        val action = playerMessage.action
        val payload = playerMessage.payload
        when(action){
            "CHEAT" -> gameLogicService.cheatDuplicate(lobbyID,playerID,payload)
            "CHECK_CHEAT" -> gameLogicService.checkIfDuplicate(lobbyID,playerID,payload)
            "PASS" -> gameLogicService.pass(lobbyID,playerID)
            "PLAY_CARDS" ->  gameLogicService.playCards(lobbyID,playerID, payload)
            "EXIT" -> gameLogicService.exitPlayer(lobbyID,playerID)
            "HAND" -> gameLogicService.getPlayerHand(lobbyID,playerID)
            "INIT" -> gameLogicService.getInitState(lobbyID,playerID)
            "EXPLODE" -> gameLogicService.explodePlayer(lobbyID,playerID)
            "SHUFFLE_DECK" -> gameLogicService.shuffleDeck(lobbyID, playerID)
            "CAT_COMBO" -> {
                val targetId = playerMessage.targetId?.let { UUID.fromString(it) }
                val networkCards = (payload as? List<*>)?.filterIsInstance<CardNetworkPacket>()

                if(networkCards != null && networkCards.isNotEmpty()) {
                    val cards = networkCards.map { Card(type = it.type, name = it.name, aliasType = it.aliasType) }
                    gameLogicService.playCatCombo(lobbyID,playerID, cards, targetId)
                }
            }
            "CHOOSE_FROM_DISCARD" -> {
                val cards = (payload as? List<*>)?.filterIsInstance<CardNetworkPacket>()
                val chosenType = cards?.firstOrNull()?.type
                if (chosenType != null) {
                    gameLogicService.chooseFromDiscard(lobbyID,playerID, chosenType)
                } else {
                    gameLogicService.sendUserError(lobbyID,playerID, "Keine Karte ausgewählt.")
                }
            }
            else -> gameLogicService.sendUserError(lobbyID,playerID,"Invalid action")
        }
    }

    /** TODO()
     * A temporary way to add players to game
     * This should be later implemented into lobby functionality
     */
    @MessageMapping("/addPlayer")
    fun addPlayer(playerMessage: PlayerMessage, principal: UserPrincipal) {
        val lobbyId = playerMessage.lobbyId
        val playerId = UUID.fromString(principal.name)
        val playerName = playerMessage.playerName!!
        gameLogicService.joinGame(lobbyId!!,playerId, playerName)
    }

    @MessageMapping("/createGame")
    fun createGame(playerMessage: PlayerMessage, principal: UserPrincipal) {
        val lobbyId = playerMessage.lobbyId
        val playerId = UUID.fromString(principal.name)
        val lobby = lobbyService.getLobby(lobbyId.toString())

        if (lobby == null) {
            gameLogicService.sendUserError(lobbyId!!, playerId, "Lobby not found.")
            return
        }

        gameLogicService.createGame(lobby)

        gameLogicService.sendGameCreated(lobbyId!!, playerId)
    }

}