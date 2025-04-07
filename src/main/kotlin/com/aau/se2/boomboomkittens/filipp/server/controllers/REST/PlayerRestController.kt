package com.aau.se2.boomboomkittens.filipp.server.controllers.REST
import com.aau.se2.boomboomkittens.filipp.server.models.Player
import com.aau.se2.boomboomkittens.filipp.server.services.PlayerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/players")
class PlayerRestController(private val playerService: PlayerService) {


    @GetMapping
    fun getPlayerById(){}


    @PostMapping
    fun registerPlayer(@RequestBody player: Player):String{
        playerService.createPlayer(player.name)
        return "Received ${player.name}"
    }


}