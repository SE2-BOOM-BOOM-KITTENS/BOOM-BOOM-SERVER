package com.aau.se2.boomboomkittens.filipp.server.services

import com.aau.se2.boomboomkittens.filipp.server.models.Lobby
import com.aau.se2.boomboomkittens.filipp.server.models.Player
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class LobbyService {
    private val lobbies = ConcurrentHashMap<String,Lobby>()

    fun createLobby(): Lobby{
        val lobby = Lobby(players = mutableListOf())
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
    }

    fun joinPlayer(lobbyId: String,player: Player){
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

}