package pwr.pim.clickergame.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import kotlinx.coroutines.delay
import pwr.pim.clickergame.R
import pwr.pim.clickergame.game.GameEngine

data class MinigameDefinition(
    val id: String,
    val title: String,
    val description: String,
    val isPlayable: Boolean,
)

@Composable
fun MinigamesScreen(gameEngine: GameEngine) {
    var activeGameId by remember { mutableStateOf<String?>(null) }

    if (activeGameId == "whackamole") {
        WhackAMoleGameScreen(
            gameEngine = gameEngine,
            onBack = { activeGameId = null }
        )
        return
    }

    val minigames = listOf(
        MinigameDefinition(
            id = "whackamole",
            title = "głaskanie kitku",
            description = "ale kitku ucieka",
            isPlayable = true,
        ),
        MinigameDefinition(
            id = "wip1",
            title = "idk",
            description = "falling coins or smth",
            isPlayable = false,
        ),
        MinigameDefinition(
            id = "wip2",
            title = "symulator zdania semestru",
            description = ":sob:",
            isPlayable = false,
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Minigry", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Grosiki i punkty do zdobycia!", style = MaterialTheme.typography.titleMedium)
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
                            onClick = { activeGameId = game.id },
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

@Composable
fun WhackAMoleGameScreen(
    gameEngine: GameEngine,
    onBack: () -> Unit
) {
    var timeLeft by remember { mutableIntStateOf(30) }
    var score by remember { mutableIntStateOf(0) }
    var activeHole by remember { mutableIntStateOf(Random.nextInt(9)) }
    var hitTrigger by remember { mutableIntStateOf(0) }
    var isGameOver by remember { mutableStateOf(false) }
    var jumpStart by remember { mutableLongStateOf(300L) }
    var jumpEnd by remember { mutableLongStateOf(900L) }

    // Timer effect (30 seconds)
    LaunchedEffect(isGameOver) {
        if (!isGameOver) {
            while (timeLeft > 0) {
                delay(1000L)
                timeLeft--
            }
            isGameOver = true
            // reward: score, coins = 3score
            gameEngine.addMinigamesRewards(score)
        }
    }

    // Mole (Cat) popping effect - resets delay whenever hitTrigger changes
    LaunchedEffect(isGameOver, hitTrigger) {
        if (!isGameOver) {
            while (!isGameOver) {
                delay(Random.nextLong(jumpStart, jumpEnd))
                activeHole = Random.nextInt(9)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!isGameOver) {
            Text(text = "Czas: ${timeLeft}s", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Głaskania: $score", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(24.dp))

            // 3x3 Grid of Holes - Responsive to screen width
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (row in 0 until 3) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (col in 0 until 3) {
                            val index = row * 3 + col
                            val isMolePresent = (index == activeHole)

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .background(
                                        if (isMolePresent) MaterialTheme.colorScheme.primaryContainer
                                        else MaterialTheme.colorScheme.surfaceVariant,
                                        shape = MaterialTheme.shapes.medium
                                    )
                                    .clickable(enabled = isMolePresent) {
                                        if (isMolePresent) {
                                            score++
                                            hitTrigger++ // reroll hole and reset delay on successful hit
                                            activeHole = Random.nextInt(9)
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                if (isMolePresent) {
                                    Image(
                                        painter = painterResource(R.drawable.cat1),
                                        contentDescription = "Cat Mole",
                                        modifier = Modifier.fillMaxSize(0.8f),
                                        contentScale = ContentScale.Fit
                                    )
                                } else {
                                    Text(text = "🕳️", style = MaterialTheme.typography.headlineMedium)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onBack) {
                Text("Wróć")
            }
        } else {
            // Game Over Summary Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Koniec!", style = MaterialTheme.typography.headlineLarge)
                    Text("Pogłaskanych $score kitku!", style = MaterialTheme.typography.titleLarge)
                    /*Text(
                        "Twoje nagrody:\n+${score} Punktów\n+${score * 3} Grosików",
                        style = MaterialTheme.typography.bodyLarge
                    )*/

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(onClick = {
                            timeLeft = 30
                            score = 0
                            isGameOver = false
                        }) {
                            Text("Spróbuj jeszcze raz")
                        }
                        Button(onClick = onBack) {
                            Text("Wybierz inną gierkę")
                        }
                    }
                }
            }
        }
    }
}
