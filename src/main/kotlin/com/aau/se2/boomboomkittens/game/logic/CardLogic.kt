package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.player.Player
import java.util.*

class CardLogic(playerSize: Int) {
    private val playerMap = mutableMapOf<UUID, Player>()

    var drawPile: CardPile = buildInitialPile(playerSize)

    fun addCardToPlayer(playerId: UUID, card: Card){
        val player = playerMap[playerId]
        requireNotNull(player){
            throw IllegalArgumentException("Player with id $playerId not found")
        }
        player.playerHand.addCard(card)
    }

    fun addPlayer(player: Player){
        playerMap[player.playerId] = player
    }

    fun removeCardFromPlayer(playerId: UUID, card: Card){
        val player = playerMap[playerId]
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
        require(newOrder.size <= drawPile.size) {
            throw IllegalArgumentException("New order has more cards than the draw pile.")
        }
        repeat(newOrder.size) { drawPile.removeFirst() }

        for (i in newOrder.size - 1 downTo 0) {
            drawPile.insertAt(0,newOrder[i])
        }
        println("${player.name} rearranged the top ${newOrder.size} cards.")
    }

}