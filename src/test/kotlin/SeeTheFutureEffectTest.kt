import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.SeeTheFutureEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.player.Player
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.LinkedList
import java.util.UUID

class SeeTheFutureEffectTest {

    private lateinit var gameLogic: GameLogic
    private lateinit var player: Player
    private val lobbyId = UUID.randomUUID()

    @BeforeEach
    fun setup() {
        gameLogic = GameLogic(lobbyId = lobbyId)
        player = Player(
            name = "TestPlayer"
        )

        val testCards = listOf(
            Card(type = CardType.BLANK),         // Card1
            Card(type = CardType.SEETHEFUTURE),            // Card2
            Card(type = CardType.DEFUSE),          // Card3
            Card(type = CardType.ALTERTHEFUTURE),         // Card4
            Card(type = CardType.EXPLODING_KITTEN) // Card5
        )

        val drawPileField = GameLogic::class.java.getDeclaredField("drawPile")
        drawPileField.isAccessible = true
        val drawPile = LinkedList<Card>(testCards)
        drawPileField.set(gameLogic, drawPile)
    }

    @Test
    fun `test SeeTheFutureEffect shows top 3 cards`() {
        val seeTheFutureEffect = SeeTheFutureEffect()
        seeTheFutureEffect.apply(player, gameLogic)

        val topCards = gameLogic.peekTopCards(3)

        assertEquals(3, topCards.size)
        assertEquals("Blank", topCards[0].name)
        assertEquals("See the Future", topCards[1].name)
        assertEquals("Defuse", topCards[2].name)
    }
}