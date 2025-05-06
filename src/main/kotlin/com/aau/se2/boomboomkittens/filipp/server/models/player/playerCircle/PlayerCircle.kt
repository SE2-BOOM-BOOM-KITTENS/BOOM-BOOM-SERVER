package com.aau.se2.boomboomkittens.filipp.server.models.player.playerCircle

import com.aau.se2.boomboomkittens.filipp.server.models.cards.Card
import com.aau.se2.boomboomkittens.filipp.server.models.player.Player
import java.util.UUID

class PlayerCircle {
    private val playerMap = mutableMapOf<UUID, PlayerNode>()
    private var currentPlayer: PlayerNode? = null

    fun addPlayer(player: Player) {
        if(playerMap.containsKey(player.playerId)){
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

    fun isCurrentPlayer(playerId: UUID): Boolean {
        val currentPlayerId = currentPlayer?.player?.playerId
        return currentPlayerId == playerId
    }

    fun getCurrentPlayerNode(): PlayerNode? {
        return currentPlayer
    }

    fun nextTurn(){
        currentPlayer = currentPlayer?.next
    }

    fun addCardToPlayer(playerId: UUID, card: Card){
        val player = playerMap[playerId]?.player
        if(player == null){
            throw IllegalArgumentException("Player with id $playerId not found")
        }
        player.playerHand.addCard(card)
    }

    fun removeCardFromPlayer(playerId: UUID, card: Card){
        val player = playerMap[playerId]?.player
        if(player == null){
            throw IllegalArgumentException("Player with id $playerId not found")
        }
        player.playerHand.removeCard(card)
    }

    fun getPlayerHand(playerId: UUID): PlayerHand {
        return playerMap[playerId]?.player?.playerHand ?: throw IllegalStateException("Player with id $playerId not found")
    }
}