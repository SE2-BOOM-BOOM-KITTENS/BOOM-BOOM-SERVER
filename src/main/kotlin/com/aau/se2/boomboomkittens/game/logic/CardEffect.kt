package com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.game.model.Player
import com.aau.se2.boomboomkittens.game.logic.GameManager

interface CardEffect {
    fun apply (player: Player, gameManager: GameManager)
}