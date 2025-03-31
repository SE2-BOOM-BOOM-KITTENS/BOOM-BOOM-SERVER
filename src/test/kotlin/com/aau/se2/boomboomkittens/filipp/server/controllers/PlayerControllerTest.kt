package com.aau.se2.boomboomkittens.filipp.server.controllers

import com.aau.se2.boomboomkittens.filipp.server.models.Player
import com.aau.se2.boomboomkittens.filipp.server.models.Lobby
import com.aau.se2.boomboomkittens.filipp.server.services.LobbyService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.times
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get


@ExtendWith(SpringExtension::class)
@WebMvcTest(PlayerController::class)
class PlayerControllerTest {

    @Autowired
    lateinit var mockMvc : MockMvc

    @MockitoBean
    lateinit var lobbyService: LobbyService

    @Test
    fun getPlayersTest(){
        val players = mutableListOf(Player(name = "player1"), Player(name = "player2"))
        val lobby = Lobby(players = players)

        given(lobbyService.getLobby(lobby.id.toString())).willReturn(lobby)

        mockMvc.get("/players?lobbyId=${lobby.id.toString()}")
            .andExpect {
                status { isOk() }
                jsonPath("$.size()"){value(2)}
                jsonPath("$[0].name"){value("player1")}
                jsonPath("$[1].name"){value("player2")}
            }

        Mockito.verify(lobbyService, times(1)).getLobby(lobby.id.toString())
    }
}