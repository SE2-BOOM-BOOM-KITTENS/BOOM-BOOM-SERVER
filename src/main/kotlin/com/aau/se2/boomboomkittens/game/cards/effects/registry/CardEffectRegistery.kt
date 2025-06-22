package com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.registry
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.AlterTheFutureEffect
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.cards.effects.SeeTheFutureEffect
import com.aau.se2.boomboomkittens.game.cards.Card
import com.aau.se2.boomboomkittens.game.cards.CardType
import com.aau.se2.boomboomkittens.game.cards.effects.*
import com.aau.se2.boomboomkittens.com.aau.se2.boomboomkittens.game.logic.CardLogic
import com.aau.se2.boomboomkittens.game.player.Player

object CardEffectRegistry {

    private val effectMap: MutableMap<CardType, (Card, Player, CardLogic) -> Unit> = mutableMapOf()

    init {
        registerDefaults()
    }

    private fun registerDefaults() {
        // Beispiel: Effekte ohne Ziel
        register(CardType.SEE_THE_FUTURE) { card, player, logic ->
            SeeTheFutureEffect().apply(card, player, logic)
        }

        register(CardType.ALTER_THE_FUTURE) { card, player, logic ->
            AlterTheFutureEffect().apply(card, player, logic)
        }

        // Beispiel: Effekte mit Zielspieler
        register(CardType.TARGETED_ATTACK) { card, player, logic ->
            // Hier Zielspieler bestimmen – z.B. nächsten lebenden Spieler
            val target = logic.gameLogic.playerLogic.moveToNextPlayer()
                ?: throw IllegalStateException("No target player available.")

            TargetedAttackEffect(target).apply(card, player, logic)
        }

        // Weitere Karten hier
    }

    fun register(type: CardType, effect: (Card, Player, CardLogic) -> Unit) {
        effectMap[type] = effect
    }

    fun getEffect(type: CardType): CardEffect {
        return object : CardEffect {
            override fun apply(card: Card, player: Player, cardLogic: CardLogic) {
                val effect = effectMap[type]
                    ?: throw IllegalStateException("No effect registered for card type: $type")
                effect(card, player, cardLogic)
            }
        }
    }
}