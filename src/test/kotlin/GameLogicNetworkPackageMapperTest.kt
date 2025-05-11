import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.filipp.server.networkPacket.NetworkPacketMapper
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
        mapper = NetworkPacketMapper()

        player1 = Player(UUID.randomUUID(), "player1")
        player2 = Player(UUID.randomUUID(), "player2")

        gameLogic = GameLogic(UUID.randomUUID(), mutableListOf(player1,player2))
        cardLogic = gameLogic.getCardLogic()

        cardLogic.drawPile.insertAt(0, Card(CardType.BLANK))

        cardLogic.addCardToPlayer(player1.playerId, Card(CardType.BLANK))
        cardLogic.addCardToPlayer(player2.playerId, Card(CardType.BLANK))
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