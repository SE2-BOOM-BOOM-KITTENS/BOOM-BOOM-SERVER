package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.player.Player

class FeralCatEffect : CardEffect {
    override fun apply(player: Player, gameLogic: GameLogic) {
        // Diese Karte wird nie direkt ausgespielt, sondern nur als Teil einer Kombo
        println("Feral Cat kann nur als andere Cat Card verwendet werden.")
    }
}
