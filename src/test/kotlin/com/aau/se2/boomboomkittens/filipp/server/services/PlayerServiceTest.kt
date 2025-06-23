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
        val id = playerService.createPlayer("Player1")
        val player = playerService.getPlayer(id)

        assertNotNull(player)
        assertNotNull(player.playerId)
        assertNotNull(player.name)
    }

    @Test
    fun getPlayersTest(){
        val id1 = playerService.createPlayer("player1")
        val id2 = playerService.createPlayer("player2")

        val player1 = playerService.getPlayer(id1)
        val player2 = playerService.getPlayer(id2)
        val players = playerService.getPlayers()

        assertEquals(2, players.size)
        assertTrue(players.containsKey(player1.playerId))
        assertTrue(players.containsKey(player2.playerId))
    }

    @Test
    fun getPlayerTest(){
        val id = playerService.createPlayer("player")

        val player = playerService.getPlayer(id)
        val fetchedPlayer = playerService.getPlayer(player.playerId)

        assertNotNull(fetchedPlayer)
        // fixme the assertNotNull already guards the next line by throwing an exception if its null
        assertEquals(player.playerId, fetchedPlayer.playerId)
    }

    @Test
    fun removePlayerTest(){
        val id = playerService.createPlayer("player")
        val player = playerService.getPlayer(id)

        val fetchedPlayer = playerService.getPlayer(player.playerId)
        assertNotNull(fetchedPlayer)

        playerService.removePlayer(player.playerId)

        assertThrows(IllegalStateException::class.java){
            playerService.getPlayer(player.playerId)
        }


    }
}