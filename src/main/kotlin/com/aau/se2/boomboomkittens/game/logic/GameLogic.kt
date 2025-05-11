package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffectRegistry
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.player.PlayerHand
import com.aau.se2.boomboomkittens.game.player.PlayerNode
import java.util.UUID

open class GameLogic(
    var lobbyId: UUID,
    val players: MutableList<Player> = mutableListOf(),
){
    private val playerLogic: PlayerLogic = PlayerLogic()
    private val cardLogic: CardLogic = CardLogic()
    private val discardPile: CardPile = CardPile()
    private val playerMap = mutableMapOf<UUID, PlayerNode>()
    private val cardRegistry = CardEffectRegistry


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

    fun nextTurn(){
        playerLogic.moveToNextPlayer()
    }

    fun addPlayer(playerId: UUID, playerName:String){
        val newPlayer = Player(playerId, playerName)
        playerLogic.addPlayerByID(newPlayer)
    }

    fun getPlayerHand(playerId: UUID): PlayerHand {
        return playerMap[playerId]?.player?.playerHand ?: throw IllegalStateException("Player with id $playerId not found")
    }

    fun getPlayerLogic(): PlayerLogic {
        return this.playerLogic
    }

    fun getCardLogic(): CardLogic {
        return this.cardLogic
    }

    fun getDiscardPile(): CardPile {
        return discardPile
    }

    fun playCard(playerId: UUID, cardType: CardType){
        val player = playerLogic.getPlayerByID(playerId)
        if(player!!.playerHand.containsCardType(cardType)){
            val card = cardRegistry.getEffect(cardType)
            card.apply(player,this)
        } else{
            throw IllegalStateException("Player doesn't have card type $cardType")
        }

    }
}