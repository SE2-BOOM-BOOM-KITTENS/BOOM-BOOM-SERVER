package com.aau.se2.boomboomkittens.filipp.server.services

import com.jetbrains.exported.JBRApi
import java.util.concurrent.ConcurrentHashMap

@JBRApi.Service
class PlayerService {
    private val players = ConcurrentHashMap<String, Player>()

    fun createPlayer(name:String): Player {
        val player = Player(name = name)
        players[player.playerId.toString()] = player
        return player
    }

    fun getPlayers(): ConcurrentHashMap<String, Player> {
        return players
    }

    fun getPlayer(id:String): Player? {
        return players[id]
    }

    fun removePlayer(id:String){
        players.remove(id)
    }

    fun clearPlayers(){
        players.clear()
    }
}