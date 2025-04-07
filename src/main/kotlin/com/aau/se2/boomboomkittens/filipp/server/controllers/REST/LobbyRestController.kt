package com.aau.se2.boomboomkittens.filipp.server.controllers.REST

import com.aau.se2.boomboomkittens.filipp.server.models.Lobby
import com.aau.se2.boomboomkittens.filipp.server.models.Player
import com.aau.se2.boomboomkittens.filipp.server.services.LobbyService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ConcurrentHashMap


@RestController
@RequestMapping("/lobbies")
class LobbyRestController(private val lobbyService: LobbyService) {

    @GetMapping
    fun getLobbies(): ConcurrentHashMap<String,Lobby> = lobbyService.getLobbies()

    @GetMapping("/players")
    fun getPlayersInLobby(@RequestHeader lobbyId:String): List<Player>{
        val lobby = lobbyService.getLobby(lobbyId)
        if (lobby != null) {
            return lobby.players
        }
        return listOf()
    }

    @PostMapping
    fun createLobby(@RequestHeader creator:Player, maxPlayers:Int): String{
        val lobby = lobbyService.createLobby(creator,maxPlayers)
        if(lobby != null){
            return "Created lobby ${lobby.id}"
        }
        return "Failed to create lobby"
    }

}