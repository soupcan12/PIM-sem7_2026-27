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
import pwr.pim.clickergame.game.GameEngine

data class MinigameDefinition(
    val id: String,
    val title: String,
    val description: String,
    val isPlayable: Boolean,
    val onPlay: (GameEngine) -> Unit
)

@Composable
fun MinigamesScreen(gameEngine: GameEngine) {
    val minigames = listOf(
        MinigameDefinition(
            id = "whackamole",
            title = "bober",
            description = "bober",
            isPlayable = true,
            onPlay = { engine ->
                // Placeholder reward action
                engine.onClick()
            }
        ),
        MinigameDefinition(
            id = "wip1",
            title = "idk",
            description = "falling coins or smth",
            isPlayable = false,
            onPlay = {}
        ),
        MinigameDefinition(
            id = "wip2",
            title = "symulator zdania semestru",
            description = ":sob:",
            isPlayable = false,
            onPlay = {}
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Minigames", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Placeholder text! :DDD", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(minigames) { game ->
                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = game.title, style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = game.description,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }

                        Button(
                            onClick = { game.onPlay(gameEngine) },
                            enabled = game.isPlayable
                        ) {
                            Text(text = if (game.isPlayable) "Play" else "Locked")
                        }
                    }
                }
            }
        }
    }
}
