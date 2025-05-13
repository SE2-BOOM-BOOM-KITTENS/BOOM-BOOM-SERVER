package com.aau.se2.boomboomkittens.game.cards

data class Card(
    val type: CardType
) {
    val name: String
        get() = when (type) {
            CardType.SEETHEFUTURE -> "See The Future"
            CardType.ALTERTHEFUTURE -> "Alter The Future"
            CardType.DEFUSE -> "Defuse"
            CardType.EXPLODING_KITTEN -> "Exploding Kitten"
            CardType.TEST -> "Test"
            CardType.BLANK -> "Blank"
}}


