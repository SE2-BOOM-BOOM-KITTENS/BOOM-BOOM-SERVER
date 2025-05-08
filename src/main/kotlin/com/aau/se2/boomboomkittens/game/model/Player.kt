package com.aau.se2.boomboomkittens.game.model

data class Player(
    val id: String,
    val name: String,
    var defuseCount: Int = 1,
    var isAlive: Boolean = true
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





