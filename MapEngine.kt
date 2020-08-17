class MapEngine(_width: Int = 9, _height: Int = 9, _numMines: Int, _showMines: Boolean = false, _showHints: Boolean = false,
                _showHintsAfterMark: Boolean = true)
    : Map(_width, _height, _numMines), MapEngineHelper {

    private val mShowMines: Boolean = _showMines
    private val mShowHints: Boolean = _showHints
    private val mShowHintsAfterMark: Boolean = _showHintsAfterMark

    fun prepareToGame() {
        if (mShowMines) addAllMinesVisibility()
        if (mShowHints) addAllHintsVisibility()
    }

    fun gameOver() {
        addAllMinesVisibility()
    }

    fun printMap() {
        showMap()
    }


    private fun addAllMinesVisibility() {
        for (index in mMapArray.indices) {
            if (mCellsWithMine.contains(index)) {
                mMapArray[index] = 'X'
            }
        }
    }

    private fun addAllHintsVisibility() {
        for (cellWithMineIndex in mCellsWithMine) {
            cellWithMineIndex?.let {
                addHintsAroundCellWithMine(cellWithMineIndex, mMapArray, mMapWidth)
            }
        }
    }

    fun markMineAndGetResult(x: Int, y: Int): Boolean {
        val cellIndex = getCellIndexByCoordinates(x, y, mMapWidth)

        when {
            mMapArray[cellIndex] == '.' -> {
                mMapArray[cellIndex] = '*'
                if (mCellsWithMine.contains(cellIndex)) {
                    return true
                }
            }
            mMapArray[cellIndex] == '/' -> println("It is an explored cell")
            else -> println("There is a number on the cell")
        }

        return false
    }

    fun openCellAndGetResult(x: Int, y: Int): Boolean {
        val cellIndex = getCellIndexByCoordinates(x, y, mMapWidth)

        return when {
            mMapArray[cellIndex] == '/' -> {
                println("It is an explored cell")
                false
            }
            mMapArray[cellIndex].isDigit() -> {
                println("There is a number on the cell")
                return false
            }
            // step on mine
            mCellsWithMine.contains(cellIndex) -> {
                true
            }
            else -> {
                if (mShowHintsAfterMark) addHintsByMarkedCell(cellIndex, mMapArray, mCellsWithMine, mMapWidth, mMapHeight)
                false
            }
        }
    }
}
