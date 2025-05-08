package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.CardEffect
import com.aau.se2.boomboomkittens.game.player.Player

class BlankEffect : CardEffect {
    override fun apply (player: Player, gameLogic: GameLogic){
        println ("Blank card played. Nothing happens.")
    }
}