package pwr.pim.clickergame.game

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.setValue
import kotlin.random.Random
import pwr.pim.clickergame.R

enum class CharacterEvolutionStage(val drawableRes: Int) {
    NORMAL(R.drawable.charactermerged_normal),
    CORRUPT1(R.drawable.charactermerged_normal),
    CORRUPT2(R.drawable.charactermerged_normal),
    CORRUPT3(R.drawable.charactermerged_normal),
    CORRUPT4(R.drawable.charactermerged_normal),
    CORRUPT5(R.drawable.charactermerged_normal),
}

class GameEngine {
    var score by mutableDoubleStateOf(0.0)
        private set

    var coins by mutableIntStateOf(0)
        private set

    var characterEvolutionLevel by mutableIntStateOf(0)
        private set

    // Upgrade levels map: upgradeId -> level
    val upgradeLevels = mutableStateMapOf<String, Int>()

    val availableUpgrades = listOf(
        UpgradeDefinition(
            id = "flat_click",
            name = "Sharper Claws",
            description = "Increases flat score per click",
            type = UpgradeType.FLAT_CLICK,
            baseCost = 10.0,
            costScaling = 1.25,
            baseEffect = 1.0,
        ),
        UpgradeDefinition(
            id = "click_mult",
            name = "Energy Drink",
            description = "Multiplies total click power",
            type = UpgradeType.CLICK_MULTIPLIER,
            baseCost = 50.0,
            costScaling = 1.5,
            baseEffect = 0.2, // +20%
        ),
        UpgradeDefinition(
            id = "passive_income",
            name = "Auto Petting Machine",
            description = "Generates score automatically every second",
            type = UpgradeType.PASSIVE_INCOME,
            baseCost = 100.0,
            costScaling = 1.4,
            baseEffect = 2.0, // +2 score/sec
        ),
        UpgradeDefinition(
            id = "crit_chance",
            name = "Lucky Paws",
            description = "Increases critical click chance",
            type = UpgradeType.CRIT_CHANCE,
            baseCost = 75.0,
            costScaling = 1.6,
            baseEffect = 0.05, // +5% crit chance
        ),
        UpgradeDefinition(
            id = "crit_power",
            name = "Fierce Swipe",
            description = "Increases critical hit damage multiplier",
            type = UpgradeType.CRIT_POWER,
            baseCost = 150.0,
            costScaling = 1.5,
            baseEffect = 0.5, // +0.5x crit damage
        ),
        UpgradeDefinition(
            id = "corruption",
            name = "Finals Week",
            description = "ONE MORE GAME",
            type = UpgradeType.CHARACTER_EVOLUTION,
            baseCost = 2500.0,
            costScaling = 3.0,
            baseEffect = 1.0,
        ),
    )

    // Derived stats
    val clickPower by derivedStateOf {
        val base = 1.0
        val flatUpgrade = availableUpgrades.find { it.id == "flat_click" }
        val level = upgradeLevels["flat_click"] ?: 0
        if (flatUpgrade != null) base + flatUpgrade.calculateEffect(level) else base
    }

    val clickMultiplier by derivedStateOf {
        val multUpgrade = availableUpgrades.find { it.id == "click_mult" }
        val level = upgradeLevels["click_mult"] ?: 0
        multUpgrade?.calculateEffect(level) ?: 1.0
    }

    val critChance by derivedStateOf {
        val critUpgrade = availableUpgrades.find { it.id == "crit_chance" }
        val level = upgradeLevels["crit_chance"] ?: 0
        critUpgrade?.calculateEffect(level)?.coerceAtMost(1.0) ?: 0.0
    }

    val critPower by derivedStateOf {
        val powerUpgrade = availableUpgrades.find { it.id == "crit_power" }
        val level = upgradeLevels["crit_power"] ?: 0
        powerUpgrade?.calculateEffect(level) ?: 1.5
    }

    val passiveIncomePerSecond by derivedStateOf {
        val passiveUpgrade = availableUpgrades.find { it.id == "passive_income" }
        val level = upgradeLevels["passive_income"] ?: 0
        passiveUpgrade?.calculateEffect(level) ?: 0.0
    }

    val currentCharacterSprite by derivedStateOf {
        val stages = CharacterEvolutionStage.entries
        stages[characterEvolutionLevel.coerceIn(0, stages.size - 1)].drawableRes
    }

    // returns true if crit proc
    fun onClick(): Boolean {
        var gain = clickPower * clickMultiplier
        val isCrit = Random.nextDouble() < critChance
        if (isCrit) {
            gain *= critPower
        }

        val oldScoreInt = score.toInt()
        score += gain
        val newScoreInt = score.toInt()

        // 1 coin every 10 score
        if ((newScoreInt / 10) > (oldScoreInt / 10)) {
            coins += (newScoreInt / 10) - (oldScoreInt / 10)
        }

        return isCrit
    }

    fun tick(deltaTimeSeconds: Double) {
        if (passiveIncomePerSecond > 0.0) {
            val oldScoreInt = score.toInt()
            score += passiveIncomePerSecond * deltaTimeSeconds
            val newScoreInt = score.toInt()

            if ((newScoreInt / 10) > (oldScoreInt / 10)) {
                coins += (newScoreInt / 10) - (oldScoreInt / 10)
            }
        }
    }

    fun buyUpgrade(upgradeId: String): Boolean {
        val definition = availableUpgrades.find { it.id == upgradeId } ?: return false
        val currentLevel = upgradeLevels[upgradeId] ?: 0

        // If character evolution and reached max stage, lock it
        if ((definition.type == UpgradeType.CHARACTER_EVOLUTION) && (currentLevel >= CharacterEvolutionStage.entries.size - 1)) {
            return false
        }

        val cost = definition.calculateCost(currentLevel)

        if (score >= cost) {
            score -= cost
            upgradeLevels[upgradeId] = currentLevel + 1
            if (definition.type == UpgradeType.CHARACTER_EVOLUTION) {
                characterEvolutionLevel = (currentLevel + 1).coerceAtMost(CharacterEvolutionStage.entries.size - 1)
            }
            return true
        }
        return false
    }
}
