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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Text
import androidx.wear.tooling.preview.devices.WearDevices
import me.dkim19375.game2048.presentation.theme.Game2048Theme

@Composable
@Preview(device = WearDevices.SMALL_ROUND, showSystemUi = true)
fun GameBoard() = Row(
    modifier = Modifier
        .padding(horizontal = 30.dp)
        .aspectRatio(1F),
    horizontalArrangement = Arrangement.spacedBy(6.dp),
) {
    repeat(GRID_SIZE) { x ->
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1F),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            repeat(GRID_SIZE) { y ->
                GameTile(x, y)
            }
        }
    }
}

@Composable
fun GameTile(x: Int, y: Int) {
    val num = GameManager.board[x][y]
    val (backgroundColor, numColor) = Game2048Theme.buttonColors.toMap().getValue(num)
    Box(
        modifier = Modifier
            .aspectRatio(1F)
            .clip(RoundedCornerShape(3.dp))
            .background(backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        if (numColor.isSpecified) {
            Text(
                text = num.toString(),
                style = Game2048Theme.typography.run {
                    when (num.toString().length) {
                        in 1..2 -> numberLarge
                        3 -> numberMedium
                        else -> numberSmall
                    }
                },
                color = numColor,
            )
        }
    }
}