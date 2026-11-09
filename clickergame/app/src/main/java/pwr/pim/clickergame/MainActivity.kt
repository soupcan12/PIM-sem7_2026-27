package pwr.pim.clickergame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
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

class EntityState(
    initialX: Dp,
    initialY: Dp,
    initialVx: Dp,
    initialVy: Dp,
    val icon: Int,
    val width: Dp = 100.dp,
    val height: Dp = 100.dp,
) {
    var x by mutableStateOf(initialX)
    var y by mutableStateOf(initialY)
    var vx by mutableStateOf(initialVx)
    var vy by mutableStateOf(initialVy)
}

class BackgroundEntityState(
    val xFraction: Float,
    val yFraction: Float,
    val widthFraction: Float,
    val heightFraction: Float,
    val sprite: Int,
)

@PreviewScreenSizes
@Composable
fun ClickergameApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }
    var score by rememberSaveable { mutableIntStateOf(0) }
    var coins by rememberSaveable { mutableIntStateOf(0) }

    // Static background sprite entities (separate from moving foreground entities)
    val backgroundEntities = remember {
        listOf(
            BackgroundEntityState(0.6f, 0.4f, 0.35f, 0.16f, R.drawable.desk1),
            BackgroundEntityState(0.1f, 0.45f, 0.7f, 0.7f, R.drawable.carpet1),
            BackgroundEntityState(0.04f, 0.18f, 0.2f, 0.2f, R.drawable.poster1),
            BackgroundEntityState(0.78f, 0.25f, 0.2f, 0.2f, R.drawable.poster2),
            BackgroundEntityState(0.65f, 0.32f, 0.15f, 0.15f, R.drawable.plant1),
            BackgroundEntityState(0.8f, 0.35f, 0.12f, 0.12f, R.drawable.plant2),
        )
    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = { Icon(painterResource(it.icon), contentDescription = it.label,) },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it },
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
            ) {
                // BACKGROUND LAYER
                BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                    val width = maxWidth
                    val height = maxHeight
                    // BACKGROUND (wall)
                    Image(
                        painter = painterResource(R.drawable.wall1),
                        contentDescription = "Background Wall",
                        modifier = Modifier.size(width, height / 1.2f).offset(0.dp, 0.dp),
                        contentScale = ContentScale.FillWidth
                    )
                    // BACKGROUND (floor)
                    Image(
                        painter = painterResource(R.drawable.floor1),
                        contentDescription = "Background Floor",
                        modifier = Modifier.size(width, height).offset(0.dp, height / 7f),
                        contentScale = ContentScale.FillWidth
                    )
                    // BACKGROUND (window)
                    Image(
                        painter = painterResource(R.drawable.window1),
                        contentDescription = "Background Window",
                        modifier = Modifier.size(width / 2f, height / 2f).offset(width / 4f, height / 16f),
                        contentScale = ContentScale.FillWidth
                    )
                    // Static background sprite entities
                    backgroundEntities.forEach { bgEntity ->
                        Image(
                            painter = painterResource(bgEntity.sprite),
                            contentDescription = "Background Sprite",
                            modifier = Modifier
                                .size(width * bgEntity.widthFraction, height * bgEntity.heightFraction)
                                .offset(width * bgEntity.xFraction, height * bgEntity.yFraction),
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }

                when (currentDestination) {
                    AppDestinations.HOME -> {
                        // Entity walking area
                        BoxWithConstraints(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val containerWidth = maxWidth
                            val containerHeight = maxHeight
                            val floorTop = containerHeight / 1.8f // top edge of floor (circa about)

                            val entities = remember {
                                mutableStateListOf(
                                    // Rectangle bounding box (h/w manually declared)
                                    EntityState(100.dp, floorTop + 20.dp, 1.2.dp, 0.9.dp, R.drawable.charactermerged_normal, width = 240.dp, height = 360.dp),
                                    EntityState(250.dp, floorTop + 50.dp, (-1.0).dp, 1.4.dp, R.drawable.cat1, width = 85.dp, height = 85.dp),
                                )
                            }

                            LaunchedEffect(Unit) {
                                while (true) {
                                    delay(16.milliseconds)
                                    entities.forEach { entity ->
                                        entity.x += entity.vx
                                        entity.y += entity.vy

                                        // Horizontal bounds
                                        val maxX = containerWidth - entity.width
                                        if ((entity.x < 0.dp) || (entity.x > maxX)) {
                                            entity.vx = -entity.vx
                                            entity.x = entity.x.coerceIn(0.dp, maxX)
                                        }

                                        // Vertical bounds
                                        val maxY = containerHeight - entity.height
                                        // TOP MOVEMENT BOUNDARY CHECKED AGAINST BOTTOM EDGE OF SPRITE (entity.y + entity.height)
                                        if ((entity.y + entity.height) < floorTop) {
                                            entity.vy = -entity.vy
                                            entity.y = floorTop - entity.height
                                        } else if (entity.y > maxY) {
                                            entity.vy = -entity.vy
                                            entity.y = maxY
                                        }
                                    }
                                }
                            }

                            entities.forEach { entity ->
                                Image(
                                    painter = painterResource(entity.icon),
                                    contentDescription = "Walking Entity",
                                    modifier = Modifier
                                        .offset(entity.x, entity.y)
                                        .size(entity.width, entity.height)
                                        .clickable {
                                            // placeholder game logic
                                            score += 1
                                            if ((score % 10) == 0) {
                                                coins += 1
                                            }
                                        },
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }
                    else -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Screen: ${currentDestination.label}")
                        }
                    }
                }

                // Floating Scoreboard Header
                TopStatsHeader(
                    score = score,
                    coins = coins,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }
}

@Composable
fun TopStatsHeader(
    score: Int,
    coins: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Clicker Game",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Score", style = MaterialTheme.typography.bodyMedium)
                    Text(text = score.toString(), style = MaterialTheme.typography.titleMedium)
                }
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Coins", style = MaterialTheme.typography.bodyMedium)
                    Text(text = coins.toString(), style = MaterialTheme.typography.titleMedium)
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
    UPGRADE("Upgrades", R.drawable.ic_home),
    MINIGAMES("Minigames", R.drawable.ic_home),
    GALLERY(label = "Gallery", icon = R.drawable.ic_home),
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClickergameTheme {
        TopStatsHeader(score = 1, coins = 2)
    }
}
