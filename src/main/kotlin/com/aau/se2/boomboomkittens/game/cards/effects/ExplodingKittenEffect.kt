package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.game.player.Player

class ExplodingKittenEffect : CardEffect {
    override fun apply (player: Player, gameLogic: GameLogic){
        if (player.hasDefuseCard()){
            player.useDefuseCard()
            println ("${player.name} defused an Exploding Kitten!")
        } else {
            player.isAlive = false
            gameLogic.removePlayer(player.playerId)
        }
    }
}