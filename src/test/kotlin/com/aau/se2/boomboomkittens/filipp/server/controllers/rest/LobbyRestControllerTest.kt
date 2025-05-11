package com.aau.se2.boomboomkittens.filipp.server.controllers.rest

import com.aau.se2.boomboomkittens.filipp.server.services.LobbyService
import com.aau.se2.boomboomkittens.game.Lobby
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.times
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

@ExtendWith(SpringExtension::class)
@WebMvcTest(LobbyRestController::class)
class LobbyRestControllerTest {

    @Autowired
    lateinit var mockMvc : MockMvc

    @MockBean
    lateinit var lobbyService: LobbyService

    @Test
    fun getLobbiesTest(){
        val dummyPlayer = Player(UUID.randomUUID(),"Dummy")
        val lobby1 = Lobby(creator = dummyPlayer , players = mutableListOf(), maxPlayers = 3)
        val lobby2 = Lobby(creator = dummyPlayer, players = mutableListOf(), maxPlayers = 3)
        val lobbies = ConcurrentHashMap<String, Lobby>()
        lobbies[lobby1.id.toString()] = lobby1
        lobbies[lobby2.id.toString()] = lobby2

        given(lobbyService.getLobbies()).willReturn(lobbies)

        mockMvc.get("/lobbies")
            .andExpect {
                status { isOk() }
                jsonPath("$.length()"){value(2)}
            }

        Mockito.verify(lobbyService, times(1)).getLobbies()
    }
}