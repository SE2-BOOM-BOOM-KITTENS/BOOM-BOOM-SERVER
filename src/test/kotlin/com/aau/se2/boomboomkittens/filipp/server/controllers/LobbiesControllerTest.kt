package com.aau.se2.boomboomkittens.filipp.server.controllers

import com.aau.se2.boomboomkittens.filipp.server.models.Lobby
import com.aau.se2.boomboomkittens.filipp.server.services.LobbyService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.times
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.util.concurrent.ConcurrentHashMap

@ExtendWith(SpringExtension::class)
@WebMvcTest(LobbiesController::class)
class LobbiesControllerTest {

    @Autowired
    lateinit var mockMvc : MockMvc

    @MockitoBean
    lateinit var lobbyService: LobbyService

    @Test
    fun getLobbiesTest(){
        val lobby1 = Lobby(players = mutableListOf())
        val lobby2 = Lobby(players = mutableListOf())
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