package com.aau.se2.boomboomkittens.filipp.server.services

import com.aau.se2.boomboomkittens.game.player.Player
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Service
class PlayerService {
    private val players = ConcurrentHashMap<UUID, Player>()

    fun createPlayer(name:String): UUID {
        val player = Player(playerId = UUID.randomUUID(), name = name)
        players[player.playerId] = player
        return player.playerId
    }

    fun getPlayers(): ConcurrentHashMap<UUID, Player> {
        return players
    }

    fun getPlayer(id:UUID): Player {
        if(players[id] == null){
            throw IllegalStateException("Player with id $id does not exist")
        } else {
            return players.getValue(id)
        }
    }

    fun removePlayer(id:UUID){
        players.remove(id)
    }

    fun clearPlayers(){
        players.clear()
    }
}