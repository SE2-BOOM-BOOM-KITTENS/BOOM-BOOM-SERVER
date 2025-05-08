package com.aau.se2.boomboomkittens.game.logic.effects

import com.aau.se2.boomboomkittens.game.logic.CardEffect
import com.aau.se2.boomboomkittens.game.logic.GameManager
import com.aau.se2.boomboomkittens.game.model.Player

class ExplodingKittenEffect : CardEffect {
    override fun apply (player: Player, gameManager: GameManager){
        if (player.hasDefuseCard()){
            player.useDefuseCard()
            println ("${player.name} defused an Exploding Kitten!")
        } else {
            gameManager.eliminatePlayer(player)
        }
    }
}