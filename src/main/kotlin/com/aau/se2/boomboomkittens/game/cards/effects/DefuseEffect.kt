package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.game.logic.GameManager
import com.aau.se2.boomboomkittens.game.model.Player

class DefuseEffect : CardEffect {
    override fun apply (player: Player, gameManager: GameManager){
        player.useDefuseCard()
        println ("${player.name} used a Defuse card!")
    }
}