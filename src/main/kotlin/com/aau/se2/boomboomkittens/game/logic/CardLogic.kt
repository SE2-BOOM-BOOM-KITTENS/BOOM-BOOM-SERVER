package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.player.PlayerNode
import java.util.*

class CardLogic {
    private val playerMap = mutableMapOf<UUID, PlayerNode>()
    val players: MutableList<Player> = mutableListOf()
    var drawPile: CardPile = buildInitialPile(players.size)

    fun addCardToPlayer(playerId: UUID, card: Card){
        val player = playerMap[playerId]?.player
        requireNotNull(player){
            throw IllegalArgumentException("Player with id $playerId not found")
        }
        player.playerHand.addCard(card)
    }

    fun removeCardFromPlayer(playerId: UUID, card: Card){
        val player = playerMap[playerId]?.player
        requireNotNull(player){
            throw IllegalArgumentException("Player with id $playerId not found")
        }
        player.playerHand.removeCard(card)
    }

    fun drawCard(playerId: UUID){
        check(!(drawPile.isEmpty())){
            throw IllegalStateException("Cannot draw from empty pile")
        }
        val card = drawPile.draw()
        addCardToPlayer(playerId, card)
    }

    fun buildInitialPile(playerCount: Int): CardPile {
        //TODO Insert specific cards based on player count
        return CardPile()
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
            drawPile.insertAt(0,newOrder[i])
        }
        println("${player.name} rearranged the top ${newOrder.size} cards.")
    }

}