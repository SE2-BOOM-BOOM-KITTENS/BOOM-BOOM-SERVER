package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.player.Player

class ShuffleEffect: CardEffect {

    override fun apply (player: Player, gameLogic: GameLogic){
        gameLogic.shuffleDeck()
        gameLogic.notifyDeckShuffled(player)
        println("${player.name} shuffled the card pile!")
    }
}