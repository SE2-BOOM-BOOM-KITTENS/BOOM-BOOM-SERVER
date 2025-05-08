package com.aau.se2.boomboomkittens.game

import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.player.PlayerCircle
import java.util.UUID

class GameState constructor(
    var lobbyId: UUID,
    val players: MutableList<Player> = mutableListOf(),
){
    var playerCircle: PlayerCircle = PlayerCircle()
    var drawPile: CardPile = buildInitialPile(players.size)
    var discardPile: CardPile = CardPile()


    init {
        for(player in players){
            playerCircle.addPlayer(player)
        }
    }

    fun drawCard(playerId: UUID){
        check(!(drawPile.isEmpty())){
            throw IllegalStateException("Cannot draw from empty pile")
        }
        val card = drawPile.draw()
        playerCircle.addCardToPlayer(playerId, card)
    }

    fun removePlayer(playerId: UUID){
        playerCircle.removePlayerById(playerId)
    }

    fun addCardToPlayer(playerId: UUID, card: Card) {
        playerCircle.addCardToPlayer(playerId, card)
    }

    fun removeCardFromPlayer(playerId: UUID, card: Card) {
        playerCircle.removeCardFromPlayer(playerId, card)
    }

    fun putCardToDiscardPile(card: Card){
        this.discardPile.insertAt(0,card)
    }

    fun getWinner(): Player? {
        if(playerCircle.getPlayerCount() == 1){
            val winner = playerCircle.getCurrentPlayer()
            return winner
        }
        return null
    }

    fun buildInitialPile(playerCount: Int): CardPile {
        //TODO Insert specific cards based on player count
        return CardPile()
    }
}