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

}