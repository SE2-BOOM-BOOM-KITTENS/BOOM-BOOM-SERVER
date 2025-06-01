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
            cardsPlayed = emptyList(),
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

        val played = message.cardsPlayed?.first()
        assertEquals(CardType.FERAL_CAT, played?.type)
        assertEquals(CardType.CAT_CATERMELON, played?.aliasType)
    }
}
