package Lab03

class Lotto {
    companion object {
        val lottoRange = 1..40
        const val n = 7
        val secretNumbers: List<Int> = listOf()  // Initialize this as needed or leave empty
    }

    fun pickNDistinct(range: IntRange, n: Int): List<Int>? {
        if (range.isEmpty() || n <= 0) {
            return null
        }

        if (range.count() < n) {
            return null
        }

        val list = range.toMutableList()
        val result = mutableListOf<Int>()

        for (i in 1..n) {
            val index = (0 until list.size).random()
            result.add(list[index])
            list.removeAt(index)
        }

        return result
    }

    fun numDistinct(list: List<Int>): Int {
        return list.distinct().count()
    }

    fun numCommon(list1: List<Int>, list2: List<Int>): Int {
        return list1.intersect(list2).count()
    }

    fun isLegalLottoGuess(guess: List<Int>, range: IntRange = lottoRange, count: Int = n): Boolean {
        return guess.size == count && guess.all { it in range } && numDistinct(guess) == count
    }

    fun checkGuess(guess: List<Int>, secret: List<Int> = secretNumbers): Int {
        return if (isLegalLottoGuess(guess)) {
            numCommon(guess, secret)
        } else {
            0
        }
    }
}

fun playLotto(lotto: Lotto) {
    // Generate secret lotto numbers
    val secretNumbers = lotto.pickNDistinct(Lotto.lottoRange, Lotto.n) ?: return
    println("Let's play Lotto!")

    while (true) {
        // Prompt the user for input
        val guess = readNDistinct(Lotto.lottoRange.first, Lotto.lottoRange.last, Lotto.n)

        if (guess == null) {
            println("Invalid input. Please try again.")
            continue
        }

        // Check how many numbers are correct
        val correctGuesses = lotto.checkGuess(guess, secretNumbers)
        println("Lotto numbers: $secretNumbers, you got $correctGuesses correct")

        // Now use findLotto to let the computer guess
        val (steps, computerGuess) = findLotto(lotto, secretNumbers, Lotto.lottoRange.first, Lotto.lottoRange.last)
        if (computerGuess.isNotEmpty()) {
            println("Computer guess in $steps steps is $computerGuess")
        } else {
            println("Computer could not guess the correct numbers.")
        }

        // Ask if the user wants to play another round
        println("More? (Y/N):")
        val answer = readLine()
        if (answer?.lowercase() != "y") {
            println("Thank you for playing! Goodbye!")
            break
        }
    }
}

fun readNDistinct(low: Int, high: Int, n: Int): List<Int>? {
    println("Enter $n distinct numbers between $low and $high, separated by commas:")
    val input = readLine()
    val numbers = input?.split(",")?.map { it.trim().toIntOrNull() }?.filterNotNull()
    return if (numbers != null && numbers.size == n && numbers.all { it in low..high } && numbers.toSet().size == n) {
        numbers
    } else {
        null
    }
}

fun findLotto(lotto: Lotto, secretNumbers: List<Int>, low: Int, high: Int): Pair<Int, List<Int>> {
    var attempts = 0

    while (true) {
        attempts++
        val guess = lotto.pickNDistinct(low..high, Lotto.n) ?: continue
        val correct = lotto.checkGuess(guess, secretNumbers)

        if (correct == Lotto.n) {
            return Pair(attempts, guess)
        }

        // Optional: log the attempt every 1000 steps to show progress
        if (attempts % 1000 == 0) {
            println("Attempt $attempts: $guess with $correct correct numbers.")
        }
    }
}

fun main() {
    val lotto = Lotto()
    playLotto(lotto)
}
