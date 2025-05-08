package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.game.cards.CardEffect
import com.aau.se2.boomboomkittens.game.logic.GameManager
import com.aau.se2.boomboomkittens.game.player.Player

class BlankEffect : CardEffect {
    override fun apply (player: Player, gameManager: GameManager){
        println ("Blank card played. Nothing happens.")
    }
}