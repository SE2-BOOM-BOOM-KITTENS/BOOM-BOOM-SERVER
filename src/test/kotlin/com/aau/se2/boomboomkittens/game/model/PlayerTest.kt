package com.aau.se2.boomboomkittens.game.model

import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class PlayerTest {

    @Test
    fun `player starts alive and has defuse`(){
        val player = Player (playerId = UUID.fromString("1"), name = "Player1", defuseCount = 1, isAlive = true)

        val hasCard = player.hasDefuseCard()

        assertTrue (player.isAlive)
        assertTrue (hasCard)
        assertEquals (1, player.defuseCount)
    }

    @Test
    fun `useDefuseCard decreases count`(){
        val player = Player (playerId = UUID.fromString("2"), name = "Player2", defuseCount = 2, isAlive = true)

        val used = player.useDefuseCard()

        assertTrue (used)
        assertEquals (1, player.defuseCount)
    }

    @Test
    fun `useDefuseCard fails when none available`(){
        val player = Player (playerId = UUID.fromString("3"), name = "Player3", defuseCount = 0, isAlive = true)

        val used = player.useDefuseCard()

        assertFalse (used)
        assertFalse (player.hasDefuseCard())
        assertEquals (0, player.defuseCount)
    }

    @Test
    fun `hasDefuseCard fails when none available`(){
        val player = Player (playerId = UUID.fromString("4"), name = "Player4", defuseCount = 0, isAlive = true)

        val hasCard = player.hasDefuseCard()

        assertFalse (hasCard)
        assertEquals (0, player.defuseCount)
    }

    @Test
    fun `addDefuseCard increases count`(){
        val player = Player (playerId = UUID.fromString("5"), name = "Player5", defuseCount = 0, isAlive = true)

        player.addDefuseCard()

        assertEquals (1, player.defuseCount)
    }


}