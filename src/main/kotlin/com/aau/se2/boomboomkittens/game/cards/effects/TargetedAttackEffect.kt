package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.player.Player

class TargetedAttackEffect(private val target: Player) : CardEffect {

    override fun apply(player: Player, gameLogic: GameLogic) {
        gameLogic.forceNextPlayerToDrawExtraCards(target, 2)
    }
}