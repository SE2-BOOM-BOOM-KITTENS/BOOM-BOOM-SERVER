package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.player.Player
import java.util.UUID

class GameLogic constructor(
    var lobbyId: UUID,
    val players: MutableList<Player> = mutableListOf(),
){
    var playerLogic: PlayerLogic = PlayerLogic()
    var drawPile: CardPile = buildInitialPile(players.size)
    var discardPile: CardPile = CardPile()


    init {
        for(player in players){
            playerLogic.addPlayer(player)
        }
    }

    fun drawCard(playerId: UUID){
        check(!(drawPile.isEmpty())){
            throw IllegalStateException("Cannot draw from empty pile")
        }
        val card = drawPile.draw()
        playerLogic.addCardToPlayer(playerId, card)
    }

    fun removePlayer(playerId: UUID){
        playerLogic.removePlayerById(playerId)
    }

    fun addCardToPlayer(playerId: UUID, card: Card) {
        playerLogic.addCardToPlayer(playerId, card)
    }

    fun removeCardFromPlayer(playerId: UUID, card: Card) {
        playerLogic.removeCardFromPlayer(playerId, card)
    }

    fun putCardToDiscardPile(card: Card){
        this.discardPile.insertAt(0,card)
    }

    fun getWinner(): Player? {
        if(playerLogic.getPlayerCount() == 1){
            val winner = playerLogic.getCurrentPlayer()
            return winner
        }
        return null
    }

    fun buildInitialPile(playerCount: Int): CardPile {
        //TODO Insert specific cards based on player count
        return CardPile()
    }
}