package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.player.PlayerNode
import java.util.*

class PlayerLogic {
    private val playerMap = mutableMapOf<UUID, PlayerNode>()
    private var currentPlayer: PlayerNode? = null

    fun addPlayer(player: Player) {
        require(!(playerMap.containsKey(player.playerId))){
            throw IllegalArgumentException("Player with id ${player.playerId} already exists")
        }
        val newNode = PlayerNode(player)
        if(currentPlayer == null) {
            newNode.next = newNode
            newNode.previous = newNode
            currentPlayer = newNode
        } else{
            val last = currentPlayer!!.previous
            last?.next = newNode
            newNode.previous = last
            newNode.next = currentPlayer
            currentPlayer!!.previous = newNode
        }
        playerMap[player.playerId] = newNode
    }

    fun removePlayerById(playerId: UUID) {
        val node = playerMap.remove(playerId)
        if(node?.next == node){
            currentPlayer = null
        } else{
            node?.previous!!.next = node.next
            node.next?.previous = node.previous
            if(currentPlayer == node){
                currentPlayer = node.next
            }
        }
    }

    fun getPlayerById(playerId: UUID): Player? {
        return playerMap[playerId]?.player
    }

    fun getPlayerCount(): Int{
        return playerMap.size
    }

    fun getPlayerList(): List<Player> {
        val playerList = mutableListOf<Player>()
        var node = currentPlayer
        val visited = mutableSetOf<UUID>()
        while (node != null && !visited.contains(node.player.playerId)) {
            playerList.add(node.player)
            visited.add(node.player.playerId)
            node = node.next
        }
        return playerList
    }

    fun getCurrentPlayer(): Player? {
        return currentPlayer?.player
    }

    fun getCurrentPlayerNode(): PlayerNode? {
        return currentPlayer
    }
}