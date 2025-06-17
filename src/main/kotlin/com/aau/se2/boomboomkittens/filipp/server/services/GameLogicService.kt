package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.services

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.CheckCardNetworkPacket
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.messages.ServerMessage
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.filipp.server.networkPacket.NetworkPacketMapper
import com.aau.se2.boomboomkittens.game.Lobby
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.cards.effects.CatComboEffectHandler
import com.aau.se2.boomboomkittens.game.player.Player
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Service
class GameLogicService(
    val messagingTemplate: SimpMessagingTemplate,
    private val jacksonObjectMapper: ObjectMapper
) {
    private val games = ConcurrentHashMap<UUID, GameLogic>()
    private val networkPacketMapper = NetworkPacketMapper()

    //TEMPORARY PARAMETER; FOR TESTING PURPOSES ONLY; REMOVE AFTER FIXING THE TEST CLASS
    var lobbyId: UUID? = null


    //TEMPORARY SOLUTION; FOR DEBUGGING ONLY; REMOVE WHEN LOBBIES ARE IMPLEMENTED
    init {
        val lobby = Lobby(id= UUID.fromString("00000000-0000-0000-0000-000000001234"),creator = Player(name="Steve"), maxPlayers = 2)
        lobbyId = lobby.id
        createGame(lobby)
    }

    fun createGame(lobby: Lobby) {
        val gameLogic = GameLogic(lobby.id, lobby.players)
        games[lobby.id] = gameLogic
    }

    fun getGame(lobbyId: UUID): GameLogic {
        val game = games[lobbyId]
        if(game == null) {
            throw IllegalArgumentException("Game with id $lobbyId does not exist")
        }
        return game
    }

    fun pass(lobbyId: UUID, playerId: UUID) {
        endTurn(lobbyId,playerId)

        val game = getGame(lobbyId)
        sendGameState(lobbyId,"Player $playerId has passed",game)
    }

    fun playCards(lobbyId: UUID,playerId: UUID, payload: Any?) {
        val game = getGame(lobbyId)
        var cardsNames = ""
        val cards = (payload as? List<*>)?.filterIsInstance<Card>()!!

        for(card in cards){
            cardsNames += card.name+", "
            cardLogic.playCard(playerId,card.type)

        }
        endTurn(lobbyId,playerId)
        sendGameState(lobbyId,"Player $playerId has played $cardsNames cards",game)
    }

    fun cheatDuplicate(lobbyID: UUID, playerId: UUID, payload: Any?) {
        val game = getGame(lobbyID)
        val mapper = jacksonObjectMapper
        val card = try{
            mapper.convertValue(payload, Card::class.java)
        }catch (e:Exception){
            null
        }
        game.cardLogic.cheatDuplicateCard(playerId, card!!)
    }

    fun checkIfDuplicate(lobbyId: UUID, playerId: UUID, payload: Any?) {
        val game = getGame(lobbyId)
        val mapper = jacksonObjectMapper
        val packet = try{
            mapper.convertValue(payload, CheckCardNetworkPacket::class.java)
        } catch (e: Exception){
            null
        }
        val result = game.cardLogic.isCardDuplicate(packet!!.targetId, packet.card)

        if(result){
            game.removePlayer(packet.targetId)
            sendGameState(lobbyId,"Player ${packet.targetId} was too bad at cheating",game)
        } else{
            game.cardLogic.drawCard(playerId)
            sendGameState(lobbyId,"Player $playerId wrongly accused ${packet.targetId}",game)
        }
    }

    fun exitPlayer(lobbyId: UUID,playerId: UUID){
        val game = getGame(lobbyId)
        game.removePlayer(playerId)

        sendGameState(lobbyId,"Player $playerId exited the game",game)
    }

    fun getInitState(lobbyId:UUID, playerId: UUID){
        val game = getGame(lobbyId)
        sendGameState(lobbyId,"Starting State",game,playerId)
    }

    fun getPlayerHand(lobbyId: UUID, playerId: UUID){
        val game = getGame(lobbyId)
        val playerHand = game.getPlayerHand(playerId)
        val serverMessage = ServerMessage("HAND","You have received your card hand",playerHand)
        println("Sending hand to user $lobbyId")
        sendResponse(lobbyId=lobbyId,playerId= playerId, payload = serverMessage)
    }

    fun joinGame(lobbyId: UUID, playerId: UUID, playerName:String){
        val game = getGame(lobbyId)
        game.addPlayer(playerId, playerName)

        val gameState = networkPacketMapper.gameStateToNetworkPacket(game,game.cardLogic)
        val serverMessage = ServerMessage("GAME_STATE","Player $playerName has joined",gameState)
        sendResponse(lobbyId = lobbyId,payload = serverMessage)

        getPlayerHand(lobbyId,playerId)
    }

    fun explodePlayer(lobbyId:UUID, playerId: UUID){
        val game = getGame(lobbyId)
        game.removePlayer(playerId)



        sendGameState(lobbyId,"Player $playerId has exploded",game)
        val privateServerMessage = ServerMessage("EXPLODE", "You have exploded",null)
        sendResponse(playerId, payload = privateServerMessage)
    }

    private fun endTurn(lobbyId: UUID, playerId: UUID){
        val game = getGame(lobbyId)
        game.cardLogic.drawCard(playerId)
        game.nextTurn()
    }

    fun sendGameState(lobbyId: UUID, message:String, game: GameLogic, playerId: UUID? = null){
        val gameState = networkPacketMapper.gameStateToNetworkPacket(game,game.cardLogic)
        val serverMessage = ServerMessage("GAME_STATE",message,gameState)
        if(playerId != null){
            sendResponse(lobbyId= lobbyId,playerId = playerId, payload = serverMessage)
        }else {
            sendResponse(lobbyId = lobbyId, payload = serverMessage)
        }
    }

    fun sendResponse(lobbyId: UUID, playerId: UUID? = null, payload: Any){
        if(playerId != null){
            println("Sending message to player: $playerId")
            messagingTemplate.convertAndSendToUser(playerId.toString(),"/queue/private", payload)
        } else{
            println("Sending message to lobby: $lobbyId")
            messagingTemplate.convertAndSend("/topic/lobby/${lobbyId}",payload)
        }
    }

    fun sendUserError(lobbyId: UUID, playerId: UUID, errorMessage: String){
        val serverMessage = ServerMessage("ERROR", errorMessage,null)
        sendResponse(lobbyId= lobbyId,payload = serverMessage)
        sendResponse(lobbyId,playerId,serverMessage)
    }

    fun playCatCombo(lobbyId:UUID, playerId: UUID, rawCards: List<Card>, targetId: UUID?) {
        val game = getGame(lobbyId)
        val player = game.getPlayerById(playerId) ?: return
        val target = targetId?.let { game.getPlayerById(it) }
        val handler = CatComboEffectHandler(cardLogic) { id, payload ->
            sendResponse(lobbyId,id, payload) // Callback für Nachrichten

        }

        handler.applyCombo(player, rawCards, target)
    }


    fun chooseFromDiscard(lobbyID: UUID, playerId: UUID, cardType: CardType) {
        val game = getGame(lobbyID)
        val card = cardLogic.discardPile.getPileList().lastOrNull { it.type == cardType } ?: return
        game.discardPile.getPileList().remove(card)
        game.getPlayerById(playerId)?.playerHand?.addCard(card)
    }

}