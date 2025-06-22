package com.aau.se2.boomboomkittens.game.player

// fixme find another name for this class as its semantic is not clear, eg., table order / turn link etc
class PlayerNode(val player: Player) {
    var next: PlayerNode? = null
    var previous: PlayerNode? = null
}