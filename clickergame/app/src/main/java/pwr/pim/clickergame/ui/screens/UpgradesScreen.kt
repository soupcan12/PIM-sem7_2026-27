package pwr.pim.clickergame.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pwr.pim.clickergame.game.CharacterEvolutionStage
import pwr.pim.clickergame.game.GameEngine
import pwr.pim.clickergame.game.UpgradeType

@Composable
fun UpgradesScreen(
    gameEngine: GameEngine,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("Upgrades", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Score: ${gameEngine.score.toInt()}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 4.dp),
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(gameEngine.availableUpgrades) { upgrade ->
                val level = gameEngine.upgradeLevels[upgrade.id] ?: 0
                val isMaxed = upgrade.type == UpgradeType.CHARACTER_EVOLUTION && level >= CharacterEvolutionStage.entries.size - 1
                val cost = if (isMaxed) 0 else upgrade.calculateCost(level)
                val canAfford = !isMaxed && gameEngine.score >= cost

                Card(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = if (isMaxed) upgrade.name else "${upgrade.name} (Lvl $level)",
                                style = MaterialTheme.typography.titleMedium,
                            )
                            Text(
                                text = upgrade.description,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(vertical = 4.dp),
                            )
                            Text(
                                text = if (isMaxed) "MAXED OUT (Locked)" else "Cost: $cost Score",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (isMaxed) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                            )
                        }

                        Button(
                            onClick = { gameEngine.buyUpgrade(upgrade.id) },
                            enabled = canAfford,
                        ) {
                            Text(text = if (isMaxed) "MAX" else "Buy")
                        }
                    }
                }
            }
        }
    }
}
