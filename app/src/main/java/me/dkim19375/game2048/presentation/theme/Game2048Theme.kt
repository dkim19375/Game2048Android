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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.wear.compose.material3.MaterialTheme
import androidx.wear.compose.material3.ProvideTextStyle

object Game2048Theme {

    val buttonColors: Game2048ButtonColors
        @ReadOnlyComposable
        @Composable
        get() = LocalButtonColors.current

    val colorScheme: Game2048ColorScheme
        @ReadOnlyComposable
        @Composable
        get() = LocalColorScheme.current

    val typography: Game2048Typography
        @ReadOnlyComposable
        @Composable
        get() = LocalTypography.current

}

@Composable
fun Game2048Theme(
    light: Boolean = true,
    content: @Composable () -> Unit,
) {
    val buttonColors = if (light) Game2048ButtonColors.Light else Game2048ButtonColors.Dark
    val colorScheme = if (light) Game2048ColorScheme.Light else Game2048ColorScheme.Dark
    val typography = if (light) Game2048Typography.Light else Game2048Typography.Dark
    MaterialTheme(content = {
        CompositionLocalProvider(
            LocalButtonColors provides buttonColors,
            LocalColorScheme provides colorScheme,
            LocalTypography provides typography,
        ) {
            ProvideTextStyle(value = typography.title) {
                content()
            }
        }
    })
}