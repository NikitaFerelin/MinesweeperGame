interface MapPrintHelper {

    fun printColumnNumbers(mapWidth: Int) {
        print(" |")
        for (i in 1..mapWidth) {
            print(i)
        }
        print("|\n")
    }

    fun printSeparator(mapWidth: Int) {
        print("-|")
        for (i in 1..mapWidth) {
            print("-")
        }
        print("|\n")
    }
}