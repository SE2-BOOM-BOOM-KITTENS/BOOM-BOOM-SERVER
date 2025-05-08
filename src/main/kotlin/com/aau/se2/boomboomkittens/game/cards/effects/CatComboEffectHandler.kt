package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.game.model.Card
import com.aau.se2.boomboomkittens.game.model.CardType
import com.aau.se2.boomboomkittens.game.model.Player
import com.aau.se2.boomboomkittens.game.logic.GameManager

object CatComboEffectHandler {

    fun handleComboRequest(player: Player, cards: List<Card>, allPlayers: List<Player>, gameManager: GameManager) {
        if (cards.isEmpty()) return

        when {
            cards.size == 2 && cardsHaveSameType(cards) -> {
                gameManager.requestRandomSteal(player)
            }

            cards.size == 3 && cardsHaveSameType(cards) -> {
                gameManager.requestSpecificSteal(player)
            }

            cards.size == 5 && cardsHaveDifferentTypes(cards) -> {
                gameManager.requestDiscardSelection(player, gameManager.discardPile)
            }

            else -> {
                println("Invalid Cat Card combo by ${player.name}")
            }
        }

        // Karten aus Spielerhand entfernen und in den Ablagestapel legen
        player.hand.removeAll(cards)
        gameManager.discardPile.addAll(cards)
    }

    fun resolveStealRandom(player: Player, target: Player) {
        val stolen = target.hand.randomCard()
        if (stolen != null) {
            target.hand.remove(stolen)
            player.hand.add(stolen)
            println("${player.name} randomly stole a card from ${target.name}")
        } else {
            println("${target.name} has no cards to steal.")
        }
    }

    fun resolveStealSpecific(player: Player, target: Player, cardType: CardType) {
        val card = target.hand.getCards().find { it.type == cardType }
        if (card != null) {
            target.hand.remove(card)
            player.hand.add(card)
            println("${player.name} stole $cardType from ${target.name}")
        } else {
            println("${target.name} doesn't have a $cardType card.")
        }
    }

    fun resolveDiscardSelection(player: Player, selectedCard: Card, gameManager: GameManager) {
        if (gameManager.discardPile.contains(selectedCard)) {
            gameManager.discardPile.remove(selectedCard)
            player.hand.add(selectedCard)
            println("${player.name} retrieved ${selectedCard.type} from the discard pile.")
        } else {
            println("Selected card not found in discard pile.")
        }
    }

    private fun cardsHaveSameType(cards: List<Card>) =
        cards.map { it.type }.toSet().size == 1

    private fun cardsHaveDifferentTypes(cards: List<Card>) =
        cards.map { it.type }.toSet().size == cards.size
}