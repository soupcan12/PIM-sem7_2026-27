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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pwr.pim.clickergame.game.GameEngine

@Composable
fun GalleryScreen(gameEngine: GameEngine) {
    var selectedSlot by remember { mutableStateOf("hairstyle") }
    val slots = listOf("hairstyle", "outerwear", "pants", "shoes", "glasses", "eyes")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Wardrobe", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Coins: ${gameEngine.coins}", style = MaterialTheme.typography.titleMedium)

        // Slot Selector Tabs (Scrollable Horizontal Overflow)
        LazyRow(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(slots) { slot ->
                Button(
                    onClick = { selectedSlot = slot },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedSlot == slot) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(slot.replaceFirstChar { it.uppercase() })
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Items for selected slot
        val itemsForSlot = gameEngine.clothesItems.filter { it.slot == selectedSlot }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(itemsForSlot) { item ->
                val isUnlocked = gameEngine.unlockedClothesIds.contains(item.id)
                val isEquipped = gameEngine.characterSprites[item.slot] == item.drawableRes

                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(item.name, style = MaterialTheme.typography.titleMedium)
                            Text(if (isUnlocked) "Unlocked" else "Cost: ${item.cost} Coins")
                        }
                        Button(
                            onClick = { gameEngine.buyAndEquipClothes(item) },
                            enabled = isUnlocked || gameEngine.coins >= item.cost
                        ) {
                            Text(
                                when {
                                    isEquipped -> "Equipped"
                                    isUnlocked -> "Equip"
                                    else -> "Buy & Equip"
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
