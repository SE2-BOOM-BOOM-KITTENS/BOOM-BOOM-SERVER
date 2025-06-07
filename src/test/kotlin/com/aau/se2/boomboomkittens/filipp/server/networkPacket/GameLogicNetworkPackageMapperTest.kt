package com.aau.se2.boomboomkittens.filipp.server.networkPacket

import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import org.junit.jupiter.api.Assertions.assertEquals
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertNull
import java.util.UUID

class GameLogicNetworkPacketMapperTest {

    private lateinit var gameLogic: GameLogic
    private lateinit var cardLogic: CardLogic
    private lateinit var mapper: NetworkPacketMapper
    private lateinit var player1: Player
    private lateinit var player2: Player

    @BeforeEach
    fun setUp() {
        gameLogic = GameLogic(UUID.randomUUID())
        cardLogic = gameLogic.cardLogic

        val player1Id = UUID.randomUUID()
        val player2Id = UUID.randomUUID()

        gameLogic.addPlayer(player1Id, "player1")
        gameLogic.addPlayer(player2Id, "player2")

        player1 = gameLogic.getPlayerById(player1Id)!!
        player2 = gameLogic.getPlayerById(player2Id)!!

        cardLogic.addCardToPlayer(player1.playerId, Card(CardType.BLANK))
        cardLogic.addCardToPlayer(player2.playerId, Card(CardType.BLANK))

        mapper = NetworkPacketMapper()
    }

    @Test
    fun gameStateToNetworkPacketTest(){
        val dto = mapper.gameStateToNetworkPacket(gameLogic, cardLogic)

        assertEquals(gameLogic.lobbyId, dto.lobbyId)
        assertEquals(2, dto.playerCount)

        assertNotNull(dto.currentPlayer)
        assertNotNull(dto.nextPlayer)
        assertNotNull(dto.drawPile)
        assertNotNull(dto.discardPile)

        assertEquals(2, dto.players.size)
        assertEquals("player1", dto.players[0].name)
        assertEquals("player2", dto.players[1].name)
    }

    @Test
    fun gameStateToNetworkPacketWinnerNullTest(){
        val dto = mapper.gameStateToNetworkPacket(gameLogic, cardLogic)
        assertNull(dto.winner)
    }

//    @Test
//    fun gameStateToDTOWinnerTest(){
//        gameLogic.removePlayer(player2.playerId)
//        val dto = mapper.gameStateToDTO(gameLogic, cardLogic)
//        assertNotNull(dto.winner)
//        assertEquals(player1.playerId, dto.winner.id)
//    }
}