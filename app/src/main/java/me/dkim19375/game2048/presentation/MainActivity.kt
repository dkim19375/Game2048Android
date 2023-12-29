/*
 * MIT License
 *
 * Copyright (c) 2023 dkim19375
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.dkim19375.game2048.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.draggable2D
import androidx.compose.foundation.gestures.rememberDraggable2DState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.rememberActiveFocusRequester
import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.ButtonDefaults
import androidx.wear.compose.material3.Checkbox
import androidx.wear.compose.material3.CheckboxDefaults
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.Text
import androidx.wear.compose.material3.ToggleButton
import androidx.wear.compose.material3.ToggleButtonDefaults
import androidx.wear.tooling.preview.devices.WearDevices
import kotlinx.coroutines.launch
import me.dkim19375.game2048.R
import me.dkim19375.game2048.presentation.theme.Game2048Theme
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setContent {
            var lightMode by remember { mutableStateOf(true) }

            Game2048App(lightMode) { lightMode = it }
        }
    }
}

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun Game2048App(
    lightMode: Boolean = true,
    preview: Boolean = false,
    setLightMode: (Boolean) -> Unit = {},
) = Game2048Theme(lightMode) {
    var alreadyRan by remember { mutableStateOf(false) }
    if (!preview && !alreadyRan) {
        if (GameManager.bestScore == 0) {
            GameManager.fetchBestScore()
        }
        if (GameManager.board.all { it.all(0::equals) }) {
            GameManager.fetchBoard()
        }
        alreadyRan = true
    }
    if (GameManager.board.all { it.all(0::equals) }) {
        GameManager.generateStart()
    }

    val listState = rememberLazyListState()
    val focusRequester = rememberActiveFocusRequester()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(modifier = Modifier
        .onRotaryScrollEvent {
            coroutineScope.launch {
                //listState.scrollBy(it.verticalScrollPixels)

                listState.animateScrollBy(it.verticalScrollPixels * 5)
            }
            true
        }
        .focusRequester(focusRequester)
        .focusable(), state = listState) {
        item(0) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1F)
                    .setupDragging()
            ) {
                Column(
                    modifier = Modifier
                        .fillParentMaxSize()
                        .background(Game2048Theme.colorScheme.background),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.score, GameManager.score),
                        style = Game2048Theme.typography.scores,
                        color = Game2048Theme.colorScheme.onBackground,
                    )
                    GameBoard()
                    Text(
                        text = stringResource(R.string.best_score, GameManager.bestScore),
                        style = Game2048Theme.typography.scores,
                        color = Game2048Theme.colorScheme.onBackground,
                    )
                }
                when (GameManager.gameState) {
                    GameState.GAME_OVER -> GameOverOrWinScreen(isGameOver = true)

                    GameState.GAME_WON -> GameOverOrWinScreen(isGameOver = false)

                    GameState.GAME_RUNNING -> {}
                }
            }
        }
        item(1) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1F)
                    .background(Game2048Theme.colorScheme.background),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = Game2048Theme.typography.title,
                    modifier = Modifier.fillMaxWidth(),
                    color = Game2048Theme.colorScheme.onBackground,
                )

                ToggleButton(
                    checked = !lightMode,
                    onCheckedChange = { setLightMode(!it) },
                    selectionControl = {
                        Checkbox(
                            checked = !lightMode,
                            colors = CheckboxDefaults.colors(
                                uncheckedBoxColor = Game2048Theme.colorScheme.checkbox,
                                uncheckedCheckmarkColor = Game2048Theme.colorScheme.onCheckbox,
                                checkedBoxColor = Game2048Theme.colorScheme.checkbox,
                                checkedCheckmarkColor = Game2048Theme.colorScheme.onCheckbox,
                            )
                        )
                        if (Game2048Theme.colorScheme.checkboxExtraOutline.isSpecified) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Box(
                                    modifier = Modifier
                                        .aspectRatio(1F)
                                        .fillMaxSize()
                                        .padding(3.dp)
                                        .border(
                                            width = 2.dp,
                                            shape = RoundedCornerShape(2.dp),
                                            color = Game2048Theme.colorScheme.checkboxExtraOutline
                                        )
                                )
                            }
                        }
                    },
                    colors = ToggleButtonDefaults.toggleButtonColors(
                        uncheckedContainerColor = Game2048Theme.colorScheme.secondaryContainer,
                        uncheckedContentColor = Game2048Theme.colorScheme.onSecondaryContainer,
                        checkedContainerColor = Game2048Theme.colorScheme.secondaryContainer,
                        checkedContentColor = Game2048Theme.colorScheme.onSecondaryContainer,
                    )
                ) {
                    Text(
                        text = stringResource(R.string.dark_mode),
                        style = Game2048Theme.typography.options,
                    )
                }

                var shouldRestart by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(Game2048Theme.colorScheme.tertiaryContainer)
                        .clickable {
                            GameManager.reset()
                            GameManager.generateStart()
                            shouldRestart = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.restart_icon),
                        contentDescription = stringResource(R.string.icon_restart),
                        tint = Game2048Theme.colorScheme.onTertiaryContainerDim,
                        modifier = Modifier.fillMaxSize(0.9f)
                    )
                }
                if (shouldRestart) {
                    Toast.makeText(
                        LocalContext.current,
                        stringResource(R.string.toast_restarted), Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

@Composable
fun LazyItemScope.GameOverOrWinScreen(isGameOver: Boolean) = Column(
    modifier = Modifier
        .fillParentMaxSize()
        .background(Game2048Theme.colorScheme.run { if (isGameOver) gameOverOverlay else gameWonOverlay }),
    verticalArrangement = Arrangement.spacedBy(
        space = 10.dp,
        alignment = Alignment.CenterVertically
    ),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Text(
        text = stringResource(if (isGameOver) R.string.game_over else R.string.you_win),
        style = Game2048Theme.typography.displayTitle,
        color = Game2048Theme.colorScheme.run {
            (if (isGameOver) onGameOverOverlay else onGameWonOverlay).copy(alpha = 0.9f)
        },
    )
    Button(
        onClick = {
            if (isGameOver) {
                GameManager.reset()
                GameManager.generateStart()
            }
            GameManager.gameState = GameState.GAME_RUNNING
        },
        colors = ButtonDefaults.filledButtonColors(
            containerColor = Game2048Theme.colorScheme.secondaryContainer.copy(alpha = 0.85f),
            contentColor = Game2048Theme.colorScheme.onSecondaryContainer,
        ),
        shape = RoundedCornerShape(3.dp),
        modifier = Modifier.height(40.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(if (isGameOver) R.string.button_restart else R.string.button_continue),
                textAlign = TextAlign.Center,
                style = Game2048Theme.typography.restartOrContinue,
            )
        }
    }
}

@Composable
@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
fun Game2048Preview() = Game2048App(preview = true)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Modifier.setupDragging(): Modifier {
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var shouldHandleSwipe by remember { mutableStateOf(false) }
    var handledSwipe by remember { mutableStateOf(false) }
    val state = rememberDraggable2DState(onDelta = { delta ->
        if (handledSwipe) {
            return@rememberDraggable2DState
        }
        offsetX += delta.x
        offsetY += delta.y
        if (abs(offsetX) >= 40 || abs(offsetY) >= 40) {
            shouldHandleSwipe = true
        }
    })
    if (shouldHandleSwipe) {
        if (abs(offsetX) >= 15 || abs(offsetY) >= 15) {
            GameManager.handleSwipe(
                if (abs(offsetX) > abs(offsetY)) {
                    if (offsetX > 0) Direction.Right else Direction.Left
                } else {
                    if (offsetY > 0) Direction.Down else Direction.Up
                }
            )
        }
        shouldHandleSwipe = false
        handledSwipe = !handledSwipe
    }
    return draggable2D(state = state, reverseDirection = false, onDragStarted = {
        offsetX = 0f
        offsetY = 0f
    }, onDragStopped = {
        if (!handledSwipe) {
            shouldHandleSwipe = true
            handledSwipe = true
        } else {
            handledSwipe = false
        }
    })
}