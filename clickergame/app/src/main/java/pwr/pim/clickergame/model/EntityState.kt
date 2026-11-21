package pwr.pim.clickergame.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class EntityState(
    initialX: Dp,
    initialY: Dp,
    initialVx: Dp,
    initialVy: Dp,
    val icon: Int,
    val alternateIcon: Int? = null,
    val intervalMinMs: Long = 3000L,
    val intervalMaxMs: Long = 5000L,
    val durationMs: Long = 500L,
    val width: Dp = 100.dp,
    val height: Dp = 100.dp,
    val layers: List<Int> = emptyList(),
) {
    var x by mutableStateOf(initialX)
    var y by mutableStateOf(initialY)
    var vx by mutableStateOf(initialVx)
    var vy by mutableStateOf(initialVy)
    var currentIcon by mutableIntStateOf(icon)
}

class BackgroundEntityState(
    val xFraction: Float,
    val yFraction: Float,
    val widthFraction: Float,
    val heightFraction: Float,
    val sprite: Int,
)
