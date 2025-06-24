package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.cards.Card

class SkipEffect: CardEffect {
    override fun apply (card: Card, player: Player, cardLogic: CardLogic){
        val gameLogic = cardLogic.gameLogic

        gameLogic.nextTurn()
        println("Skip got played. ${player.name}, your turn is skipped")
    }

}