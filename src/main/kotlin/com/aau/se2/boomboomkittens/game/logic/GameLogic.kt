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

    private var skipDraw = false
    private val extraTurns = mutableMapOf<UUID, Int>()


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
        val currentPlayer = playerLogic.getCurrentPlayer() ?: return

        if (consumeExtraTurn(currentPlayer.playerId)){
            println("${currentPlayer.name} has an extra turn.")
            // Player bleibt der gleiche, spielt noch eine Runde
        } else {
            playerLogic.moveToNextPlayer()
            println("Current Player: ${playerLogic.getCurrentPlayer()!!.name}")
        }

        resetSkipDraw()
    }

    /* alte Implementierung für Playerwechsel
    fun nextTurn(){
        _playerLogic.moveToNextPlayer()
        println("Current Player: ${playerLogic.getCurrentPlayer()!!.playerId}")
    }*/

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
                val card = drawPile.removeFirst()
                player.playerHand.addCard(card)
            }
        }
    }

    fun skipDrawForCurrentPlayer(){
        skipDraw = true
    }

    fun giveExtraTurnToNextPlayer(){
        val nextPlayer = playerLogic.getCurrentPlayerNode()?.next?.player
        if (nextPlayer != null){
            val currentExtra = extraTurns.getOrDefault(nextPlayer.playerId, 0)
            extraTurns[nextPlayer.playerId] = currentExtra + 1
        }
    }

    fun shouldSkipDraw(): Boolean{
        return skipDraw
    }

    fun resetSkipDraw() {
        skipDraw = false
    }

    fun getExtraTurns(playerId: UUID): Int{
        return extraTurns.getOrDefault(playerId, 0)
    }

    fun consumeExtraTurn(playerId: UUID): Boolean{
        val turnsLeft = extraTurns.getOrDefault(playerId, 0)
        return if (turnsLeft > 0){
            extraTurns[playerId] = turnsLeft - 1
            true
        } else {
            false
        }
    }

}