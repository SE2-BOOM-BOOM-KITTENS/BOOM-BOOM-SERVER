package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.player.Player

class ReverseEffect : CardEffect {
    override fun apply(card: Card, player: Player, cardLogic: CardLogic) {
        cardLogic.gameLogic.playerLogic.reverseOrder()

        // Der Spieler muss NICHT ziehen!
        // Also Zug sofort weitergeben:
        cardLogic.gameLogic.nextTurn()
    }
}
