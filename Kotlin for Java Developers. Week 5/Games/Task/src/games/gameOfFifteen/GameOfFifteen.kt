package games.gameOfFifteen

import board.*
import games.game.Game
import kotlin.random.Random

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)
    override fun initialize() {
        var list = initializer.initialPermutation.toMutableList<Int?>()
        list.add(15, null)
        (board as GameBoardImpl)
        println("list $list")
        for (i in 1..4) {
            for (j in 1..4) {
                board.ce[i][j] = board.ce[i][j].copy(second = list[((i - 1) * 4) + (j - 1)])
                print("${board.ce[i][j]}  ,")
            }
            println("")
        }


    }

    override fun canMove(): Boolean = true

    override fun hasWon(): Boolean =
        initializer.initialPermutation.zipWithNext().all { it.first < it.second }


    override fun processMove(direction: Direction) {
        (board.moveValuesFifteen(direction))
    }

    override fun get(i: Int, j: Int): Int? {
        println("get $i  $j  ${(board as GameBoardImpl).ce[i][j].second}  ${board.ce[i][j].first.toString()}")
        return (board as GameBoardImpl).ce[i][j].second
    }

}


private fun GameBoard<Int?>.moveValuesFifteen(direction: Direction): Boolean {

    var moved = false
    lateinit var emptyCell: Cell
    (this as GameBoardImpl)
    for (arrayOfPairs in ce.copyOfRange(1, width + 1)) {
        for (a in arrayOfPairs.copyOfRange(1, width + 1))
            if (a.second == null)
                emptyCell = a.first
    }


    println(" find ${emptyCell.i}  , ${emptyCell.j}")
    when (direction) {
        Direction.RIGHT -> {

            if (emptyCell.j > 1) {

                ce[emptyCell.i][emptyCell.j] =
                    (ce[emptyCell.i][emptyCell.j]).copy(second = (ce[emptyCell.i][emptyCell.j - 1]).second)

                ce[emptyCell.i][emptyCell.j - 1] =
                    ce[emptyCell.i][emptyCell.j - 1].copy(second = null)
                moved = true
            }
        }


        Direction.LEFT -> {
            if (emptyCell.j < width) {
                ce[emptyCell.i][emptyCell.j] =
                    (ce[emptyCell.i][emptyCell.j]).copy(second = (ce[emptyCell.i][emptyCell.j + 1]).second)

                ce[emptyCell.i][emptyCell.j + 1] =
                    ce[emptyCell.i][emptyCell.j + 1].copy(second = null)
                moved = true
            }
            }
        Direction.UP -> {
            if (emptyCell.i < width) {
                ce[emptyCell.i][emptyCell.j] =
                    (ce[emptyCell.i][emptyCell.j]).copy(second = (ce[emptyCell.i + 1][emptyCell.j]).second)

                ce[emptyCell.i + 1][emptyCell.j] =
                    ce[emptyCell.i + 1][emptyCell.j].copy(second = null)


                moved = true
            }
        }
        Direction.DOWN -> {
            if (emptyCell.i > 1) {
                ce[emptyCell.i][emptyCell.j] =
                    (ce[emptyCell.i][emptyCell.j]).copy(second = (ce[emptyCell.i - 1][emptyCell.j]).second)

                ce[emptyCell.i - 1][emptyCell.j] =
                    ce[emptyCell.i - 1][emptyCell.j].copy(second = null)


                moved = true
            }
        }
    }
    return moved

}