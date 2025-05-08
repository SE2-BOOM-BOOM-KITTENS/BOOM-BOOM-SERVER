package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.game.logic.GameManager
import com.aau.se2.boomboomkittens.game.model.Player

class CatCardEffect : CardEffect {
    override fun apply(player: Player, gameManager: GameManager) {
        println("Cat cards must be played as combos.")
    }
}
