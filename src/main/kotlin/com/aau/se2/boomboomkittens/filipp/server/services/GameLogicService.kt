package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.services

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.messages.ServerMessage
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.filipp.server.networkPacket.CardNetworkPacket
import com.aau.se2.boomboomkittens.filipp.server.networkPacket.NetworkPacketMapper
import com.aau.se2.boomboomkittens.game.Lobby
import com.aau.se2.boomboomkittens.game.player.Player
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.UUID


@Service
class GameLogicService {
    private val lobby = Lobby(creator = Player(name="Steve"), maxPlayers = 4)
    private val gameLogic = GameLogic(lobby.id)
    private val cardLogic = CardLogic()
    private val networkPacketMapper = NetworkPacketMapper()
    private lateinit var messagingTemplate: SimpMessagingTemplate


    fun pass(playerId: UUID) {
        endTurn(playerId)

        val gameState = networkPacketMapper.gameStateToNetworkPacket(gameLogic,cardLogic)
        val serverMessage = ServerMessage("GAME_STATE","Player $playerId has passed",gameState)
        sendGameUpdate(payload = serverMessage)
    }

    fun playCards(playerId: UUID, cards: List<CardNetworkPacket>) {
        var cardsNames = ""
        for(card in cards){
            cardsNames += card.name+", "
            gameLogic.playCard(playerId,card.type)
        }
        endTurn(playerId)
        val gameState = networkPacketMapper.gameStateToNetworkPacket(gameLogic,cardLogic)
        val serverMessage = ServerMessage("GAME_STATE", "Player $playerId has played $cardsNames cards", gameState)
        sendGameUpdate(payload = serverMessage)
    }

    fun exitPlayer(playerId: UUID){
        gameLogic.removePlayer(playerId)

        val gameState = networkPacketMapper.gameStateToNetworkPacket(gameLogic,cardLogic)
        val serverMessage = ServerMessage("GAME_STATE","Player $playerId has exited",gameState)
        sendGameUpdate(payload = serverMessage)
    }

    fun sendGameUpdate(playerId: UUID? = null, payload: Any){
        if(playerId != null){
            messagingTemplate.convertAndSendToUser(playerId.toString(),"/queue/private", payload)
        } else{
            messagingTemplate.convertAndSend("/topic/lobby/${lobby.id}",payload)
        }
    }

    fun addPlayer(playerId: UUID, playerName:String){
        gameLogic.addPlayer(playerId, playerName)
    }

    private fun endTurn(playerId: UUID){
        cardLogic.drawCard(playerId)
        gameLogic.nextTurn()
    }

    fun sendUserError(playerId: UUID, errorMessage: String){
        val serverMessage = ServerMessage("ERROR", errorMessage,null)
        sendGameUpdate(playerId,serverMessage)
    }

}