import kotlin.random.Random

open class Map(_width: Int, _height: Int, _numMines: Int) : MapPrintHelper {

    private class WrongInputConfigurationException(s: String) : Exception(s)

    init {
        checkInputData(_width, _height, _numMines)
    }

    protected lateinit var mMapArray: Array<Char>

    protected val mMapSize: Int = _width * _height

    protected val mCellsWithMine: Array<Int?> = arrayOfNulls(_numMines)

    protected val mMapWidth: Int = _width
    protected val mMapHeight: Int = _height
    protected val mMinesNumber: Int = _numMines

    init {
        initMap()
    }

    private fun initMap() {
        mMapArray = Array(mMapSize) { '.' }
        generateAndFillCellsWithMines()
    }

    protected fun showMap() {
        printColumnNumbers(mMapWidth)
        printSeparator(mMapWidth)

        var indexCursor = 0

        for (i in 1..mMapHeight) {
            print("$i|")

            for (j in 1..mMapWidth) {
                print(mMapArray[indexCursor])
                indexCursor++
            }
            print("|\n")
        }

        printSeparator(mMapWidth)
    }

    private fun checkInputData(width: Int, height: Int, numMines: Int) {
        if (numMines < 0 || width < 0 || height < 0 || numMines > height * width) {
            throw WrongInputConfigurationException("Wrong input data")
        }
    }

    private fun generateAndFillCellsWithMines() {
        var minesLeftToGenerate = mMinesNumber

        while (minesLeftToGenerate > 0) {
            val generatedCellForMine = Random.nextInt(from = 0, until = mMapSize)

            if (mCellsWithMine.contains(generatedCellForMine)) continue

            mCellsWithMine[minesLeftToGenerate - 1] = generatedCellForMine
            minesLeftToGenerate--
        }

    }
}