package com.aau.se2.boomboomkittens.filipp.server.services

import com.aau.se2.boomboomkittens.filipp.server.models.player.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID



@SpringBootTest
class LobbyServiceTest {

    @Autowired
    lateinit var lobbyService: LobbyService


    @Test
    fun createLobbyTest(){
        val creatorDummy = Player(UUID.randomUUID(),"Dummy")
        val lobby = lobbyService.createLobby(creatorDummy,3)

        assertNotNull(lobby)
        assertNotNull(lobby.id)
        assertTrue(lobby.players.isEmpty())
    }

    @Test
    fun getLobbiesTest(){
        val creatorDummy = Player(UUID.randomUUID(),"Dummy")
        val lobby1 = lobbyService.createLobby(creatorDummy,3)
        val lobby2 = lobbyService.createLobby(creatorDummy,3)

        val lobbies = lobbyService.getLobbies()

        assertEquals(2,lobbies.size)
        assertTrue(lobbies.containsKey(lobby1.id.toString()))
        assertTrue(lobbies.containsKey(lobby2.id.toString()))
    }

    @Test
    fun getLobbyTest(){
        val creatorDummy = Player(UUID.randomUUID(),"Dummy")
        val lobby = lobbyService.createLobby(creatorDummy,3)

        val fetchedLobby = lobbyService.getLobby(lobby.id.toString())

        assertNotNull(fetchedLobby)
        if (fetchedLobby != null) {
            assertEquals(lobby.id, fetchedLobby.id)
        }
    }

    @Test
    fun joinPlayerTest(){
        val creatorDummy = Player(UUID.randomUUID(),"Dummy")
        val lobby = lobbyService.createLobby(creatorDummy,3)
        val player = Player(name = "Player1")

        lobbyService.joinPlayer(lobby.id.toString(), player)

        assertTrue(lobby.players.contains(player))
    }

    @Test
    fun removePlayerTest(){
        val creatorDummy = Player(UUID.randomUUID(),"Dummy")
        val lobby = lobbyService.createLobby(creatorDummy,3)
        val player = Player(name = "Player1")
        lobbyService.joinPlayer(lobby.id.toString(), player)

        assertTrue(lobby.players.contains(player))

        lobbyService.removePlayer(lobby.id.toString(), player)

        val deletedLobby = lobbyService.getLobby(lobby.id.toString())
        assertNull(deletedLobby)
    }
}