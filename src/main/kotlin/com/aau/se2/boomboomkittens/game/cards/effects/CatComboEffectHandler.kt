package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.player.Player

object CatComboEffectHandler {

    fun handleComboRequest(player: Player, cards: List<Card>, game: GameLogic) {
        val allPlayers = game.playerLogic.getPlayerList().filter { it.isAlive && it.playerId != player.playerId}

            when {
            cards.size == 2 && cardsHaveSameType(cards) -> {
                game.requestRandomSteal(player, allPlayers)
            }

            cards.size == 3 && cardsHaveSameType(cards) -> {
                game.requestSpecificSteal(player, allPlayers)
            }

            cards.size == 5 && cardsHaveDifferentTypes(cards) -> {
                game.requestDiscardSelection(player)
            }

            else -> {
                println("Invalid Cat Card combo by ${player.name}")
            }
        }

        // Karten aus Spielerhand entfernen und in den Ablagestapel legen
        player.playerHand.cards.removeAll(cards)
        game.discardPile.getPileList().addAll(cards)
    }

    fun resolveRandomSteal(player: Player, target: Player) {
        val stolen = target.playerHand.getRandomCard() ?: return
        target.playerHand.removeCard(stolen)
        player.playerHand.addCard(stolen)
        println("${player.name} randomly stole a card from ${target.name}")
    }

    fun resolveSpecificSteal(player: Player, target: Player, type: CardType) {
        val card = target.playerHand.cards.find { it.type == type } ?: return
        target.playerHand.removeCard(card)
        player.playerHand.addCard(card)
        println("${player.name} stole a card from ${target.name}")
    }

    fun resolveDiscardSelection(player: Player, selectedCard: Card, game: GameLogic) {
        if (game.discardPile.getPileList().remove(selectedCard)) {
            player.playerHand.addCard(selectedCard)
            println("${player.name} retrieved a card from the discard pile.")
        }
    }

    private fun cardsHaveSameType(cards: List<Card>) =
        cards.map { it.type }.toSet().size == 1

    private fun cardsHaveDifferentTypes(cards: List<Card>) =
        cards.map { it.type }.toSet().size == cards.size

    fun GameLogic.requestRandomSteal(player: Player, opponents: List<Player>) {
        println("Requesting ${player.name} to choose a player to steal from (random)")
        // TODO: WebSocket/Event-Auslösung zur UI mit Spielernamen
    }

    fun GameLogic.requestSpecificSteal(player: Player, opponents: List<Player>) {
        println("Requesting ${player.name} to choose a player and card type to steal")
        // TODO: WebSocket/Event-Auslösung zur UI mit Gegnern + auswählbaren Kartentypen
    }

    fun GameLogic.requestDiscardSelection(player: Player) {
        println("Requesting ${player.name} to choose a card from the discard pile")
        // TODO: WebSocket/Event-Auslösung zur UI mit Liste aus discardPile.getPileList()
    }
}
