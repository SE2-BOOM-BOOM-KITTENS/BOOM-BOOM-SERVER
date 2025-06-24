package com.aau.se2.boomboomkittens.filipp.server.services

import com.aau.se2.boomboomkittens.game.player.LobbyPlayer
import com.aau.se2.boomboomkittens.game.Lobby
import com.aau.se2.boomboomkittens.game.player.Player
import org.springframework.stereotype.Service
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@Service
class LobbyService(private val playerService: PlayerService) {
    private val lobbies = ConcurrentHashMap<String, Lobby>()

    init {
        val steve = Player(name="Steve")
        createLobby(steve,2)

    }

    fun createLobby(creator: Player, maxPlayers: Int): Lobby {
        val lobby = Lobby(creator=creator,players = mutableListOf(), maxPlayers = maxPlayers)
        lobby.players.add(creator)
        lobbies[lobby.id.toString()] = lobby
        return lobby
    }

    fun getLobbies(): ConcurrentHashMap<String, Lobby> {
        return this.lobbies
    }

    fun getLobby(lobbyId:String): Lobby? {
        return this.lobbies[lobbyId]
    }

    private fun deleteLobby(id:String){
        this.lobbies.remove(id)
        //lobbyWebSocketController.broadcastLobbyUpdate()
    }

    fun joinLobby(lobbyId: String,id: UUID){
        val player = playerService.getPlayer(id)
        val lobby = lobbies[lobbyId]
        lobby?.players?.add(player)
    }

    fun removePlayer(lobbyId: String,player: Player){
        val lobby = lobbies[lobbyId]
        lobby?.players?.remove(player)
        if (lobby != null) {
            if(lobby.players.isEmpty()){
                deleteLobby(lobbyId)
            }
        }
    }

    fun clearAllLobbies() {
        lobbies.clear()
    }
}