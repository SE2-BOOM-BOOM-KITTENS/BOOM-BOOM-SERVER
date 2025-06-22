package com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.player.Player
import java.util.*

class NopeEffect : CardEffect {
    override fun apply (card: Card, player: Player, cardLogic: CardLogic){
        val gameLogic = cardLogic.gameLogic

        gameLogic.nextTurn()
        println ("Nope got played. Your turn was skipped.")
    }
}