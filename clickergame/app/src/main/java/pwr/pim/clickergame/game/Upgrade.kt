package pwr.pim.clickergame.game

import kotlin.math.pow

enum class UpgradeType {
    FLAT_CLICK,
    PASSIVE_INCOME,
    CLICK_MULTIPLIER,
    CRIT_CHANCE,
    CRIT_POWER,
    SCALING_FUNCTION,
    CHARACTER_EVOLUTION
}

data class UpgradeDefinition(
    val id: String,
    val name: String,
    val description: String,
    val type: UpgradeType,
    val baseCost: Double,
    val costScaling: Double = 1.15,
    val baseEffect: Double,
    val effectScaling: Double = 1.0,
) {
    fun calculateCost(level: Int): Int {
        return (baseCost * costScaling.pow(level.toDouble())).toInt()
    }

    fun calculateEffect(level: Int): Double {
        return when (type) {
            UpgradeType.FLAT_CLICK -> baseEffect * level
            UpgradeType.PASSIVE_INCOME -> baseEffect * level
            UpgradeType.CLICK_MULTIPLIER -> 1.0 + (baseEffect * level)
            UpgradeType.CRIT_CHANCE -> baseEffect * level
            UpgradeType.CRIT_POWER -> 1.5 + (baseEffect * level)
            UpgradeType.SCALING_FUNCTION -> baseEffect * (level.toDouble().pow(effectScaling))
            UpgradeType.CHARACTER_EVOLUTION -> level.toDouble()
        }
    }
}
