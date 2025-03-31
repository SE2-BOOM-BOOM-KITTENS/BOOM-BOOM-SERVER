package com.aau.se2.boomboomkittens.filipp.server.models

import java.util.UUID

data class Player(val id:UUID = UUID.randomUUID(), val name:String)