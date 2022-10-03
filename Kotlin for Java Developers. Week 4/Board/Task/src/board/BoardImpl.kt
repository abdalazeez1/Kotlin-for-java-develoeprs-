package board

import board.Direction.*

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl<T>(width)

class GameBoardImpl<T>(width: Int) : GameBoard<T>, SquareBoardImpl(width) {
    var cellsWithValue: Array<Array<Pair<Cell, T?>>>

    init {
        cellsWithValue =
            Array((width + 1)) { row ->
                Array<Pair<Cell, T?>>((width + 1)) { col ->
                    cells[row][col] to null
                }
            }

        println("This is primary constructor")
    }

    override fun get(cell: Cell): T? {
        return cellsWithValue[cell.i][cell.j].second
    }

    override fun set(cell: Cell, value: T?) {
        cellsWithValue[cell.i][cell.j] = cellsWithValue[cell.i][cell.j].copy(second = value)

    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        var collection = listOf<Cell>()
        for ((indexI, i) in cellsWithValue.copyOfRange(1, width + 1).withIndex())
            for ((indexJ, j) in cellsWithValue.copyOfRange(1, width + 1).withIndex())
                if (predicate(cellsWithValue[indexI][indexJ].second)) collection =
                    collection + cellsWithValue[indexI][indexJ].first
        return collection
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        for ((i, e) in cellsWithValue.copyOfRange(1, width + 1).withIndex())
            for ((j, e1) in cellsWithValue.copyOfRange(1, width + 1).withIndex())
                if (predicate(cellsWithValue[i][j].second)) return cellsWithValue[i][j].first

        return null
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return cellsWithValue.copyOfRange(1, width + 1)
            .any { it.copyOfRange(1, width + 1).any { r -> predicate(r.second) } }
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return cellsWithValue.copyOfRange(1, width + 1)
            .all { it.copyOfRange(1, width + 1).all { r -> predicate(r.second) } }

    }

}


open class SquareBoardImpl
    (override val width: Int) : SquareBoard {
    val cells: Array<Array<Cell>> =
        Array((width + 1)) { row -> Array((width + 1)) { col -> Cell(row, col) } }


    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if (i == 0 || j == 0) {
            throw IndexOutOfBoundsException()
        }
        return if (width >= i) cells[i][j] else null
    }

    override fun getCell(i: Int, j: Int): Cell {
        if (i == 0 || j == 0) {
            throw IndexOutOfBoundsException()
        }
        return cells[i][j]
    }

    override fun getAllCells(): Collection<Cell> {
        var collection = listOf<Cell>()
        for (i in 1..width) {
            for (j in 1..width)
                collection = collection + cells[i][j]
        }
        return collection
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val list = mutableListOf<Cell>()
        val d: IntProgression = when {
            jRange.first >= width && jRange.last < width -> width..jRange.last
            jRange.last >= width && jRange.first < width -> jRange.first..width
            jRange.first >= width && jRange.last >= width -> 0..0
            else -> jRange
        }
        for (j in d.first toward d.last) {
            list.add(cells[i][j])

        }
        return list
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val list = mutableListOf<Cell>()
        val d: IntProgression = when {
            iRange.first >= width && iRange.last < width -> width..iRange.last
            iRange.last >= width && iRange.first < width -> iRange.first..width
            iRange.first >= width && iRange.last >= width -> 0..0
            else -> iRange
        }
        println(d)

        for (i in d.first toward d.last) {
            list.add(cells[i][j])

        }
        return list
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        val underAllow = i + 1 <= width
        val rightAllow = j + 1 <= width
        val topAllow = i - 1 > 0
        val leftAllow = j - 1 > 0
        return when (direction) {
            DOWN -> if (underAllow) cells[i + 1][j] else null
            UP -> if (topAllow) cells[i - 1][j] else null
            RIGHT -> if (rightAllow) cells[i][j + 1] else null
            LEFT -> if (leftAllow) cells[i][j - 1] else null
        }
    }

}

private infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}

private fun getBigger(val1: Int, val2: Int) = if (val1 >= val2) val1 else val2
private fun getSmall(val1: Int, val2: Int) = if (val1 <= val2) val2 else val1