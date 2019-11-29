package gvfraction

import java.util.*

interface FractionOperator {
    fun whole() : Int
    fun numerator(): Int
    fun denominator(): Int
    fun isPositive(): Boolean
    fun makeProper(): Unit
    fun isProper(): Boolean
    fun toProper(): FractionOperator
    fun reduce(): Unit
    fun isReduced(): Boolean

    /** Operator functions */

    // Addition
    operator fun plus(other:FractionOperator) : FractionOperator
    operator fun plus(other:Int) : FractionOperator

    // Subtraction and unary minus
    operator fun minus(other:FractionOperator) : FractionOperator
    operator fun minus(other:Int) : FractionOperator
    operator fun unaryMinus(): FractionOperator

    // Multiplication
    operator fun times(other:FractionOperator) : FractionOperator
    operator fun times(other:Int):FractionOperator

    // Comparison
    operator fun compareTo(other:FractionOperator) : Int

    // Element lookup
    operator fun get(pos:Int) : Optional<Int>

    // EXTRA CREDIT: Decimal expansion & isRepeating
    operator fun invoke(len:Int) : String
    fun isRepeating(): Optional<String>
}


