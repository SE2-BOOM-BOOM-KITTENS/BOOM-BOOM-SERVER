package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.game.player.Player

class AlterTheFutureEffect : CardEffect {
    override fun apply(player: Player, gameLogic: GameLogic) {
        val topCards = gameLogic.peekTopCards(3)
        println("${player.name} is altering the top 3 cards: $topCards")
        gameLogic.allowPlayerToRearrangeTopCards(player, topCards)
    }
}