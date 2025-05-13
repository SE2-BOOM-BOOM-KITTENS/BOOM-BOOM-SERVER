package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.ShuffleEffect
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.cards.effects.DefuseEffect
import com.aau.se2.boomboomkittens.game.cards.effects.ExplodingKittenEffect

object CardEffectRegistry {
    private val effects = mapOf(
        CardType.DEFUSE to DefuseEffect(),
        CardType.EXPLODING_KITTEN to ExplodingKittenEffect(),
        CardType.SHUFFLE to ShuffleEffect()
    )

    fun getEffect (cardType: CardType): CardEffect {
        return effects [cardType]
            ?: throw IllegalArgumentException ("No effect registered for $cardType")
    }

}