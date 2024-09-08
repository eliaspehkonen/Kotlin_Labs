package Lab04

class Fraction(numerator: Int, denominator: Int, private val sign: Int = 1) : Comparable<Fraction> {
    private val numerator: Int
    private val denominator: Int

    init {
        if (denominator == 0) {
            throw IllegalArgumentException("Denominator cannot be zero")
        }

        // Normalize the sign so that only the numerator carries the sign
        val gcd = gcd(numerator, denominator)
        val absNumerator = kotlin.math.abs(numerator / gcd)
        val absDenominator = kotlin.math.abs(denominator / gcd)

        // Determine the correct sign of the fraction based on inputs
        val overallSign = if (numerator < 0 || sign < 0) -1 else 1

        this.numerator = overallSign * absNumerator
        this.denominator = absDenominator
    }

    private fun gcd(a: Int, b: Int): Int {
        return if (b == 0) a else gcd(b, a % b)
    }

    // Negate the fraction (flip the sign of the numerator)
    fun negate(): Fraction {
        return Fraction(-this.numerator, this.denominator)
    }

    // Addition of two fractions
    fun add(other: Fraction): Fraction {
        val newNumerator = this.numerator * other.denominator + other.numerator * this.denominator
        val newDenominator = this.denominator * other.denominator
        return Fraction(newNumerator, newDenominator)
    }

    // Subtraction of two fractions
    operator fun minus(other: Fraction): Fraction {
        val newNumerator = this.numerator * other.denominator - other.numerator * this.denominator
        val newDenominator = this.denominator * other.denominator
        return Fraction(newNumerator, newDenominator)
    }

    // Multiplication of two fractions
    fun mult(other: Fraction): Fraction {
        val newNumerator = this.numerator * other.numerator
        val newDenominator = this.denominator * other.denominator
        return Fraction(newNumerator, newDenominator)
    }

    // Division of two fractions
    operator fun div(other: Fraction): Fraction {
        if (other.numerator == 0) {
            throw ArithmeticException("Cannot divide by zero")
        }
        val newNumerator = this.numerator * other.denominator
        val newDenominator = this.denominator * other.numerator
        return Fraction(newNumerator, newDenominator)
    }

    // Comparison of two fractions
    override fun compareTo(other: Fraction): Int {
        val lhs = this.numerator * other.denominator
        val rhs = other.numerator * this.denominator
        return lhs.compareTo(rhs)
    }

    // String representation of the fraction
    override fun toString(): String {
        return if (numerator < 0) {
            "-${kotlin.math.abs(numerator)}/$denominator"
        } else {
            "$numerator/$denominator"
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Fraction) return false

        return this.numerator == other.numerator && this.denominator == other.denominator
    }

    override fun hashCode(): Int {
        return 31 * numerator + denominator
    }

    operator fun unaryMinus(): Any {
        return Fraction(-numerator, denominator)

    }

    operator fun times(fraction: Fraction): Any {
        return mult(fraction)

    }

    operator fun plus(fraction: Fraction): Any {
        return add(fraction)

    }
}

private operator fun Any.plus(fraction: Fraction): Any {
    return fraction.plus(this as Fraction)
}

fun main() {
    val a = Fraction(1,2,-1)
    println(a)
    println(a.add(Fraction(1,3)))
    println(a.mult(Fraction(5,2, -1)))
    println(a.div(Fraction(2,1)))
    println(-Fraction(1,6) + Fraction(1,2))
    println(Fraction(2,3) * Fraction(3,2))
    println(Fraction(1,2) > Fraction(2,3)) // Comparable interface function compareTo()
}
