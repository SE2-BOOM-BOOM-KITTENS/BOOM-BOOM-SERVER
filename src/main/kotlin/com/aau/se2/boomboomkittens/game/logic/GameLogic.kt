package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffectRegistry
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.PlayerLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import java.util.LinkedList
import java.util.UUID

open class GameLogic(
    var lobbyId: UUID,
    val players: MutableList<Player> = mutableListOf(),

){

    private val _playerLogic: PlayerLogic = PlayerLogic()
    private val _cardLogic: CardLogic = CardLogic(players.size)
    private val _discardPile: CardPile = CardPile()
    private val _cardRegistry = CardEffectRegistry
    private val cardPile = CardPile()

    val playerLogic: PlayerLogic
        get() = _playerLogic
    val cardLogic: CardLogic
        get() = _cardLogic
    val discardPile: CardPile
        get() = _discardPile


    init {
        for (player in players) {
            _playerLogic.addPlayerByID(player)
            _cardLogic.addPlayer(player)
        }
    }

    fun removePlayer(playerId: UUID) {
        _playerLogic.removePlayerByID(playerId)
    }

    fun getWinner(): Player? {
        return if (_playerLogic.getPlayerCount() == 1) {
            _playerLogic.getCurrentPlayer()
        } else null
    }

    fun nextTurn() {
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

    fun playCard(playerId: UUID, cardType: CardType) {
        val player = _playerLogic.getPlayerByID(playerId)
            ?: throw IllegalArgumentException("Player not found: $playerId")

        if (player.playerHand.containsCardType(cardType)) {
            val effect = _cardRegistry.getEffect(cardType)
            effect.apply(player, this)
        } else {
            throw IllegalStateException("Player doesn't have card type $cardType")
        }
    }

    open fun peekTopCards(count: Int): List<Card> {
        return drawPile.take(count)
    }


    fun playCard(playerId: UUID, cardType: CardType) {
        val player = _playerLogic.getPlayerByID(playerId)
            ?: throw IllegalArgumentException("Player not found")

        if (!player.playerHand.containsCardType(cardType)) {
            throw IllegalStateException("Player doesn't have card type $cardType")
        }

        val effect = _cardRegistry.getEffect(cardType)

        effect.apply(player, this)
    }


        for (i in newOrder.size - 1 downTo 0) {
            drawPile.add(newOrder[i])
        }

    fun notifyDeckShuffled(player: Player){
        // eventDispatcher.sendToAllPlayers("DeckShuffled", currentCardPileState())

    }
}