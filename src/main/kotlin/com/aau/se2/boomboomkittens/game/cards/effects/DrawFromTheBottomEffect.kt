package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.game.cards.Card

class DrawFromTheBottomEffect : CardEffect {
    override fun apply(card: Card, player: Player, cardLogic: CardLogic) {
        val bottomCard = cardLogic.drawPile.drawAt(cardLogic.drawPile.size - 1)
        player.playerHand.addCard(bottomCard)
        println("${player.name} drew ${bottomCard.name} from the bottom of the deck.")
        cardLogic.gameLogic.nextTurn()
    }
}