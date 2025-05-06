package com.aau.se2.boomboomkittens.filipp.server.controllers.rest
import com.aau.se2.boomboomkittens.filipp.server.models.player.Player
import com.aau.se2.boomboomkittens.filipp.server.services.PlayerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/players")
class PlayerRestController(private val playerService: PlayerService) {


    @GetMapping
    fun getPlayerById(@RequestHeader id:String): Player? {
        val player = playerService.getPlayer(id)
        return player
    }

    @GetMapping("/allPlayers")
    fun getAllPlayers(): List<Player> {
        return playerService.getPlayers().values.toList()

    }


    @PostMapping
    fun registerPlayer(@RequestBody player: Player):String{
        playerService.createPlayer(player.name)
        return "Received ${player.name}"
    }


}