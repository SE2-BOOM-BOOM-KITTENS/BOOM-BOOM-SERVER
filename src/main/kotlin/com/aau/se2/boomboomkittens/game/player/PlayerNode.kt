package com.aau.se2.boomboomkittens.game.player

class PlayerNode(val player: Player) {
    var next: PlayerNode? = null
    var previous: PlayerNode? = null
}