package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry

import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.cards.effects.BlankEffect
import com.aau.se2.boomboomkittens.game.cards.effects.DefuseEffect
import com.aau.se2.boomboomkittens.game.cards.effects.ExplodingKittenEffect
//import com.aau.se2.boomboomkittens.game.cards.effects.NopeEffect

// fixme remove this registry, as its an unnecessary manual matching
//  either add a constructor parameter for the effect to the CardType enum
//  or pass the CardEffect directly to the card, with potentially adding another getter to the effect for the enum's name
object CardEffectRegistry {
    private val effects = mapOf(
        CardType.BLANK to BlankEffect(),
        CardType.DEFUSE to DefuseEffect(),
        //CardType.NOPE to NopeEffect(),
        CardType.EXPLODING_KITTEN to ExplodingKittenEffect(),
        //CardType.SHUFFLE to ShuffleEffect()
    )

    fun getEffect (cardType: CardType): CardEffect {
        return effects [cardType]
            ?: throw IllegalArgumentException ("No effect registered for $cardType")
    }
}