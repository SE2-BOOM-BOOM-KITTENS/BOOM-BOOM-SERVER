package com.aau.se2.boomboomkittens.game.logic

import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.PlayerLogic
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class PlayerLogicTest {

    private lateinit var logic: PlayerLogic
    private lateinit var player1: Player
    private lateinit var player2: Player
    private lateinit var player3: Player

    @BeforeEach
    fun setup() {
        logic = PlayerLogic()
        player1 = Player(UUID.randomUUID(), "Alice")
        player2 = Player(UUID.randomUUID(), "Bob")
        player3 = Player(UUID.randomUUID(), "Charlie")
    }

    @Test
    fun `add players and get current player`() {
        logic.addPlayerByID(player1)
        assertEquals(player1, logic.getCurrentPlayer())

        logic.addPlayerByID(player2)
        logic.addPlayerByID(player3)

        assertEquals(3, logic.getPlayerCount())
    }

    @Test
    fun `addPlayerByID throws if duplicate`() {
        logic.addPlayerByID(player1)
        val exception = assertThrows(IllegalArgumentException::class.java) {
            logic.addPlayerByID(player1)
        }
        assertTrue(exception.message!!.contains("already exists"))
    }

    @Test
    fun `remove player updates current player if necessary`() {
        logic.addPlayerByID(player1)
        logic.addPlayerByID(player2)
        logic.addPlayerByID(player3)

        val currentBefore = logic.getCurrentPlayer()
        logic.removePlayerByID(currentBefore!!.playerId)
        assertNotEquals(currentBefore, logic.getCurrentPlayer())
        assertFalse(currentBefore.isAlive)
    }

    @Test
    fun `remove last remaining player sets current to null`() {
        logic.addPlayerByID(player1)
        logic.removePlayerByID(player1.playerId)
        assertNull(logic.getCurrentPlayer())
    }

    @Test
    fun `get player by ID returns correct player`() {
        logic.addPlayerByID(player1)
        logic.addPlayerByID(player2)

        assertEquals(player2, logic.getPlayerByID(player2.playerId))
        assertNull(logic.getPlayerByID(UUID.randomUUID()))
    }

    @Test
    fun `get player list returns all added players`() {
        logic.addPlayerByID(player1)
        logic.addPlayerByID(player2)
        logic.addPlayerByID(player3)

        val list = logic.getPlayerList()
        assertEquals(3, list.size)
        assertTrue(list.containsAll(listOf(player1, player2, player3)))
    }

    @Test
    fun `move to next player changes current`() {
        logic.addPlayerByID(player1)
        logic.addPlayerByID(player2)

        val original = logic.getCurrentPlayer()
        logic.moveToNextPlayer()
        val next = logic.getCurrentPlayer()
        assertNotEquals(original, next)
    }

    @Test
    fun `get next player skips dead players`() {
        logic.addPlayerByID(player1)
        logic.addPlayerByID(player2)
        logic.addPlayerByID(player3)

        logic.removePlayerByID(player2.playerId)
        val next = logic.getNextPlayer()

        assertEquals(player3.playerId, next?.playerId)
    }

    @Test
    fun `get next player returns self if only alive`() {
        logic.addPlayerByID(player1)
        assertEquals(player1, logic.getNextPlayer())
    }

    @Test
    fun `reverse order rotates correctly`() {
        logic.addPlayerByID(player1)
        logic.addPlayerByID(player2)
        logic.addPlayerByID(player3)

        val before = logic.getPlayerList().map { it.playerId }

        logic.reverseOrder()
        val after = logic.getPlayerList().map { it.playerId }

        assertEquals(before.toSet(), after.toSet())
        assertNotEquals(before, after)
    }

    @Test
    fun `get current player node returns correct node`() {
        logic.addPlayerByID(player1)
        assertNotNull(logic.getCurrentPlayerNode())
        assertEquals(player1.playerId, logic.getCurrentPlayerNode()!!.player.playerId)
    }

    @Test
    fun `getNextPlayer returns null when no one is alive`() {
        logic.addPlayerByID(player1)
        logic.removePlayerByID(player1.playerId)
        assertNull(logic.getNextPlayer())
    }
}
