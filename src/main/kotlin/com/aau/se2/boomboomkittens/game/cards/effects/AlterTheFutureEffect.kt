package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.player.Player

class AlterTheFutureEffect : CardEffect {
    override fun apply(card: Card, player: Player, gameLogic: GameLogic) {
        val topCards = gameLogic.peekTopCards(3)
        val rearranged = topCards.reversed() // Beispiel: umgekehrte Reihenfolge
        gameLogic.run { allowPlayerToRearrangeTopCards(player, rearranged) }
    }
}