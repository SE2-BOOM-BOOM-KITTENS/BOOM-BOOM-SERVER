package com.aau.se2.boomboomkittens.filipp.server.controllers

import com.aau.se2.boomboomkittens.filipp.server.models.Player
import com.aau.se2.boomboomkittens.filipp.server.services.LobbyService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/players")
class PlayerController(private val lobbyService: LobbyService) {

    @GetMapping
    fun getPlayers(lobbyId:String): List<Player>{
        val lobby = lobbyService.getLobby(lobbyId)
        if (lobby != null) {
            return lobby.players
        }
        return listOf()
    }


}