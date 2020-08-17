interface MapEngineHelper {

    fun addHintsByMarkedCell(cellIndex: Int, mapArray: Array<Char>, arrWithMinesIndices: Array<Int?>, mapWidth: Int, mapHeight: Int) {
        exploreCell(cellIndex, mapArray, arrWithMinesIndices, mapWidth, mapHeight)
    }

    private fun exploreCell(cellIndex: Int, mapArray: Array<Char>, arrWithMinesIndices: Array<Int?>, mapWidth: Int, mapHeight: Int) {
        // update current cell
        val minesAround = getMinesAround(cellIndex, mapArray, arrWithMinesIndices, mapWidth)
        if (minesAround == 0) {
            mapArray[cellIndex] = '/'
        } else {
            setHintOnCell(cellIndex, mapArray, minesAround, mapWidth)
            return
        }

        // check left-right
        val (leftBorder, rightBorder) = getLineBorderByCellIndex(mapArray.size, mapWidth, cellIndex)

        val cellRight = cellIndex + 1
        if (cellRight <= rightBorder && canBeExplored(cellRight, mapArray, arrWithMinesIndices, mapWidth)) {
            exploreCell(cellRight, mapArray, arrWithMinesIndices, mapWidth, mapHeight)
        }

        val cellLeft = cellIndex - 1
        if (cellLeft >= leftBorder && canBeExplored(cellLeft, mapArray, arrWithMinesIndices, mapWidth)) {
            exploreCell(cellLeft, mapArray, arrWithMinesIndices, mapWidth, mapHeight)
        }

        // check over
        val cellOver = cellIndex - mapWidth
        if (cellOver >= 0) {
            if (canBeExplored(cellOver, mapArray, arrWithMinesIndices, mapWidth)) {
                exploreCell(cellOver, mapArray, arrWithMinesIndices, mapWidth, mapHeight)
            }

            val (lBorderOver, rBorderOver) = getLineBorderByCellIndex(mapArray.size, mapWidth, cellOver)

            val cellOverRight = cellOver + 1
            if (cellOverRight <= rBorderOver && canBeExplored(cellOverRight, mapArray, arrWithMinesIndices, mapWidth)) {
                exploreCell(cellOverRight, mapArray, arrWithMinesIndices, mapWidth, mapHeight)
            }

            val cellOverLeft = cellOver - 1
            if (cellOverLeft >= lBorderOver && canBeExplored(cellOverLeft, mapArray, arrWithMinesIndices, mapWidth)) {
                exploreCell(cellOverLeft, mapArray, arrWithMinesIndices, mapWidth, mapHeight)
            }
        }


        // check under
        val cellUnder = cellIndex + mapWidth
        if (cellUnder < mapArray.size) {
            if (canBeExplored(cellUnder, mapArray, arrWithMinesIndices, mapWidth)) {
                exploreCell(cellUnder, mapArray, arrWithMinesIndices, mapWidth, mapHeight)
            }

            val (lBorderUnder, rBorderUnder) = getLineBorderByCellIndex(mapArray.size, mapWidth, cellUnder)

            val cellUnderRight = cellUnder + 1
            if (cellUnderRight <= rBorderUnder && canBeExplored(cellUnderRight, mapArray, arrWithMinesIndices, mapWidth)) {
                exploreCell(cellUnderRight, mapArray, arrWithMinesIndices, mapWidth, mapHeight)
            }

            val cellUnderLeft = cellUnder - 1
            if (cellUnderLeft >= lBorderUnder && canBeExplored(cellUnderLeft, mapArray, arrWithMinesIndices, mapWidth)) {
                exploreCell(cellUnderLeft, mapArray, arrWithMinesIndices, mapWidth, mapHeight)
            }
        }
    }

    private fun setHintOnCell(cellIndex: Int, mapArray: Array<Char>, mines: Int, mapWidth: Int) {
        val symHint = mines.toString().toCharArray()
        mapArray[cellIndex] = symHint[0]
    }

    private fun canBeExplored(cellIndex: Int, mapArray: Array<Char>, arrWithMinesIndices: Array<Int?>, mapWidth: Int): Boolean {
        return !arrWithMinesIndices.contains(cellIndex) && mapArray[cellIndex] != '/' && !mapArray[cellIndex].isDigit()
                && isExploredCellNear(cellIndex, mapArray, mapWidth)
    }

    private fun isExploredCellNear(cellIndex: Int, mapArray: Array<Char>, mapWidth: Int): Boolean {
        val (leftBorder, rightBorder) = getLineBorderByCellIndex(mapArray.size, mapWidth, cellIndex)

        if (mapArray[cellIndex] == '/') {
            return true
        }

        val cellLeft = cellIndex - 1
        if (cellLeft >= leftBorder && mapArray[cellLeft] == '/') {
            return true
        }

        val cellRight = cellIndex + 1
        if (cellRight <= rightBorder && mapArray[cellRight] == '/') {
            return true
        }

        val cellOver = cellIndex - mapWidth
        if (cellOver >= 0) {
            if (mapArray[cellOver] == '/') {
                return true
            }

            val (lBorderOver, rBorderOver) = getLineBorderByCellIndex(mapArray.size, mapWidth, cellOver)

            val cellOverRight = cellOver + 1
            if (cellOverRight <= rBorderOver && mapArray[cellOverRight] == '/') {
                return true
            }

            val cellOverLeft = cellOver - 1
            if (cellOverLeft >= lBorderOver && mapArray[cellOverLeft] == '/') {
                return true
            }
        }


        val cellUnder = cellIndex + mapWidth
        if (cellUnder < mapArray.size) {

            if (mapArray[cellUnder] == '/') {
                return true
            }
            val (lBorderUnder, rBorderUnder) = getLineBorderByCellIndex(mapArray.size, mapWidth, cellUnder)

            val cellUnderRight = cellUnder + 1
            if (cellUnderRight <= rBorderUnder && mapArray[cellUnderRight] == '/') {
                return true
            }

            val cellUnderLeft = cellUnder - 1
            if (cellUnderLeft >= lBorderUnder && mapArray[cellUnderLeft] == '/') {
                return true
            }
        }

        return false
    }

    private fun getMinesAround(cellIndex: Int, mapArray: Array<Char>, arrWithMinesIndices: Array<Int?>, mapWidth: Int): Int {

        var minesCounter = 0

        minesCounter += getMinesOnLineNearCellIndex(cellIndex, mapWidth, mapArray, arrWithMinesIndices)

        val cellOver = cellIndex - mapWidth
        if (cellOver >= 0) {
            minesCounter += getMinesOnLineNearCellIndex(cellOver, mapWidth, mapArray, arrWithMinesIndices)
        }

        val cellUnder = cellIndex + mapWidth
        if (cellUnder < mapArray.size) {
            minesCounter += getMinesOnLineNearCellIndex(cellUnder, mapWidth, mapArray, arrWithMinesIndices)
        }

        return minesCounter
    }

    private fun getMinesOnLineNearCellIndex(cellIndex: Int, mapWidth: Int, mapArray: Array<Char>, arrWithMinesIndices: Array<Int?>): Int {
        val (leftBorder, rightBorder) = getLineBorderByCellIndex(mapArray.size, mapWidth, cellIndex)

        var minesCounter = 0

        if (arrWithMinesIndices.contains(cellIndex)) {
            minesCounter++
        }

        val cellRight = cellIndex + 1
        if (cellRight <= rightBorder) {
            if (arrWithMinesIndices.contains(cellRight)) {
                minesCounter++
            }
        }

        val cellLeft = cellIndex - 1
        if (cellLeft >= leftBorder) {
            if (arrWithMinesIndices.contains(cellLeft)) {
                minesCounter++
            }
        }

        return minesCounter
    }

    private fun addHintsOnLine(cellIndex: Int, mapArray: Array<Char>, mapWidth: Int) {
        updateHintOnCell(cellIndex, mapArray)

        val (lineLeftBorder, lineRightBorder) = getLineBorderByCellIndex(mapArray.size, cellIndex, mapWidth)
        val cellOnRightIndex = cellIndex + 1
        val cellOnLeftIndex = cellIndex - 1

        if (cellOnRightIndex <= lineRightBorder) updateHintOnCell(cellOnRightIndex, mapArray)
        if (cellOnLeftIndex >= lineLeftBorder) updateHintOnCell(cellOnLeftIndex, mapArray)
    }

    private fun updateHintOnCell(cellIndex: Int, mapArray: Array<Char>) {
        val symbolOnCell = mapArray[cellIndex]

        when {
            symbolOnCell == '.' -> mapArray[cellIndex] = '1'

            symbolOnCell.isDigit() -> {
                var symIntValue = symbolOnCell.toInt()
                symIntValue += 1
                mapArray[cellIndex] = symIntValue.toChar()
            }
        }
    }

    private fun getLineBorderByCellIndex(mapSize: Int, mapWidth: Int, cellIndex: Int): Array<Int> {
        val numberOfAllLines = mapSize / mapWidth
        var currentLineCursor = numberOfAllLines
        var currentIndexMapCursor = mapSize

        while (currentIndexMapCursor >= 0) {
            if (cellIndex >= currentIndexMapCursor) {
                currentLineCursor++
                break
            }
            currentIndexMapCursor -= mapWidth
            currentLineCursor--
        }

        val lineRightBorder = (currentLineCursor * mapWidth) - 1
        val lineLeftBorder = lineRightBorder - mapWidth + 1

        return arrayOf(lineLeftBorder, lineRightBorder)
    }

    fun addHintsAroundCellWithMine(cellIndex: Int, mapArray: Array<Char>, mapWidth: Int) {
        addHintsOnLine(cellIndex, mapArray, mapWidth)

        val cellIndexOnLineOver = cellIndex - mapWidth
        val cellIndexOnLineUnder = cellIndex + mapWidth

        if (cellIndexOnLineOver >= 0) addHintsOnLine(cellIndexOnLineOver, mapArray, mapWidth)
        if (cellIndexOnLineUnder < mapArray.size) addHintsOnLine(cellIndexOnLineUnder, mapArray, mapWidth)
    }

    fun getCellIndexByCoordinates(x: Int, y: Int, mapWidth: Int): Int {
        // val index = y * mMapWidth - mMapWidth - 1
        val index = (y - 1) * mapWidth - 1
        return index + x
    }
}