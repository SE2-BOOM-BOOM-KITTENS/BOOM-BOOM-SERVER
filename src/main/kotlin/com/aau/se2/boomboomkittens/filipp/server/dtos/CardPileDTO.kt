package com.aau.se2.boomboomkittens.filipp.server.dtos

data class CardPileDTO(
    val cardCount: Int,
    val cards: List<CardDTO>?,
)