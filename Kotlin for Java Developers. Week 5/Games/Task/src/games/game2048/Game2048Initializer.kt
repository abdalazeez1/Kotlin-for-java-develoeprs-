package games.game2048

import board.Cell
import board.GameBoard
import board.GameBoardImpl
import kotlin.random.Random

interface Game2048Initializer<T> {
    /*
     * Specifies the cell and the value that should be added to this cell.
     */
    fun nextValue(board: GameBoard<T?>): Pair<Cell, T>?
}

object RandomGame2048Initializer : Game2048Initializer<Int> {
    private fun generateRandomStartValue(): Int =
        if (Random.nextInt(10) == 9) 4 else 2

    /*
     * Generate a random value and a random cell among free cells
     * that given value should be added to.
     * The value should be 2 for 90% cases, and 4 for the rest of the cases.
     * Use the 'generateRandomStartValue' function above.
     * If the board is full return null.
     */
    override fun nextValue(board: GameBoard<Int?>): Pair<Cell, Int>? {

        var collection = listOf<Cell>()
        val list = (board as GameBoardImpl).ce
        for ((indexI, i) in list.copyOfRange(1,board.width+1).withIndex())
            for ((indexJ, j) in list.copyOfRange(1,board.width+1).withIndex())
            {

                if (list[indexI+1][indexJ+1].second == null) {
                    collection = collection + list[indexI+1][indexJ+1].first
                }
            }
        if (collection.isEmpty()) {
            return null
        }
        return collection.first() to generateRandomStartValue()
    }
}