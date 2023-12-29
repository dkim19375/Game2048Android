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

package me.dkim19375.game2048.presentation.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import java.util.Collections
import java.util.SortedMap

private const val LIGHT_MODE_DARK_NUM_COLOR = 0xFF776E65
private const val LIGHT_MODE_LIGHT_NUM_COLOR = 0xFFF9F6F2

data class Game2048ButtonColors(
    val zero: Game2048ButtonColor = Game2048ButtonColor(
        backgroundColor = Color(0xFFCDC1B4),
        numColor = Color.Unspecified
    ),
    val two: Game2048ButtonColor = Game2048ButtonColor(
        backgroundColor = Color(0xFFEEE4DA),
        numColor = Color(LIGHT_MODE_DARK_NUM_COLOR)
    ),
    val four: Game2048ButtonColor = Game2048ButtonColor(
        backgroundColor = Color(color = 0xFFEDE0C8),
        numColor = Color(LIGHT_MODE_DARK_NUM_COLOR)
    ),
    val eight: Game2048ButtonColor = Game2048ButtonColor(
        backgroundColor = Color(color = 0xFFF2B179),
        numColor = Color(LIGHT_MODE_LIGHT_NUM_COLOR)
    ),
    val sixteen: Game2048ButtonColor = Game2048ButtonColor(
        backgroundColor = Color(color = 0xFFF59563),
        numColor = Color(LIGHT_MODE_LIGHT_NUM_COLOR)
    ),
    val thirtyTwo: Game2048ButtonColor = Game2048ButtonColor(
        backgroundColor = Color(color = 0xFFF67C5F),
        numColor = Color(LIGHT_MODE_LIGHT_NUM_COLOR)
    ),
    val sixtyFour: Game2048ButtonColor = Game2048ButtonColor(
        backgroundColor = Color(color = 0xFFF65E3B),
        numColor = Color(LIGHT_MODE_LIGHT_NUM_COLOR)
    ),
    val oneTwentyEight: Game2048ButtonColor = Game2048ButtonColor(
        backgroundColor = Color(color = 0xFFEDCF72),
        numColor = Color(LIGHT_MODE_LIGHT_NUM_COLOR)
    ),
    val twoFiftySix: Game2048ButtonColor = Game2048ButtonColor(
        backgroundColor = Color(color = 0xFFEDCC61),
        numColor = Color(LIGHT_MODE_LIGHT_NUM_COLOR)
    ),
    val fiveOneTwo: Game2048ButtonColor = Game2048ButtonColor(
        backgroundColor = Color(color = 0xFFEDC850),
        numColor = Color(LIGHT_MODE_LIGHT_NUM_COLOR)
    ),
    val oneZeroTwoFour: Game2048ButtonColor = Game2048ButtonColor(
        backgroundColor = Color(color = 0xFFEDC53F),
        numColor = Color(LIGHT_MODE_LIGHT_NUM_COLOR)
    ),
    val twoZeroFourEight: Game2048ButtonColor = Game2048ButtonColor(
        backgroundColor = Color(color = 0xFFEDC22E),
        numColor = Color(LIGHT_MODE_LIGHT_NUM_COLOR)
    ),
    val fourZeroNineSix: Game2048ButtonColor = Game2048ButtonColor(
        backgroundColor = Color(color = 0xFFEBB914),
        numColor = Color(LIGHT_MODE_LIGHT_NUM_COLOR)
    ),
    val eightOneNineTwo: Game2048ButtonColor = Game2048ButtonColor(
        backgroundColor = Color(color = 0xFFD3A612),
        numColor = Color(LIGHT_MODE_LIGHT_NUM_COLOR)
    ),
) {
    private val map = Collections.unmodifiableSortedMap(
        sortedMapOf(
            0 to zero,
            2 to two,
            4 to four,
            8 to eight,
            16 to sixteen,
            32 to thirtyTwo,
            64 to sixtyFour,
            128 to oneTwentyEight,
            256 to twoFiftySix,
            512 to fiveOneTwo,
            1024 to oneZeroTwoFour,
            2048 to twoZeroFourEight,
            4096 to fourZeroNineSix,
            8192 to eightOneNineTwo,
        )
    )

    fun toMap(): SortedMap<Int, Game2048ButtonColor> = map

    companion object {
        val Light = Game2048ButtonColors()

        val Dark = Game2048ButtonColors(
            zero = Game2048ButtonColor(
                backgroundColor = Color(0xFF9F886F),
                numColor = Color.Unspecified
            )
        )
    }
}

data class Game2048ButtonColor(
    val backgroundColor: Color,
    val numColor: Color,
)

internal val LocalButtonColors = staticCompositionLocalOf { Game2048ButtonColors() }