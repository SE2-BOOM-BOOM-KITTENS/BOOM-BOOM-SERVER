package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.player.Player

class FavorEffect : CardEffect {
    override fun apply(card: Card, player: Player, cardLogic: CardLogic) {
        val gameLogic = cardLogic.gameLogic
        val target = gameLogic.playerLogic.getNextPlayer()
            ?: throw IllegalStateException("No player available to give a card.")

        val stolenCard = target.playerHand.getRandomCard()
        if (stolenCard != null) {
            target.playerHand.removeCard(stolenCard)
            player.playerHand.addCard(stolenCard)
        }
    }
}