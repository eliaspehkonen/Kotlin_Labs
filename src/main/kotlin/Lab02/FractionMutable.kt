package Lab02

class FractionMutable(var numerator: Int, var denominator: Int, var sign: Int = 1) {
    init {
        require(denominator != 0) { "Denominator must not be zero" }
        normalize()
    }

    fun negate() {
        sign *= -1
        normalize()
    }

    fun add(other: FractionMutable) {
        val newNumerator = sign * numerator * other.denominator + other.sign * other.numerator * denominator
        val newDenominator = denominator * other.denominator
        numerator = kotlin.math.abs(newNumerator)
        denominator = newDenominator
        sign = if (newNumerator < 0) -1 else 1
        normalize()
    }

    fun mult(other: FractionMutable) {
        numerator *= other.numerator
        denominator *= other.denominator
        sign *= other.sign
        normalize()
    }

    fun div(other: FractionMutable) {
        numerator *= other.denominator
        denominator *= other.numerator
        sign *= other.sign
        normalize()
    }

    fun intPart(): Int {
        return sign * (numerator / denominator)
    }

    override fun toString(): String {
        return "${if (sign < 0) "-" else ""}${kotlin.math.abs(numerator)}/${denominator}"
    }

    private fun normalize() {
        if (numerator < 0) {
            sign *= -1
            numerator = -numerator
        }
        val gcd = gcd(numerator, denominator)
        numerator /= gcd
        denominator /= gcd
    }

    private fun gcd(a: Int, b: Int): Int {
        var x = kotlin.math.abs(a)
        var y = kotlin.math.abs(b)
        while (y != 0) {
            val t = y
            y = x % t
            x = t
        }
        return x
    }
}

fun main() {
    val a = FractionMutable(1, 2, -1)
    a.add(FractionMutable(1, 3))
    println(a)  // Expected output: -1/6
    a.mult(FractionMutable(5, 2, -1))
    println(a)  // Expected output: 5/12
    a.div(FractionMutable(2, 1))
    println(a)  // Expected output: 5/24
}
