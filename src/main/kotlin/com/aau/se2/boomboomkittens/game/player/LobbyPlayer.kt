package com.aau.se2.boomboomkittens.game.player

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class LobbyPlayer @JsonCreator constructor(
    @JsonProperty("name") val name: String
)