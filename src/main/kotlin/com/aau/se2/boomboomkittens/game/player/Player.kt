package com.aau.se2.boomboomkittens.game.player

import java.util.UUID

data class Player(
    val playerId: UUID = UUID.randomUUID(),
    val name: String,
    var defuseCount: Int = 1,
    var isAlive: Boolean = true,
    val status: String = "ACTIVE",
    val playerHand: PlayerHand = PlayerHand(playerId),
){

    fun useDefuseCard(): Boolean {
        return if (defuseCount > 0){
            defuseCount--
            true
        } else {
            false
        }
    }

    fun hasDefuseCard(): Boolean{
        return defuseCount > 0
    }

    fun addDefuseCard(){
        defuseCount++
    }

}





