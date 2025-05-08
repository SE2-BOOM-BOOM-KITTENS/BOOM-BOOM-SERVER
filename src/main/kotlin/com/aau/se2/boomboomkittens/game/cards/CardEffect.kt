package com.aau.se2.boomboomkittens.game.cards

import com.aau.se2.boomboomkittens.game.logic.GameManager
import com.aau.se2.boomboomkittens.game.player.Player

interface CardEffect {
    fun apply (player: Player, gameManager: GameManager)
}