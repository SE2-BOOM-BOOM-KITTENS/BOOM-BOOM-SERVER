package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffectRegistry
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.player.PlayerHand
import java.util.UUID

open class GameLogic(
    var lobbyId: UUID,
    val players: MutableList<Player> = mutableListOf(),
){


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

    fun addPlayer(playerId: UUID, playerName:String){
        val newPlayer = Player(playerId, playerName)
        _playerLogic.addPlayerByID(newPlayer)
        _cardLogic.addPlayer(newPlayer)
    }

    fun getPlayerById(playerId: UUID): Player? {
        return _playerLogic.getPlayerByID(playerId)
    }

    fun getPlayerHand(playerId: UUID): PlayerHand? {
        return _cardLogic.getPlayerHand(playerId)
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

    fun shuffleDeck(){
        cardPile.shuffle()
    }

    fun notifyDeckShuffled(player: Player){
        //eventDispatcher.sendToAllPlayers("DeckShuffled", currentCardPileState())
    }

}