package com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.game.model.CardType
import com.aau.se2.boomboomkittens.game.logic.effects.*

object CardEffectRegistry {
    private val effects = mapOf(
        CardType.BLANK to BlankEffect(),
        CardType.DEFUSE to DefuseEffect(),
        CardType.EXPLODING_KITTEN  to ExplodingKittenEffect()
    )

    fun getEffect (cardType: CardType): CardEffect{
        return effects [cardType]
            ?: throw IllegalArgumentException ("No effect registered for $cardType")
    }

}