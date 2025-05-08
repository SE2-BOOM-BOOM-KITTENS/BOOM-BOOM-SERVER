package com.aau.se2.boomboomkittens.filipp.server.models.player.playerCircle

import com.aau.se2.boomboomkittens.filipp.server.models.player.Player

class PlayerNode(val player: Player) {
    var next: PlayerNode? = null
    var previous: PlayerNode? = null
}