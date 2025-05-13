package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffectRegistry
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.player.PlayerHand
import java.util.UUID

open class GameLogic(
    var lobbyId: UUID,
    val players: MutableList<Player> = mutableListOf(),
){
    private val _playerLogic: PlayerLogic = PlayerLogic()
    private val _cardLogic: CardLogic = CardLogic(players.size)
    private val _discardPile: CardPile = CardPile()
    private val _cardRegistry = CardEffectRegistry
    private val drawPile = PlayerHand(UUID(0, 0))


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
    open fun peekTopCards(count: Int): List<Card> {
        return drawPile.cards.take(count)
    }
    open fun allowPlayerToRearrangeTopCards(player: Player, newOrder: List<Card>) {
        if (newOrder.size > drawPile.cards.size) {
            throw IllegalArgumentException("New order has more cards than the draw pile.")
        }

        repeat(newOrder.size) { drawPile.cards.removeFirst() }

        for (i in newOrder.indices.reversed()) {
            drawPile.cards.add(0, newOrder[i])
        }

        println("${player.name} rearranged the top ${newOrder.size} cards.")
    }
}