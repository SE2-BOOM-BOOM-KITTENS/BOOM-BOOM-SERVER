package com.aau.se2.boomboomkittens.filipp.server.networkPacket.messages

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.filipp.server.networkPacket.messages.PlayerMessage
import com.aau.se2.boomboomkittens.filipp.server.networkPacket.CardNetworkPacket
import com.aau.se2.boomboomkittens.game.cards.CardType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class PlayerMessageTest {

    @Test
    fun `should store playerName and action`() {
        val message = PlayerMessage(
            playerName = "Steve",
            action = "CAT_COMBO",
            payload = emptyList<Any?>(),
            targetId = "1234"
        )

        assertEquals("Steve", message.playerName)
        assertEquals("CAT_COMBO", message.action)
        assertEquals("1234", message.targetId)
    }

    @Test
    fun `should hold cardsPlayed with aliasType`() {
        val card = CardNetworkPacket("Feral", CardType.FERAL_CAT, aliasType = CardType.CAT_CATERMELON)
        val message = PlayerMessage("P1", "CAT_COMBO", listOf(card), null)

        val playedList = message.payload as? List<*> ?: fail("Payload is not a list")
        val played = playedList.firstOrNull() as? CardNetworkPacket ?: fail("first item is not a CardNetworkPacket")
        assertEquals(CardType.FERAL_CAT, played.type)
        assertEquals(CardType.CAT_CATERMELON, played.aliasType)
    }
}
