package com.aau.se2.boomboomkittens.filipp.server.services


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PlayerServiceTest {

    @Autowired
    lateinit var playerService: PlayerService

    @BeforeEach
    fun resetPlayers(){
        playerService.clearPlayers()
    }

    @Test
    fun createPlayerTest(){
        val player = playerService.createPlayer("Player1")

        assertNotNull(player)
        assertNotNull(player.playerId)
        assertNotNull(player.name)
    }

    @Test
    fun getPlayersTest(){
        val player1 = playerService.createPlayer("player1")
        val player2 = playerService.createPlayer("player2")

        val players = playerService.getPlayers()

        assertEquals(2, players.size)
        assertTrue(players.containsKey(player1.playerId.toString()))
        assertTrue(players.containsKey(player2.playerId.toString()))
    }

    @Test
    fun getPlayerTest(){
        val player = playerService.createPlayer("player")

        val fetchedPlayer = playerService.getPlayer(player.playerId.toString())

        assertNotNull(fetchedPlayer)
        if (fetchedPlayer != null) {
            assertEquals(player.playerId, fetchedPlayer.playerId)
        }
    }

    @Test
    fun removePlayerTest(){
        val player = playerService.createPlayer("player")

        val fetchedPlayer = playerService.getPlayer(player.playerId.toString())
        assertNotNull(fetchedPlayer)

        playerService.removePlayer(player.playerId.toString())

        val deletedPlayer = playerService.getPlayer(player.playerId.toString())
        assertNull(deletedPlayer)
    }
}