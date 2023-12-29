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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import me.dkim19375.game2048.R

private val clearSansFont by lazy {
    FontFamily(
        Font(R.font.clearsans_light, FontWeight.Light),
        Font(R.font.clearsans_regular, FontWeight.Normal),
        Font(R.font.clearsans_bold, FontWeight.Bold)
    )
}

private val DefaultTextStyle = TextStyle.Default.copy(
    textAlign = TextAlign.Center,
    fontFamily = clearSansFont,
)

data class Game2048Typography(
    val title: TextStyle = DefaultTextStyle.copy(
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
    ),
    val displayTitle: TextStyle = DefaultTextStyle.copy(
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold,
    ),
    val restartOrContinue: TextStyle = DefaultTextStyle.copy(
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
    ),
    val options: TextStyle = DefaultTextStyle.copy(
        fontSize = 17.sp,
        fontWeight = FontWeight.Bold,
    ),
    val numberLarge: TextStyle = DefaultTextStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 19.sp,
    ),
    val numberMedium: TextStyle = DefaultTextStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    val numberSmall: TextStyle = DefaultTextStyle.copy(
        fontWeight = FontWeight.Bold,
        fontSize = (12.5).sp,
    ),
    val scores: TextStyle = DefaultTextStyle.copy(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
) {
    companion object {
        val Light = Game2048Typography()

        val Dark = Game2048Typography()
    }
}

internal val LocalTypography = staticCompositionLocalOf { Game2048Typography() }