package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.player.PlayerHand
import com.aau.se2.boomboomkittens.game.player.PlayerNode
import java.util.LinkedList
import java.util.UUID

open class GameLogic(
    var lobbyId: UUID,
    val players: MutableList<Player> = mutableListOf(),
){
    var playerLogic: PlayerLogic = PlayerLogic()
    var discardPile: CardPile = CardPile()
    private val playerMap = mutableMapOf<UUID, PlayerNode>()
    private val drawPile: LinkedList<Card> = LinkedList()


    init {
        for(player in players){
            playerLogic.addPlayerByID(player)
        }
    }

    fun removePlayer(playerId: UUID){
        playerLogic.removePlayerByID(playerId)
    }

    fun getWinner(): Player? {
        if(playerLogic.getPlayerCount() == 1){
            val winner = playerLogic.getCurrentPlayer()
            return winner
        }
        return null
    }

    fun getPlayerHand(playerId: UUID): PlayerHand {
        return playerMap[playerId]?.player?.playerHand ?: throw IllegalStateException("Player with id $playerId not found")
    }

    open fun peekTopCards(count: Int): List<Card> {
        return drawPile.take(count)
    }
    open fun allowPlayerToRearrangeTopCards(player: Player, newOrder: List<Card>) {
        if (newOrder.size > drawPile.size) {
            throw IllegalArgumentException("New order has more cards than the draw pile.")
        }
        repeat(newOrder.size) { drawPile.removeFirst() }

        for (i in newOrder.size - 1 downTo 0) {
            drawPile.addFirst(newOrder[i])
        }
        println("${player.name} rearranged the top ${newOrder.size} cards.")
    }
}