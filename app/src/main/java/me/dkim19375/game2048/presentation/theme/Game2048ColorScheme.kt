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

data class Game2048ColorScheme(
    val background: Color = Color(0xFFBBADA0),
    val onBackground: Color = Color(0xFF776E65),
    val secondaryContainer: Color = Color(0xFF786656),

    val onSecondaryContainer: Color = Color(0xFFDDD6CF),
    val checkbox: Color = Color(0xFFBBADA0),
    val checkboxExtraOutline: Color = Color.Unspecified,
    val onCheckbox: Color = Color(0xFF776E65),
    val tertiaryContainer: Color = Color(0xFF8F7A66),
    val onTertiaryContainerDim: Color = Color(0xFFBBADA0),
    val onTertiaryContainer: Color = Color(0xFFEBE6E2),
    val gameOverOverlay: Color = Color(0x80EEE4DA),
    val onGameOverOverlay: Color = Color(0xFF776E65),
    val gameWonOverlay: Color = Color(0x80EDC22E),
    val onGameWonOverlay: Color = Color(0xFFEBE6E2),
) {
    companion object {
        val Light = Game2048ColorScheme()
        val Dark = Game2048ColorScheme(
            background = Color(0xFF5A4D40),
            onBackground = Color(0xFFBBADA0),
            secondaryContainer = Color(0xFFAA9888),
            onSecondaryContainer = Color(0xFF453B31),
            checkbox = Color(0xFF836F5D),
            checkboxExtraOutline = Color(0xFF55493D),
            onCheckbox = Color(0xFFCFC6BC),
            tertiaryContainer = Color(0xFF97816D),
            onTertiaryContainerDim = Color(0xFF5A4D40),
            onTertiaryContainer = Color(0xFF453B31),
            gameOverOverlay = Color(0xB0251B11),
            onGameOverOverlay = Color(0xFFBBADA0),
            gameWonOverlay = Color(0x80695309),
            onGameWonOverlay = Color(0xFFEBE6E2),
        )
    }
}

internal val LocalColorScheme = staticCompositionLocalOf { Game2048ColorScheme() }