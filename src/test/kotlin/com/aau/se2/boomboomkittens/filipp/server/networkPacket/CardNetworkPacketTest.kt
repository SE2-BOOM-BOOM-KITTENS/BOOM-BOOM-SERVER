package com.aau.se2.boomboomkittens.filipp.server.networkPacket

import com.aau.se2.boomboomkittens.game.cards.CardType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CardNetworkPacketTest {

    @Test
    fun `should correctly store type and name`() {
        val packet = CardNetworkPacket(name = "Taco Cat", type = CardType.CAT_TACO)
        assertEquals("Taco Cat", packet.name)
        assertEquals(CardType.CAT_TACO, packet.type)
    }

    @Test
    fun `should support aliasType for FeralCat`() {
        val packet = CardNetworkPacket(name = "Feral Cat", type = CardType.FERAL_CAT, aliasType = CardType.CAT_BEARD)
        assertEquals(CardType.FERAL_CAT, packet.type)
        assertEquals(CardType.CAT_BEARD, packet.aliasType)
    }
}
