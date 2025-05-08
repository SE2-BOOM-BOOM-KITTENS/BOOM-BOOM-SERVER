package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.player.PlayerHand
import com.aau.se2.boomboomkittens.game.player.PlayerNode
import java.util.UUID

class GameLogic(
    var lobbyId: UUID,
    val players: MutableList<Player> = mutableListOf(),
){
    var playerLogic: PlayerLogic = PlayerLogic()
    var discardPile: CardPile = CardPile()
    private val playerMap = mutableMapOf<UUID, PlayerNode>()


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
}