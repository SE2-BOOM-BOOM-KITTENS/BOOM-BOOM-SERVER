package com.aau.se2.boomboomkittens.game

import com.aau.se2.boomboomkittens.game.player.Player
import java.util.UUID

data class Lobby(val id:UUID = UUID.randomUUID(),
                 val creator: Player,
                 val players:MutableList<Player>,
                 val maxPlayers:Int)