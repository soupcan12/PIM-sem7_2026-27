package pwr.pim.clickergame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import pwr.pim.clickergame.game.GameEngine
import pwr.pim.clickergame.ui.components.TopStatsHeader
import pwr.pim.clickergame.ui.screens.GalleryScreen
import pwr.pim.clickergame.ui.screens.HomeScreen
import pwr.pim.clickergame.ui.screens.MinigamesScreen
import pwr.pim.clickergame.ui.screens.UpgradesScreen
import pwr.pim.clickergame.ui.theme.ClickergameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClickergameTheme { ClickergameApp() }
        }
    }
}

@PreviewScreenSizes
@Composable
fun ClickergameApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    val gameEngine = remember { GameEngine() }

    // Passive income ticker loop
    LaunchedEffect(Unit) {
        while (true) {
            delay(100.milliseconds)
            gameEngine.tick(0.1)
        }
    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = { Icon(painterResource(it.icon), contentDescription = it.label) },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it },
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                when (currentDestination) {
                    AppDestinations.HOME -> {
                        HomeScreen(gameEngine = gameEngine)
                    }
                    AppDestinations.UPGRADE -> {
                        UpgradesScreen(
                            gameEngine = gameEngine,
                        )
                    }
                    AppDestinations.MINIGAMES -> {
                        MinigamesScreen(gameEngine = gameEngine)
                    }
                    AppDestinations.GALLERY -> {
                        GalleryScreen(gameEngine = gameEngine)
                    }
                }

                // Floating Scoreboard Header (only on HOME)
                if (currentDestination == AppDestinations.HOME) {
                    TopStatsHeader(
                        score = gameEngine.score.toInt(),
                        coins = gameEngine.coins,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    HOME("Home", R.drawable.ic_home),
    UPGRADE("Upgrades", R.drawable.ic_shop),
    MINIGAMES("Minigames", R.drawable.ic_game),
    GALLERY(label = "Wardrobe", icon = R.drawable.ic_camera),
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClickergameTheme {
        TopStatsHeader(score = 1, coins = 2)
    }
}
