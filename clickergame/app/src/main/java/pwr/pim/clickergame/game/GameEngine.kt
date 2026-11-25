package pwr.pim.clickergame.game

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.setValue
import kotlin.random.Random
import pwr.pim.clickergame.R

enum class CharacterEvolutionStage(val drawableRes: Int) {
    // deprecated: replaced whole sprite sheet with individual sprites
    // update: still waiting for diff sprites :)
    NORMAL(R.drawable.charactermerged_normal),
    CORRUPT1(R.drawable.charactermerged_normal),
    CORRUPT2(R.drawable.charactermerged_normal),
    CORRUPT3(R.drawable.charactermerged_normal),
    CORRUPT4(R.drawable.charactermerged_normal),
    CORRUPT5(R.drawable.charactermerged_normal),
}

data class DressUp(val id: String, val name: String, val slot: String, val drawableRes: Int, val cost: Int)

class GameEngine {
    var score by mutableDoubleStateOf(0.0)
        private set
    var coins by mutableIntStateOf(0)
        private set
    var characterEvolutionLevel by mutableIntStateOf(0)
        private set
    // map: upgradeId -> level
    val upgradeLevels = mutableStateMapOf<String, Int>()

    // UBRANIA
    val clothesItems = listOf(
        DressUp("hair1", "Zwykła fryzura", "fryzury", R.drawable.hair1, 0),
        DressUp("hair2", "Różowe rozpuszczone", "fryzury", R.drawable.hair2, 0),
        DressUp("hoodie1", "Fioletowa bluza", "ubrania", R.drawable.hoodie1, 0),
        DressUp("hoodie2", "Zielona bluza", "ubrania", R.drawable.hoodie2, 0),
        DressUp("pants1", "Zwykłe spodnie", "spodnie", R.drawable.pants1, 0),
        DressUp("pants2", "Jeansy", "spodnie", R.drawable.pants2, 0),
        DressUp("shoes1", "Skarpetki", "buty", R.drawable.shoes1, 0),
        DressUp("shoes2", "Papucie", "buty", R.drawable.shoes2, 0),
        DressUp("glasses0", "Bez okularów", "okulary", R.drawable.nothing, 0),
        DressUp("glasses1", "Okularki", "okulary", R.drawable.glasses1, 0),
        DressUp("skirt1", "Spódniczka", "spodnie", R.drawable.skirt1, 0),
        DressUp("eyes1", "Twoje oczy", "oczy", R.drawable.eyes1, 0),
    )
    val unlockedClothesIds = mutableStateListOf("hair1", "hoodie1", "pants1", "shoes1", "glasses1", "eyes1")
    val characterSprites = mutableStateMapOf<String, Int?>().apply {
        put("ubrania", R.drawable.hoodie1)
        put("spodnie", R.drawable.pants1)
        put("buty", R.drawable.shoes1)
        put("fryzury", R.drawable.hair1)
        put("okulary", R.drawable.glasses1)
        put("oczy", R.drawable.eyes1)}

    fun buyAndEquipClothes(item: DressUp): Boolean {
        if (unlockedClothesIds.contains(item.id)) {
            characterSprites[item.slot] = item.drawableRes
            return true
        }
        // buy with coins
        if (coins >= item.cost) {
            coins -= item.cost
            unlockedClothesIds.add(item.id)
            characterSprites[item.slot] = item.drawableRes
            return true
        }
        return false
    }
    val availableUpgrades = listOf(
        UpgradeDefinition(
            id = "flat_click",
            name = "Poranna herbatka",
            description = "Lepsze kliknięcia",
            type = UpgradeType.FLAT_CLICK,
            baseCost = 10.0,
            costScaling = 1.25,
            baseEffect = 1.0,
        ),
        UpgradeDefinition(
            id = "click_mult",
            name = "Kalkulator prosty",
            description = "Mnoży moc kliknięcia",
            type = UpgradeType.CLICK_MULTIPLIER,
            baseCost = 50.0,
            costScaling = 1.5,
            baseEffect = 0.2, // +20%
        ),
        UpgradeDefinition(
            id = "passive_income",
            name = "Kitku",
            description = "Pasywne punkty na sekundę",
            type = UpgradeType.PASSIVE_INCOME,
            baseCost = 100.0,
            costScaling = 1.4,
            baseEffect = 2.0, // +2 score/sec
        ),
        UpgradeDefinition(
            id = "crit_chance",
            name = "Kalkulator naukowy",
            description = "Kliknięcia mają szansę na dużą nagrodę",
            type = UpgradeType.CRIT_CHANCE,
            baseCost = 75.0,
            costScaling = 1.6,
            baseEffect = 0.05, // +5% crit chance
        ),
        UpgradeDefinition(
            id = "crit_power",
            name = "Przypinka na plecak",
            description = "Większa nagroda za przypadkowe kliknięcia",
            type = UpgradeType.CRIT_POWER,
            baseCost = 150.0,
            costScaling = 1.5,
            baseEffect = 0.5, // +0.5x crit damage
        ),
        UpgradeDefinition(
            id = "corruption",
            name = "Sesja",
            description = "ja nie chce do kostnicy",
            type = UpgradeType.CHARACTER_EVOLUTION,
            baseCost = 2500.0,
            costScaling = 3.0,
            baseEffect = 1.0,
        ),
    )

    // statystyki ulepszeń; ulepszenia
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
        if (isCrit) {gain *= critPower}

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

        // if character evolution and reached max stage: lock it
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

    fun addMinigamesRewards(whackedCount: Int) {
        val oldScoreInt = score.toInt()
        score += whackedCount.toDouble()
        val newScoreInt = score.toInt()
        if ((newScoreInt / 10) > (oldScoreInt / 10)) {coins += (newScoreInt / 10) - (oldScoreInt / 10)}
        coins += whackedCount * 3
    }
}
