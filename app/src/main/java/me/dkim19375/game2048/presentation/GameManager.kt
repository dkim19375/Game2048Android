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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import me.dkim19375.game2048.presentation.theme.Game2048Theme
import java.io.File

const val GRID_SIZE = 4

object GameManager {
    var score by mutableIntStateOf(0)
    var bestScore by mutableIntStateOf(0)
    var gameState by mutableStateOf(GameState.GAME_RUNNING)

    private var hasWonYet = false

    val board = Array(GRID_SIZE) {
        mutableStateListOf<Int>().apply {
            repeat(GRID_SIZE) { add(0) }
        }
    }

    fun reset() {
        board.forEach { list -> list.fill(0) }
        score = 0
    }

    fun generateStart() {
        repeat(2) {
            generateRandom()
        }
    }

    private fun generateRandom() {
        val (x, y) = board.mapIndexed { i, col ->
            col.withIndex().filter { (_, num) -> num == 0 }.map { (j, _) -> i to j }
        }.flatten().randomOrNull() ?: return
        val value = if (Math.random() < 0.9) 2 else 4
        board[x][y] = value
    }

    @Composable
    fun handleSwipe(direction: Direction) {
        if (gameState != GameState.GAME_RUNNING) {
            return
        }
        val (resultBoard, resultScore) = getSwipeResult(direction)
        if (board.map(SnapshotStateList<Int>::toList) == resultBoard.map(IntArray::toList)) {
            return
        }
        resultBoard.forEachIndexed { i, array ->
            array.forEachIndexed { j, num ->
                if (board[i][j] != num) {
                    board[i][j] = num
                }
            }
        }
        if (score != resultScore) {
            score = resultScore
            saveBestScore()
        }
        generateRandom()
        saveBoard()
        LaunchedEffect(null) {
            if (gameState != GameState.GAME_RUNNING) {
                return@LaunchedEffect
            }
            if (board.any { list -> list.any { it == 0 } }) return@LaunchedEffect
            if (Direction.entries.any { direction ->
                    getSwipeResult(direction).board.map(IntArray::toList) != board.map(
                        SnapshotStateList<Int>::toList
                    )
                }) {
                return@LaunchedEffect
            }
            gameState = GameState.GAME_OVER
        }
        if (!hasWonYet && board.any { list -> list.any { it == 2048 } }) {
            gameState = GameState.GAME_WON
            hasWonYet = true
        }
    }

    private fun getSwipeResult(
        direction: Direction,
        board: Array<out List<Int>> = this.board,
    ): SwipeResult {
        val resultBoard = Array(GRID_SIZE) { board[it].toIntArray() }
        var resultScore = score

        fun swap() {
            val temp = resultBoard.map(IntArray::toList)
            for (i in resultBoard.indices) {
                for (j in resultBoard[i].indices) {
                    resultBoard[i][j] = temp[j][i]
                }
            }
        }

        val shouldSwap = !direction.isVertical
        if (shouldSwap) {
            swap()
        }

        for (i in board.indices) {
            val list = resultBoard[i]
            val withoutZeroes = list.filterNot(0::equals).run {
                if (direction.isPositive) asReversed() else this
            }
            if (withoutZeroes.isEmpty()) continue
            var justCombined = false
            val result = if (withoutZeroes.size < 2) withoutZeroes.toMutableList() else {
                withoutZeroes.fold(mutableListOf()) { acc, num ->
                    val last = acc.lastOrNull()
                    if (justCombined || last != num) {
                        acc.add(num)
                        justCombined = false
                        return@fold acc
                    }
                    acc[acc.lastIndex] = num * 2
                    justCombined = true
                    resultScore += num * 2
                    acc
                }
            }
            while (result.size < GRID_SIZE) {
                result.add(0)
            }
            val newResult = if (direction.isPositive) result.asReversed() else result
            for (j in newResult.indices) {
                resultBoard[i][j] = newResult[j]
            }
        }

        if (shouldSwap) {
            swap()
        }

        return SwipeResult(resultBoard, resultScore)
    }

    @Composable
    fun fetchBoard() {
        val storedBoard = File(LocalContext.current.filesDir, "board").let { file ->
            if (!file.exists()) {
                return@let null
            }
            val lines = file.readLines().take(GRID_SIZE)
            if (lines.size < GRID_SIZE) {
                return@let null
            }
            val allowedNums = Game2048Theme.buttonColors.toMap().keys
            lines.mapNotNull { line ->
                line.split(",")
                    .mapNotNull(String::toIntOrNull)
                    .filter(allowedNums::contains)
                    .take(GRID_SIZE)
                    .takeIf { it.size == GRID_SIZE }
            }
        } ?: return
        if (storedBoard.none { list -> list.any { it == 0 } }) {
            if (Direction.entries.none { direction ->
                    getSwipeResult(
                        direction = direction,
                        board = storedBoard.toTypedArray()
                    ).board.map(IntArray::toList) != storedBoard
                }) {
                gameState = GameState.GAME_OVER
            }
        }

        board.forEachIndexed { i, list ->
            list.forEachIndexed { j, _ ->
                board[i][j] = storedBoard[i][j]
            }
        }
    }

    @Composable
    fun saveBoard() {
        val file = File(LocalContext.current.filesDir, "board")
        LaunchedEffect(null) {
            file.writeText(board.joinToString("\n") { list ->
                list.joinToString(",")
            })
        }
    }

    @Composable
    fun fetchBestScore() {
        val storedBestScore = File(LocalContext.current.filesDir, "best_score").let { file ->
            if (file.exists()) file.readText().toIntOrNull() ?: 0 else 0
        }
        bestScore = storedBestScore
    }

    @Composable
    fun saveBestScore() {
        if (score > bestScore) {
            bestScore = score
            val file = File(LocalContext.current.filesDir, "best_score")
            LaunchedEffect(null) {
                file.writeText(score.toString())
            }
        }
    }

    private data class SwipeResult(
        val board: Array<IntArray>,
        val score: Int,
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as SwipeResult

            if (!board.contentDeepEquals(other.board)) return false
            return score == other.score
        }

        override fun hashCode(): Int {
            var result = board.contentDeepHashCode()
            result = 31 * result + score
            return result
        }
    }
}