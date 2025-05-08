package com.aau.se2.boomboomkittens.game.cards

import com.aau.se2.boomboomkittens.game.cards.effects.BlankEffect
import com.aau.se2.boomboomkittens.game.cards.effects.DefuseEffect
import com.aau.se2.boomboomkittens.game.cards.effects.ExplodingKittenEffect

object CardEffectRegistry {
    private val effects = mapOf(
        CardType.BLANK to BlankEffect(),
        CardType.DEFUSE to DefuseEffect(),
        CardType.EXPLODING_KITTEN  to ExplodingKittenEffect()
    )

    fun getEffect (cardType: CardType): CardEffect {
        return effects [cardType]
            ?: throw IllegalArgumentException ("No effect registered for $cardType")
    }

}