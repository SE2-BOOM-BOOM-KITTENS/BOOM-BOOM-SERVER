package com.aau.se2.boomboomkittens.filipp.server.controllers.rest
import com.aau.se2.boomboomkittens.filipp.server.services.PlayerService
import com.aau.se2.boomboomkittens.game.player.Player
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID


@RestController
@RequestMapping("/players")
class PlayerRestController(private val playerService: PlayerService) {
    private val logger = LoggerFactory.getLogger(PlayerRestController::class.java)


    @GetMapping
    fun getPlayerById(@RequestHeader id:String): Player? {
        val uuid = UUID.fromString(id)
        val player = playerService.getPlayer(uuid)
        return player
    }

    @GetMapping("/allPlayers")
    fun getAllPlayers(): List<Player> {
        return playerService.getPlayers().values.toList()

    }


    @PostMapping
    fun registerPlayer(@RequestBody name: String):String{

        val uuid =  playerService.createPlayer(name)
        val logName = name.replace("[\n\r]".toRegex(), "_")
        logger.info("Player $logName registered with id $uuid")
        return uuid.toString()
    }


}