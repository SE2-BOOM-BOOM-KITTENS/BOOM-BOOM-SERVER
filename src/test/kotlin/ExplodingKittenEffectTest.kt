package com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.cards.effects.ExplodingKittenEffect
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class ExplodingKittenEffectTest {

    @Test
    fun `exploding kitten kills player without defuse`(){
        val player = Player (id = "1", name = "Player1", defuseCount = 0, isAlive = true)
        val effect = ExplodingKittenEffect()
        val gameLogic = GameLogic(UUID.randomUUID())

        effect.apply(player, gameLogic)

        assertFalse(player.isAlive)
    }

    @Test
    fun `exploding kitten defused when player has defuse card`(){
        val player = Player (id = "2", name = "Player2", defuseCount = 1, isAlive = true)
        val effect = ExplodingKittenEffect()
        val gameLogic = GameLogic(UUID.randomUUID())

        effect.apply(player, gameLogic)

        assertTrue(player.isAlive)
        assertEquals(0, player.defuseCount)
    }

}