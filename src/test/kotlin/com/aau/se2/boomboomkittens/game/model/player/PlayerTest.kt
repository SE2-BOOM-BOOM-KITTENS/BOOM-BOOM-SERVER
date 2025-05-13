package com.aau.se2.boomboomkittens.game.model.player

import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class PlayerTest {

    @Test
    fun `player starts alive and has defuse`(){
        val player = Player(name = "Player1", defuseCount = 1, isAlive = true)

        val hasCard = player.hasDefuseCard()

        Assertions.assertTrue(player.isAlive)
        Assertions.assertTrue(hasCard)
        Assertions.assertEquals(1, player.defuseCount)
    }

    @Test
    fun `useDefuseCard decreases count`(){
        val player = Player(name = "Player2", defuseCount = 2, isAlive = true)

        val used = player.useDefuseCard()

        Assertions.assertTrue(used)
        Assertions.assertEquals(1, player.defuseCount)
    }

    @Test
    fun `useDefuseCard fails when none available`(){
        val player = Player(name = "Player3", defuseCount = 0, isAlive = true)

        val used = player.useDefuseCard()

        Assertions.assertFalse(used)
        Assertions.assertFalse(player.hasDefuseCard())
        Assertions.assertEquals(0, player.defuseCount)
    }

    @Test
    fun `hasDefuseCard fails when none available`(){
        val player = Player(name = "Player4", defuseCount = 0, isAlive = true)

        val hasCard = player.hasDefuseCard()

        Assertions.assertFalse(hasCard)
        Assertions.assertEquals(0, player.defuseCount)
    }

    @Test
    fun `addDefuseCard increases count`(){
        val player = Player(name = "Player5", defuseCount = 0, isAlive = true)

        player.addDefuseCard()

        Assertions.assertEquals(1, player.defuseCount)
    }


}