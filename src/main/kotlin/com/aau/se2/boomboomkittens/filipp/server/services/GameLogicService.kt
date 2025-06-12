package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.services

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.CheckCardNetworkPacket
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.messages.ServerMessage
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.filipp.server.networkPacket.CardNetworkPacket
import com.aau.se2.boomboomkittens.filipp.server.networkPacket.NetworkPacketMapper
import com.aau.se2.boomboomkittens.game.Lobby
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.cards.effects.CatComboEffectHandler
import com.aau.se2.boomboomkittens.game.player.Player
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.UUID


@Service
class GameLogicService(
    val messagingTemplate: SimpMessagingTemplate,
    private val jacksonObjectMapper: ObjectMapper
) {
    private val lobby = Lobby(creator = Player(name="Steve"), maxPlayers = 2)
    private val gameLogic = GameLogic(lobby.id)
    private val cardLogic = gameLogic.cardLogic
    private val networkPacketMapper = NetworkPacketMapper()

    fun pass(playerId: UUID) {
        endTurn(playerId)

        val gameState = networkPacketMapper.gameStateToNetworkPacket(gameLogic,cardLogic)
        val serverMessage = ServerMessage("GAME_STATE","Player $playerId has passed",gameState)
        sendGameUpdate(payload = serverMessage)
    }

    fun playCards(playerId: UUID, payload: Any?) {
        var cardsNames = ""
        val cards = (payload as? List<*>)?.filterIsInstance<Card>()!!
        for(card in cards){
            cardsNames += card.name+", "
            gameLogic.playCard(playerId,card.type)
        }
        endTurn(playerId)
        val gameState = networkPacketMapper.gameStateToNetworkPacket(gameLogic,cardLogic)
        val serverMessage = ServerMessage("GAME_STATE", "Player $playerId has played $cardsNames cards", gameState)
        sendGameUpdate(payload = serverMessage)
    }

    fun cheatDuplicate(playerId: UUID, payload: Any?) {
        val mapper = jacksonObjectMapper
        val card = try{
            mapper.convertValue(payload, Card::class.java)
        }catch (e:Exception){
            null
        }
        cardLogic.cheatDuplicateCard(playerId, card!!)
    }


    fun checkIfDuplicate(playerId: UUID, payload: Any?) {
        val mapper = jacksonObjectMapper
        val packet = try{
            mapper.convertValue(payload, CheckCardNetworkPacket::class.java)
        } catch (e: Exception){
            null
        }
        val result = cardLogic.isCardDuplicate(packet!!.targetId, packet.card)

        if(result){
            gameLogic.removePlayer(packet.targetId)
            val gameState = networkPacketMapper.gameStateToNetworkPacket(gameLogic,cardLogic)
            val serverMessage = ServerMessage("GAME_STATE","Player ${packet.targetId} was too bad at cheating", gameState)
            sendGameUpdate(payload = serverMessage)
        } else{
            cardLogic.drawCard(playerId)
            val gameState = networkPacketMapper.gameStateToNetworkPacket(gameLogic,cardLogic)
            val serverMessage = ServerMessage("GAME_STATE","Player $playerId wrongly accused ${packet.targetId}", gameState)
            sendGameUpdate(payload = serverMessage)
        }
    }

    fun exitPlayer(playerId: UUID){
        gameLogic.removePlayer(playerId)

        val gameState = networkPacketMapper.gameStateToNetworkPacket(gameLogic,cardLogic)
        val serverMessage = ServerMessage("GAME_STATE","Player $playerId has exited",gameState)
        sendGameUpdate(payload = serverMessage)
    }

    fun getInitState(playerId: UUID){
        val gameState = networkPacketMapper.gameStateToNetworkPacket(gameLogic,cardLogic)
        val serverMessage = ServerMessage("GAME_STATE", "Starting State",gameState)
        sendGameUpdate(playerId = playerId, payload = serverMessage)
    }

    fun getPlayerHand(playerId: UUID){
        val playerHand = gameLogic.getPlayerHand(playerId)
        val serverMessage = ServerMessage("HAND","You have received your card hand",playerHand)
        sendGameUpdate(playerId= playerId, payload = serverMessage)
    }

    fun sendGameUpdate(playerId: UUID? = null, payload: Any){
        if(playerId != null){
            messagingTemplate.convertAndSendToUser(playerId.toString(),"/queue/private", payload)
        } else{
            messagingTemplate.convertAndSend("/topic/lobby/1234",payload)
        }
    }

    fun getGameLogic(): GameLogic = gameLogic

    fun joinGame(playerId: UUID, playerName:String){
        gameLogic.addPlayer(playerId, playerName)

        val gameState = networkPacketMapper.gameStateToNetworkPacket(gameLogic,cardLogic)
        val player = gameLogic.getPlayerById(playerId)
        var playerHand = getPlayerHand(playerId)
        if(playerHand != null){
        } else{
            val playerPacket = networkPacketMapper.playerToNetworkPacket(player,playerHand)
        }
        val serverMessage = ServerMessage("GAME_STATE","Player $playerId has joined",gameState)
        sendGameUpdate(payload = serverMessage)
    }

    fun explodePlayer(playerId: UUID){
        gameLogic.removePlayer(playerId)

        val gameState = networkPacketMapper.gameStateToNetworkPacket(gameLogic,cardLogic)
        val serverMessage = ServerMessage("GAME_STATE", "Player $playerId has exploded",gameState)
        val privateServerMessage = ServerMessage("EXPLODE", "You have exploded",null)

        sendGameUpdate(payload = serverMessage)
        sendGameUpdate(playerId, payload = privateServerMessage)

    }

    private fun endTurn(playerId: UUID){
        cardLogic.drawCard(playerId)
        gameLogic.nextTurn()
    }

    fun sendUserError(playerId: UUID, errorMessage: String){
        val serverMessage = ServerMessage("ERROR", errorMessage,null)
        sendGameUpdate(payload = serverMessage)
        sendGameUpdate(playerId,serverMessage)
    }

    fun sendDebugBroadcast(){
        val serverMessage = ServerMessage("DEBUG","BROADCASTING TEST",null)
        messagingTemplate.convertAndSend("/topic/test",serverMessage)
    }

    fun playCatCombo(playerId: UUID, rawCards: List<Card>, targetId: UUID?) {
        val player = gameLogic.getPlayerById(playerId) ?: return
        val target = targetId?.let { gameLogic.getPlayerById(it) }

        val handler = CatComboEffectHandler(gameLogic) { id, payload ->
            sendGameUpdate(id, payload) // Callback für Nachrichten
        }

        handler.applyCombo(player, rawCards, target)
    }

    fun chooseFromDiscard(playerId: UUID, cardType: CardType) {
        val card = gameLogic.discardPile.getPileList().lastOrNull { it.type == cardType } ?: return
        gameLogic.discardPile.getPileList().remove(card)
        gameLogic.getPlayerById(playerId)?.playerHand?.addCard(card)
    }

}