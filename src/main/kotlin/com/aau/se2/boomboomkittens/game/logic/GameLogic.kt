package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffectRegistry
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.player.PlayerHand
import java.util.*

open class GameLogic(
    var lobbyId: UUID,
    val players: MutableList<Player> = mutableListOf(),
){
    private val _playerLogic: PlayerLogic = PlayerLogic()
    private val _cardLogic: CardLogic = CardLogic(players.size)
    private val _discardPile: CardPile = CardPile()
    private val _cardRegistry = CardEffectRegistry

    val playerLogic: PlayerLogic get() = _playerLogic
    val cardLogic: CardLogic get() = _cardLogic
    val discardPile: CardPile get() = _discardPile
    val cardRegistry: CardEffectRegistry get() = _cardRegistry

    val drawPile: LinkedList<Card> = LinkedList()

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
        _cardLogic.addPlayer(newPlayer)
    }

    fun getPlayerById(playerId: UUID): Player? {
        return _playerLogic.getPlayerByID(playerId)
    }

    fun getPlayerHand(playerId: UUID): PlayerHand? {
        return _cardLogic.getPlayerHand(playerId)
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

    fun shuffleDeck(){
        discardPile.shuffle()
    }

    fun notifyDeckShuffled(player: Player){
        // eventDispatcher.sendToAllPlayers("DeckShuffled", currentCardPileState())
    }
    open fun peekTopCards(count: Int): List<Card> {
        return drawPile.take(count)
    }

    open fun allowPlayerToRearrangeTopCards(player: Player, newOrder: List<Card>) {
        if (newOrder.size > drawPile.size) {
            throw IllegalArgumentException("New order has more cards than the draw pile.")
        }

        repeat(newOrder.size) {
            drawPile.removeFirst()
        }

        for (i in newOrder.size - 1 downTo 0) {
            drawPile.add(newOrder[i])
        }

        println("${player.name} rearranged the top ${newOrder.size} cards.")
    }

}