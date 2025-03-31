package com.aau.se2.boomboomkittens.filipp.server.services

import com.aau.se2.boomboomkittens.filipp.server.models.Player
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@SpringBootTest
class LobbyServiceTest {

    @Autowired
    lateinit var lobbyService: LobbyService

    @Test
    fun createLobbyTest(){
        val lobby = lobbyService.createLobby()

        assertNotNull(lobby)
        assertNotNull(lobby.id)
        assertTrue(lobby.players.isEmpty())
    }

    @Test
    fun getLobbiesTest(){
        val lobby1 = lobbyService.createLobby()
        val lobby2 = lobbyService.createLobby()

        val lobbies = lobbyService.getLobbies()

        assertEquals(2,lobbies.size)
        assertTrue(lobbies.containsKey(lobby1.id.toString()))
        assertTrue(lobbies.containsKey(lobby2.id.toString()))
    }

    @Test
    fun getLobbyTest(){
        val lobby = lobbyService.createLobby()

        val fetchedLobby = lobbyService.getLobby(lobby.id.toString())

        assertNotNull(fetchedLobby)
        assertEquals(lobby.id, fetchedLobby.id)
    }

    @Test
    fun joinPlayerTest(){
        val lobby = lobbyService.createLobby()
        val player = Player(name = "Player1")

        lobbyService.joinPlayer(lobby.id.toString(), player)

        assertTrue(lobby.players.contains(player))
    }

    @Test
    fun removePlayerTest(){
        val lobby = lobbyService.createLobby()
        val player = Player(name = "Player1")
        lobbyService.joinPlayer(lobby.id.toString(), player)

        assertTrue(lobby.players.contains(player))

        lobbyService.removePlayer(lobby.id.toString(), player)

        val deletedLobby = lobbyService.getLobby(lobby.id.toString())
        assertNull(deletedLobby)
    }
}