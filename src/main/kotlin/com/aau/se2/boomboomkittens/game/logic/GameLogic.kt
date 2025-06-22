package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.player.PlayerHand
import java.util.*

open class GameLogic(
    var lobbyId: UUID,
    val players: MutableList<Player>
){

    private val _playerLogic: PlayerLogic = PlayerLogic()
    private val _cardLogic: CardLogic = CardLogic(players.size, this)

    val playerLogic: PlayerLogic get() = _playerLogic
    val cardLogic: CardLogic get() = _cardLogic
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
        println("Current Player: ${playerLogic.getCurrentPlayer()!!.playerId}")
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
    fun forceNextPlayerToDrawExtraCards(player: Player, amount: Int) {
        if (amount <= 0) return

        repeat(amount) {
            if (!drawPile.isEmpty()) {
                val card = drawPile.removeFirst() // ← hier angepasst!
                player.playerHand.addCard(card)
            }
        }
    }

}