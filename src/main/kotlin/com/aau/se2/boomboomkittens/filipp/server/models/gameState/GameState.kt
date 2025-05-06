package com.aau.se2.boomboomkittens.filipp.server.models.gameState

import com.aau.se2.boomboomkittens.filipp.server.models.cards.Card
import com.aau.se2.boomboomkittens.filipp.server.models.cards.CardPile
import com.aau.se2.boomboomkittens.filipp.server.models.dtos.CardDTO
import com.aau.se2.boomboomkittens.filipp.server.models.dtos.CardPileDTO
import com.aau.se2.boomboomkittens.filipp.server.models.dtos.GameStateDTO
import com.aau.se2.boomboomkittens.filipp.server.models.dtos.PlayerDTO
import com.aau.se2.boomboomkittens.filipp.server.models.dtos.PlayerHandDTO
import com.aau.se2.boomboomkittens.filipp.server.models.player.Player
import com.aau.se2.boomboomkittens.filipp.server.models.player.playerCircle.PlayerCircle
import com.aau.se2.boomboomkittens.filipp.server.models.player.playerCircle.PlayerHand
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
        if(drawPile.isEmpty()){
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