package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.player.PlayerHand
import java.util.*

open class GameLogic(
    var lobbyId: UUID,
    val players: MutableList<Player> = mutableListOf(),
) {
    private val _playerLogic: PlayerLogic = PlayerLogic()
    private val _cardLogic: CardLogic = CardLogic(players.size, _playerLogic)

    val playerLogic: PlayerLogic get() = _playerLogic
    val cardLogic: CardLogic get() = _cardLogic
    val drawPile: CardPile get() = _cardLogic.drawPile

    init {
        for (player in players) {
            _playerLogic.addPlayerByID(player)
            _cardLogic.giveInitialHand(player)
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

    fun skipPlayer() {
        nextTurn()
        nextTurn()
    }

    fun addPlayer(playerId: UUID, playerName: String) {
        val newPlayer = Player(playerId, playerName)
        _playerLogic.addPlayerByID(newPlayer)
        _cardLogic.giveInitialHand(newPlayer)
    }

    fun getPlayerById(playerId: UUID): Player? {
        return _playerLogic.getPlayerByID(playerId)
    }

    fun getPlayerHand(playerId: UUID): PlayerHand? {
        return _cardLogic.getPlayerHand(playerId)
    }
}
