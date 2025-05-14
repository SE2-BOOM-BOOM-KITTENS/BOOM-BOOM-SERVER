import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.PlayerLogic
import com.aau.se2.boomboomkittens.game.cards.effects.NopeEffect
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class NopeTest {
    private lateinit var player0: Player
    private lateinit var player1: Player
    private lateinit var gameLogic: GameLogic
    private lateinit var playerLogic: PlayerLogic

    @BeforeEach
    fun setUp() {
        player0 = Player (playerId = UUID.randomUUID(), name = "Player0", defuseCount = 0, isAlive = true)
        player1 = Player (playerId = UUID.randomUUID(), name = "Player1", defuseCount = 0, isAlive = true)
        gameLogic = GameLogic(UUID.randomUUID(), mutableListOf(player0, player1))
        playerLogic = PlayerLogic()
    }

    @Test
    fun nopeTest(){
        val effect = NopeEffect()

        effect.apply(player0, gameLogic)

        assertEquals(gameLogic.playerLogic.getCurrentPlayer(), player0)
    }
}