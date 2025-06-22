import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.TargetedAttackEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

class TargetedAttackEffectTest {

    private fun testCard(name: String): Card = Card(type = CardType.BLANK, name = name)

    private fun createGameLogicWithPlayers(attacker: Player, target: Player): GameLogic {
        val players = mutableListOf(attacker, target)
        return GameLogic(UUID.randomUUID(), players)
    }

    @Test
    fun `apply makes target draw two cards from draw pile`() {
        // Arrange
        val attacker = Player(name = "Attacker")
        val target = Player(name = "Target")
        val gameLogic = createGameLogicWithPlayers(attacker, target)

        gameLogic.drawPile.add(testCard("Card 1"))
        gameLogic.drawPile.add(testCard("Card 2"))
        gameLogic.drawPile.add(testCard("Card 3"))

        val effect = TargetedAttackEffect(target)

        // Act
        effect.apply(attacker, gameLogic)

        // Assert
        assertEquals(2, target.playerHand.getCardAmount())
        assertEquals(1, gameLogic.drawPile.size)
    }

    @Test
    fun `apply draws only available cards if fewer than two`() {
        val attacker = Player(name = "Attacker")
        val target = Player(name = "Target")
        val gameLogic = createGameLogicWithPlayers(attacker, target)

        gameLogic.drawPile.add(testCard("Only Card"))

        val effect = TargetedAttackEffect(target)
        effect.apply(attacker, gameLogic)

        assertEquals(1, target.playerHand.getCardAmount())
        assertTrue(gameLogic.drawPile.isEmpty())
    }

    @Test
    fun `apply does nothing if draw pile is empty`() {
        val attacker = Player(name = "Attacker")
        val target = Player(name = "Target")
        val gameLogic = createGameLogicWithPlayers(attacker, target)

        val effect = TargetedAttackEffect(target)
        effect.apply(attacker, gameLogic)

        assertEquals(0, target.playerHand.getCardAmount())
        assertTrue(gameLogic.drawPile.isEmpty())
    }
}