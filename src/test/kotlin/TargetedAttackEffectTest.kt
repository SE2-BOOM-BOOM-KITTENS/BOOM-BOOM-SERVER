import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardPile
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.GameLogic
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.TargetedAttackEffect
import com.aau.se2.boomboomkittens.game.player.Player
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TargetedAttackEffectTest {

    private fun testCard(name: String): Card = Card(type = CardType.BLANK, name = name)

    @Test
    fun `apply makes target draw two cards from deck`() {
        // Arrange
        val pile = CardPile().apply {
            add(testCard("Card 1"))
            add(testCard("Card 2"))
            add(testCard("Card 3"))
        }
        val gameLogic = GameLogic(pile)

        val attacker = Player(name = "Attacker")
        val target = Player(name = "Target")

        val effect = TargetedAttackEffect(target)

        // Act
        effect.apply(attacker, gameLogic)

        // Assert
        val targetHand = target.playerHand.cards
        assertEquals(2, targetHand.size)
        assertEquals(1, pile.size)
    }

    @Test
    fun `apply does not fail when fewer than two cards available`() {
        val pile = CardPile().apply {
            add(testCard("Only"))
        }
        val gameLogic = GameLogic(pile)

        val attacker = Player(name = "Attacker")
        val target = Player(name = "Target")

        val effect = TargetedAttackEffect(target)
        effect.apply(attacker, gameLogic)

        assertEquals(1, target.playerHand.getCardAmount())
        assertTrue(pile.isEmpty())
    }

    @Test
    fun `apply does nothing if pile is empty`() {
        val pile = CardPile() // empty
        val gameLogic = GameLogic(pile)

        val attacker = Player(name = "Attacker")
        val target = Player(name = "Target")

        val effect = TargetedAttackEffect(target)
        effect.apply(attacker, gameLogic)

        assertEquals(0, target.playerHand.getCardAmount())
        assertTrue(pile.isEmpty())
    }
}