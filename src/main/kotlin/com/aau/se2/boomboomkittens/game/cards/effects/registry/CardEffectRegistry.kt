package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry

import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.cards.effects.BlankEffect
import com.aau.se2.boomboomkittens.game.cards.effects.DefuseEffect
import com.aau.se2.boomboomkittens.game.cards.effects.ExplodingKittenEffect
import com.aau.se2.boomboomkittens.game.cards.effects.SeeTheFutureEffect
import com.aau.se2.boomboomkittens.game.cards.effects.AltertheFutureEffect

object CardEffectRegistry {
    private val effects = mapOf(
        CardType.BLANK to BlankEffect(),
        CardType.DEFUSE to DefuseEffect(),
        CardType.EXPLODING_KITTEN to ExplodingKittenEffect()
        CardType.SEETHEFUTURE to SeeTheFutureEffect()
        CardType.ALTERTHEFUTRE to AlterTheFutureEffect()
    )

    fun getEffect (cardType: CardType): CardEffect {
        return effects [cardType]
            ?: throw IllegalArgumentException ("No effect registered for $cardType")
    }

}