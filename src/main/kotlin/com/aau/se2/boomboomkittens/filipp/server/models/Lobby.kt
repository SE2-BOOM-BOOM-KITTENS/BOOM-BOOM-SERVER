package com.aau.se2.boomboomkittens.filipp.server.models

import java.util.UUID

data class Lobby(val id:UUID = UUID.randomUUID(), val players:MutableList<Player>)