package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.game.player.Player

class NopeEffect : CardEffect {
    override fun apply (player: Player, gameLogic: GameLogic){
        gameLogic.nextTurn()
        println ("Nope got played. Your turn was skipped.")
    }
}