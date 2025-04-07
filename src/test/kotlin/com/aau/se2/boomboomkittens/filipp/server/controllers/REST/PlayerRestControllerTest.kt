package com.aau.se2.boomboomkittens.filipp.server.controllers.REST

import com.aau.se2.boomboomkittens.filipp.server.models.Player
import com.aau.se2.boomboomkittens.filipp.server.services.PlayerService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import java.util.UUID


@ExtendWith(SpringExtension::class)
@WebMvcTest(PlayerRestController::class)
class PlayerRestControllerTest {

    @Autowired
    lateinit var mockMvc : MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var playerService: PlayerService

    @Test
    fun getPlayerByIdTest(){
        val id = UUID.randomUUID()
        val player = Player(id,"Dummy")


        given(playerService.getPlayer(player.id.toString())).willReturn(player)

        mockMvc.get("/players"){
            header("id",id)
        }.andExpect {
            status { isOk() }
            jsonPath("$.name"){value("Dummy")}
            jsonPath("$.id"){value(id.toString())}
        }
        }

    @Test
    fun registerPlayerTest(){
        val player = Player(null, "Dummy")
        val playerJson = objectMapper.writeValueAsString(player)


        mockMvc.post("/players"){
            contentType = MediaType.APPLICATION_JSON
            content = playerJson
        }.andExpect {
            status { isOk() }
            content { string("Received Dummy") }
        }
    }
    }
