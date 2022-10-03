package rationals

import java.math.BigInteger


fun main() {
    println("-578136305229133309744/-966904753430936619984".toRational().toString())
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third

    println(5 divBy 6 == sum)

    val difference: Rational = half - third

    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")

    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)
    println(2000000000L divBy 4000000000L == 1 divBy 2)
    println(2000000000L divBy 4000000000L)
    println(
        "912016490186296920119201192141970416029".toBigInteger() divBy
                "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2
    )
}


data class Rational(val numerator: BigInteger, var denominator: BigInteger) : Comparable<Rational> {
    override operator fun compareTo(other: Rational): Int {

        val first = numerator * other.denominator
        val second = other.numerator * denominator
        if ((this.numerator == other.numerator && this.denominator == other.denominator)||(first==second)) {
            return 0
        }
        if (first > second) {
            return 1;
        }
        return -1;
    }

    operator fun <B> Pair<B, B>.contains(half: B): Boolean where B : Rational {

        if (half == first || half == second) return true
        if (second > half && half > first) {
            return true
        }
        return false
    }

    operator fun Rational.rangeTo(rational: Rational): Pair<Rational, Rational> {
        return Pair(this, rational)
    }

    override fun toString(): String {
        val gcd = gcd(numerator, denominator)
        if (denominator == numerator) {
            return "$numerator"
        }
        if (denominator / gcd == BigInteger.ONE) {
            return "${numerator / gcd}"
        }
        return "$numerator/$denominator"
    }


    ///operater
    operator fun unaryPlus(): Rational {
        return Rational(numerator, denominator)
    }

    operator fun unaryMinus(): Rational {
        return Rational(-numerator, denominator)
    }


    operator fun inc(): Rational {
        return Rational(numerator + denominator, denominator + denominator)
    }

    operator fun dec(): Rational {
        return Rational(numerator - denominator, denominator - denominator)
    }


    operator fun plus(other: Rational): Rational {
        val first = (other.denominator * numerator) + (other.numerator * denominator)
        val second = denominator * other.denominator
        val gcd = gcd(first, second).abs()
        return Rational(
            first / gcd,
            second / gcd
        )
    }


    operator fun minus(other: Rational): Rational {
        val first = (other.denominator * numerator) - (other.numerator * denominator)
        val second = denominator * other.denominator
        val gcd = gcd(first, second).abs()
        return Rational(
            first / gcd,
            second / gcd
        )
    }

    operator fun times(other: Rational): Rational {

        var first = numerator * other.numerator
        var second = denominator * other.denominator
        val gcd = gcd(first, second).abs()
        if (first >= BigInteger.ZERO && second < BigInteger.ZERO) {
            first = -first
            second = second.abs()
        }
        if (first <= BigInteger.ZERO && first <= BigInteger.ZERO) {
            first = first.abs()
            second = second.abs()
        }
        return Rational(first / gcd, second / gcd)
    }


    operator fun div(other: Rational): Rational {

        var first = (numerator * other.denominator).abs()
        var second = (denominator * other.numerator).abs()
        if (second == BigInteger.ZERO) throw  IllegalArgumentException()
        val firstNumberIsNeg=
            (numerator < BigInteger.ZERO && denominator >= BigInteger.ZERO) || (numerator >= BigInteger.ZERO && denominator < BigInteger.ZERO)

        val secondNumeratorNeg =
            (other.numerator < BigInteger.ZERO && other.denominator >= BigInteger.ZERO) || (other.numerator >= BigInteger.ZERO && other.denominator < BigInteger.ZERO)

            if (!firstNumberIsNeg && secondNumeratorNeg ) {
                first = -first
                second = second.abs()

            }
        if (firstNumberIsNeg &&secondNumeratorNeg) {first=first.abs()
            second=second.abs()

        }
        if(firstNumberIsNeg){
            first=-first
        }
        println(first)
        println(second)
        val gcd = gcd(first, second).abs()
        return Rational(first / gcd, second / gcd)
    }


    operator fun rem(other: Rational): Rational {
        return Rational(numerator % other.numerator, denominator % other.denominator)
    }
}

fun String.toRational(): Rational {

    val indexOf = this.indexOf('/')
    if(indexOf == -1){
       return Rational(
            BigInteger(this), BigInteger.ONE

        )
    }
    var first = BigInteger(substring(0, indexOf))
    var second = BigInteger(substring(indexOf + 1, length))

    val gcd: BigInteger = gcd(first.abs(), second.abs())
    if (first < BigInteger.ZERO && second < BigInteger.ZERO) {
        first = first.abs()
        second = second.abs()
    }
    if (first >= BigInteger.ZERO && second < BigInteger.ZERO) {
        first = -first
        second = second.abs()
    }

    return Rational(
        first / gcd,
        second / gcd
    )
}


infix fun <T : Number> T.divBy(other: T): Rational {
    if (other == 1) {
        return Rational(bigInteger(this), BigInteger.ONE)
    }
    var first = bigInteger(this)
    var second = bigInteger(other)
    val gcd = gcd(first, second)
    if (first >= BigInteger.ZERO && second < BigInteger.ZERO) {
        first = -first
        second = second.abs()
    }
    if (first <= BigInteger.ZERO && first <= BigInteger.ZERO) {
        first = first.abs()
        second = second.abs()
    }
    return Rational(
        first / gcd,
        second / gcd.abs()
    )
}

private fun <T : Number> bigInteger(other: T): BigInteger = when (other) {
    is BigInteger -> other
    is Int -> other.toBigInteger()
    is Long -> other.toBigInteger()
    else -> other.toString().toBigInteger()
}


fun gcd(n1: BigInteger, n2: BigInteger): BigInteger {
    return if (n2 != BigInteger.ZERO) gcd(n2, n1 % n2) else n1
}