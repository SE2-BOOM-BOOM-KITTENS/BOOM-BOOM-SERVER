package com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry.CardEffectRegistry
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.cards.effects.BlankEffect
import com.aau.se2.boomboomkittens.game.cards.effects.DefuseEffect
import com.aau.se2.boomboomkittens.game.cards.effects.ExplodingKittenEffect
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CardEffectRegistryTest {

    @Test
    fun `returns DefuseEffect for DEFUSE card`(){
        val effect = CardEffectRegistry.getEffect(CardType.DEFUSE)
        assertTrue(effect is DefuseEffect)
    }

    @Test
    fun `returns BlankEffect for BLANK card`(){
        val effect = CardEffectRegistry.getEffect(CardType.BLANK)
        assertTrue(effect is BlankEffect)
    }

    @Test
    fun `returns ExplodingKittenEffect for EXPLODING_KITTEN card`(){
        val effect = CardEffectRegistry.getEffect(CardType.EXPLODING_KITTEN)
        assertTrue(effect is ExplodingKittenEffect)
    }

    @Test
    fun `throws exception for unregistered card type`(){
        // Simulierter nicht registrierter Typ TEST
        // TEST existiert im Enum, ist aber nicht in effects-Map registriert.
        assertThrows<IllegalArgumentException> {
            CardEffectRegistry.getEffect(CardType.TEST)
        }
    }

}