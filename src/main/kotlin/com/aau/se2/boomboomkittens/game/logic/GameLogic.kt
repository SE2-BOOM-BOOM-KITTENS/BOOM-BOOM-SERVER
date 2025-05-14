package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffectRegistry
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import java.util.UUID

open class GameLogic(
    var lobbyId: UUID,
    val players: MutableList<Player> = mutableListOf(),
){
    private val _playerLogic: PlayerLogic = PlayerLogic()
    private val _cardLogic: CardLogic = CardLogic(players.size)
    private val _discardPile: CardPile = CardPile()
    private val _cardRegistry = CardEffectRegistry

    val playerLogic: PlayerLogic
        get() = _playerLogic
    val cardLogic: CardLogic
        get() = _cardLogic
    val discardPile: CardPile
        get() = _discardPile
    val cardRegistry: CardEffectRegistry
        get() = _cardRegistry


    init {
        for(player in players){
            _playerLogic.addPlayerByID(player)
            _cardLogic.addPlayer(player)
        }
    }

    fun removePlayer(playerId: UUID){
        _playerLogic.removePlayerByID(playerId)
    }

    fun getWinner(): Player? {
        if(_playerLogic.getPlayerCount() == 1){
            val winner = _playerLogic.getCurrentPlayer()
            return winner
        }
        return null
    }

    fun nextTurn(){
        _playerLogic.moveToNextPlayer()
    }

    fun skipPlayer(){
        nextTurn()
        nextTurn()
    }

    fun addPlayer(playerId: UUID, playerName:String){
        val newPlayer = Player(playerId, playerName)
        _playerLogic.addPlayerByID(newPlayer)
    }

    fun playCard(playerId: UUID, cardType: CardType){
        val player = _playerLogic.getPlayerByID(playerId)
        if(player!!.playerHand.containsCardType(cardType)){
            val card = _cardRegistry.getEffect(cardType)
            card.apply(player,this)
        } else{
            throw IllegalStateException("Player doesn't have card type $cardType")
        }

    }
}