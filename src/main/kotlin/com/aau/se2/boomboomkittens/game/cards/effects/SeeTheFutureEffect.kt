package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.game.player.Player

class SeeTheFutureEffect : CardEffect {
    override fun apply(player: Player, gameLogic: GameLogic) {
        val topCards = gameLogic.peekTopCards(3) // Nimm die obersten 3 Karten
        println("${player.name} looked at the top 3 cards: $topCards")
    }
}