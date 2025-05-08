package com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.game.model.CardType
import com.aau.se2.boomboomkittens.game.logic.effects.*

object CardEffectRegistry {
    private val effects = mapOf(
        CardType.DEFUSE to DefuseEffect(),
        CardType.EXPLODING_KITTEN  to ExplodingKittenEffect(),
        CardType.CAT_TACO to CatCardEffect(),
        CardType.CAT_BEARD to CatCardEffect(),
        CardType.CAT_CATERMELON to CatCardEffect(),
        CardType.CAT_HAIRY_POTATO to CatCardEffect(),
        CardType.CAT_RAINBOW_RALPHING to CatCardEffect(),
    )

    fun getEffect (cardType: CardType): CardEffect{
        return effects [cardType]
            ?: throw IllegalArgumentException ("No effect registered for $cardType")
    }

}