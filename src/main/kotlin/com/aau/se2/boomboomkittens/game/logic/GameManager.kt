package com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.filipp.server.controllers.webSocket.SessionWebSocketController
import com.aau.se2.boomboomkittens.game.logic.effects.CatComboEffectHandler
import com.aau.se2.boomboomkittens.game.model.Card
import com.aau.se2.boomboomkittens.game.model.CardType
import com.aau.se2.boomboomkittens.game.model.Player

class GameManager(private val messagingService: SessionWebSocketController) {

    private val players = mutableListOf<Player>()
    var currentPlayerIndex = 0
    val discardPile = mutableListOf<Card>()

    fun requestRandomSteal(player: Player) {
        val validTargets = players.filter { it != player && it.isAlive && it.hand.size() > 0 }

        if (validTargets.size == 1) {
            CatComboEffectHandler.resolveStealRandom(player, validTargets.first())
        } else {
            messagingService.sendToClient(
                player.id,
                mapOf(
                    "action" to "requestRandomStealTarget",
                    "validTargets" to validTargets.map { it.id }
                )
            )
        }
    }

    fun requestSpecificSteal(player: Player) {
        val validTargets = players.filter { it != player && it.isAlive }

        messagingService.sendToClient(
            player.id,
            mapOf(
                "action" to "requestSpecificSteal",
                "validTargets" to validTargets.map { it.id },
                "cardOptions" to CardType.entries.map { it.name } // alle Typen
            )
        )
    }

    fun requestDiscardSelection(player: Player, discardPile: List<Card>) {
        messagingService.sendToClient(
            player.id,
            mapOf(
                "action" to "requestDiscardCardChoice",
                "discardPile" to discardPile.map {
                    mapOf("id" to it.hashCode().toString(), "type" to it.type.name)
                }
            )
        )
    }

    fun addPlayer (player: Player){
        players.add(player)
    }

    fun eliminatePlayer (player:Player){
        player.isAlive = false
        println ("${player.name} has been eliminated!")
    }

    fun getCurrentPlayer(): Player {
        return players[currentPlayerIndex]
    }

}



