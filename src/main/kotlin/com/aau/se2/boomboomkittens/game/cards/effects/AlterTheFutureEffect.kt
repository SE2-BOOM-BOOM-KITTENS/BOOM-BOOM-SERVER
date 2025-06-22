package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.player.Player

class AlterTheFutureEffect : CardEffect {
    override fun apply(card: Card, player: Player, cardLogic: CardLogic) {
        val topCards = cardLogic.peekTopCards(3)
        val rearranged = topCards.reversed() // Beispiel: umgekehrte Reihenfolge
        cardLogic.run { allowPlayerToRearrangeTopCards(player, rearranged) }
    }
}