package games.gameOfFifteen

interface GameOfFifteenInitializer {
    /*
     * Even permutation of numbers 1..15
     * used to initialized the first 15 cells on a board.
     * The last cell is empty.
     */
    val initialPermutation: List<Int>
}

class RandomGameInitializer : GameOfFifteenInitializer {
    /*
     * Generate a random permutation from 1 to 15.
     * `shuffled()` function might be helpful.
     * If the permutation is not even, make it even (for instance,
     * by swapping two numbers).
     */
    override val initialPermutation by lazy {
        var result = mutableListOf<Int>()
        val list = (1..15).toList().shuffled();
        for (i in 0 .. 4) {
            for (j in 0 .. 4) {
                if(((i*4)+j) < 15)
                result.add(list[((i*4)+j)])
            }
        }
        for (i in 0 until 15 step 4) {
            var sub = list.subList(i, if(i + 4 == 16 )15 else i+4)
            sub = if (isEven(sub)) sub else convertToEven(permutation = sub)
            var k = 0
            for (j in i until i + 3)
                result[if(i + 4 == 16 )15 else i] = sub[k++]
        }


        result
    }


}

private fun convertToEven(permutation: List<Int>): List<Int> {
    var list = permutation.shuffled()
    if (isEven(list)) {
        return list
    }
    return convertToEven(list)
}