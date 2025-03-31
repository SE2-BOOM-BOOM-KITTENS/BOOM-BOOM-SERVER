package com.aau.se2.boomboomkittens.filipp.server.controllers

import com.aau.se2.boomboomkittens.filipp.server.models.Lobby
import com.aau.se2.boomboomkittens.filipp.server.services.LobbyService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ConcurrentHashMap


@RestController
@RequestMapping("/lobbies")
class LobbiesController(private val lobbyService: LobbyService) {

    @GetMapping
    fun getLobbies(): ConcurrentHashMap<String,Lobby> = lobbyService.getLobbies()

}