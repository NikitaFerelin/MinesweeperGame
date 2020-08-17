object MinesweeperGame {

    private lateinit var mMapEngine: MapEngine

    private val mMinesNum = askUserForNumberOfMines()

    fun startGame() {
        prepareToGame()
        gameIsGoing()
        gameIsOver()
    }

    private fun prepareToGame() {
        mMapEngine = MapEngine(_numMines = mMinesNum)
        mMapEngine.prepareToGame()
        mMapEngine.printMap()
    }

    private fun gameIsGoing() {
        var foundMinesCount = 0
        var attemptsLeft = 1

        while (foundMinesCount < mMinesNum && attemptsLeft != 0) {
            val userInput = askUserStep()
            val x: Int = userInput[0].toInt()
            val y: Int = userInput[1].toInt()
            val command: String = userInput[2]

            when (command) {
                "mine" -> {
                    if (mMapEngine.markMineAndGetResult(x, y)) {
                        foundMinesCount++
                    }

                    if (foundMinesCount == mMinesNum) {
                        println("Congratulations! You found all the mines!")
                    }
                }
                "free" -> {
                    if (mMapEngine.openCellAndGetResult(x, y)) {
                        attemptsLeft--
                    }

                    if (attemptsLeft == 0) {
                        println("You stepped on a mine and failed!")
                    }
                }
            }
            mMapEngine.printMap()
        }
    }

    private fun gameIsOver() {
        mMapEngine.gameOver()
        mMapEngine.printMap()
    }

    private fun askUserStep(): List<String> {
        print("Set/unset mine marks or claim a cell as free: ")
        return readLine()!!.split(" ")
    }

    private fun askUserForNumberOfMines(): Int {
        print("How many mines do you want on the field? > ")
        return readLine()!!.toInt()
    }
}