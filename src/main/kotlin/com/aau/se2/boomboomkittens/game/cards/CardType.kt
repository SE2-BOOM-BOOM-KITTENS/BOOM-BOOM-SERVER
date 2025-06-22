package com.aau.se2.boomboomkittens.game.cards

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.AlterTheFutureEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.FeralCatEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.SeeTheFutureEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.ShuffleEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffect
import com.aau.se2.boomboomkittens.game.cards.effects.*

enum class CardType(val effect: CardEffect) {
    EXPLODING_KITTEN(ExplodingKittenEffect()),
    DEFUSE(DefuseEffect()),
    BLANK(BlankEffect()),
    CAT_TACO(CatCardEffect()),
    CAT_BEARD(CatCardEffect()),
    CAT_HAIRY_POTATO(CatCardEffect()),
    CAT_RAINBOW_RALPHING(CatCardEffect()),
    CAT_CATERMELON(CatCardEffect()),
    SHUFFLE(ShuffleEffect()),
    ALTER_THE_FUTURE(AlterTheFutureEffect()),
    SEE_THE_FUTURE(SeeTheFutureEffect()),
    NOPE(NopeEffect()),
    FERAL_CAT(FeralCatEffect()),
    TARGETED_ATTACK(TODO()),
}