package pwr.pim.clickergame.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.Dp
// WHY ARE THESE SEPARATE IMPORTS???????
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay
import pwr.pim.clickergame.R
import pwr.pim.clickergame.game.GameEngine
import pwr.pim.clickergame.model.BackgroundEntityState
import pwr.pim.clickergame.model.EntityState

@Composable
fun HomeScreen(
    gameEngine: GameEngine,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        // Static background sprite entities (separate from moving foreground entities)
        val backgroundEntities = remember {
            listOf(
                BackgroundEntityState(0.6f, 0.405f, 0.35f, 0.16f, R.drawable.desk1),
                BackgroundEntityState(0.1f, 0.45f, 0.7f, 0.7f, R.drawable.carpet1),
                BackgroundEntityState(0.04f, 0.18f, 0.2f, 0.2f, R.drawable.poster1),
                BackgroundEntityState(0.78f, 0.19f, 0.2f, 0.2f, R.drawable.poster2),
                BackgroundEntityState(0.65f, 0.32f, 0.15f, 0.15f, R.drawable.plant1),
                BackgroundEntityState(0.8f, 0.35f, 0.12f, 0.12f, R.drawable.plant2),
            )
        }

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val width = maxWidth
            val height = maxHeight
            // BACKGROUND (wall)
            Image(
                painter = painterResource(R.drawable.wall1),
                contentDescription = "Background Wall",
                modifier = Modifier
                    .size(width, height / 1.2f)
                    .offset(0.dp, 0.dp),
                contentScale = ContentScale.FillWidth
            )
            // BACKGROUND (floor)
            Image(
                painter = painterResource(R.drawable.floor1),
                contentDescription = "Background Floor",
                modifier = Modifier
                    .size(width, height)
                    .offset(0.dp, height / 7f),
                contentScale = ContentScale.FillWidth
            )
            // BACKGROUND (window)
            Image(
                painter = painterResource(R.drawable.window1),
                contentDescription = "Background Window",
                modifier = Modifier
                    .size(width / 2f, height / 2f)
                    .offset(x = width / 4f, y = 0.dp),
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
                    EntityState(100.dp, floorTop + 20.dp, 1.2.dp, 0.9.dp, R.drawable.body1, width = 240.dp, height = 360.dp),
                    EntityState(250.dp, floorTop + 50.dp, (-1.0).dp, 1.4.dp, R.drawable.cat1, alternateIcon = R.drawable.cat1_blink, width = 85.dp, height = 85.dp),
                )
            }

            fun changeMovementDirection(entity: EntityState) {
                entity.vx = (-entity.vx + Random.nextDouble(-0.3, 0.3).dp).coerceIn(-2.3.dp, 2.3.dp)
                entity.vy = (-entity.vy + Random.nextDouble(-0.3, 0.3).dp).coerceIn(-2.3.dp, 2.3.dp)
                if (entity.vx < 0.7.dp && entity.vx > (-0.7).dp) {entity.vx = (listOf(-1,1).random()*0.7).dp}
                if (entity.vy < 0.7.dp && entity.vy > (-0.7).dp) {entity.vy = (listOf(-1,1).random()*0.7).dp}
            }

            LaunchedEffect(Unit) {
                while (true) {
                    delay(16.milliseconds)
                    entities.forEach { entity ->
                        entity.x += entity.vx
                        entity.y += entity.vy

                        // horizontal bounds
                        val maxX = containerWidth - entity.width
                        if ((entity.x < 0.dp) || (entity.x > maxX)) {
                            changeMovementDirection(entity)
                            entity.x = entity.x.coerceIn(0.dp, maxX)
                        }

                        // vertical bounds
                        val maxY = containerHeight - entity.height
                        // TOP MOVEMENT BOUNDARY CHECKED AGAINST BOTTOM EDGE OF SPRITE (entity.y + entity.height)
                        if ((entity.y + entity.height) < floorTop) {
                            changeMovementDirection(entity)
                            entity.y = floorTop - entity.height
                        } else if (entity.y > maxY) {
                            changeMovementDirection(entity)
                            entity.y = maxY
                        }
                    }
                }
            }

            entities.forEach { entity ->
                key(entity) {
                    if (entity == entities[0]) {
                        Box(
                            modifier = Modifier
                                .offset(entity.x, entity.y)
                                .size(entity.width, entity.height)
                                .clickable { gameEngine.onClick() }
                        ) {
                            Image(
                                painter = painterResource(R.drawable.body1),
                                contentDescription = "base body",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                            Image(
                                painter = painterResource(R.drawable.head1),
                                contentDescription = "base head",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                            gameEngine.characterSprites["buty"]?.let { shoes ->
                                Image(
                                    painter = painterResource(shoes),
                                    contentDescription = "buty",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
                            gameEngine.characterSprites["spodnie"]?.let { pants ->
                                Image(
                                    painter = painterResource(pants),
                                    contentDescription = "pant",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
                            gameEngine.characterSprites["oczy"]?.let { eyes ->
                                var currentEyesIcon by remember(eyes) { mutableStateOf(eyes) }
                                LaunchedEffect(eyes) {
                                    while (true) {
                                        val delayTime = Random.nextLong(2000L, 5000L)
                                        delay(delayTime.milliseconds)
                                        currentEyesIcon = listOf(R.drawable.eyes_blink, R.drawable.eyes_closed).random()
                                        delay(150.milliseconds)
                                        currentEyesIcon = eyes
                                    }
                                }
                                Image(
                                    painter = painterResource(currentEyesIcon),
                                    contentDescription = "źrenice",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
                            gameEngine.characterSprites["ubrania"]?.let { outerwear ->
                                Image(
                                    painter = painterResource(outerwear),
                                    contentDescription = "ubranie",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
                            gameEngine.characterSprites["fryzury"]?.let { hair ->
                                Image(
                                    painter = painterResource(hair),
                                    contentDescription = "włosy",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
                            gameEngine.characterSprites["okulary"]?.let { glasses ->
                                Image(
                                    painter = painterResource(glasses),
                                    contentDescription = "okulary",
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    } else {
                        if (entity.alternateIcon != null) {
                            LaunchedEffect(Unit) {
                                while (true) {
                                    val delayTime = Random.nextLong(entity.intervalMinMs, entity.intervalMaxMs + 1)
                                    delay(delayTime.milliseconds)
                                    entity.currentIcon = entity.alternateIcon
                                    delay(entity.durationMs.milliseconds)
                                    entity.currentIcon = entity.icon
                                }
                            }
                        }
                        Image(
                            painter = painterResource(entity.currentIcon),
                            contentDescription = "Walking Entity",
                            modifier = Modifier
                                .offset(entity.x, entity.y)
                                .size(entity.width, entity.height)
                                .clickable { gameEngine.onClick() },
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }
    }
}
