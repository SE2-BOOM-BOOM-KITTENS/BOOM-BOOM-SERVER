package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.player.Player

class FeralCatEffect : CardEffect {
    override fun apply(card: Card, player: Player, cardLogic: CardLogic) {
        // Diese Karte wird nie direkt ausgespielt, sondern nur als Teil einer Kombo
        throw IllegalStateException("${card.type} kann nicht alleine ausgespielt werden. Kombiniere sie mit anderen Cat-Karten.")
    }
}
