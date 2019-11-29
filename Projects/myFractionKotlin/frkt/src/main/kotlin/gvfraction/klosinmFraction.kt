package gvfraction
import gvfraction.FractionOperator
import java.util.*
import kotlin.math.absoluteValue


class KlosinmFraction() : FractionOperator{

    var w: Int = 0
    var x: Int = 0
    var y: Int = 1
    var isPos: Boolean = true


    constructor(a: Int) : this(){
        w = a
        x = 0
        y = 1
    }

    override fun whole(): Int {
        return (w).absoluteValue
    }
    override fun numerator(): Int {
        return (x).absoluteValue
    }
    override fun denominator(): Int {
        return (y).absoluteValue
    }
    override fun isPositive(): Boolean {
        return isPos
    }


    override fun makeProper() {
        val num: Int

        val whole: Int

        if( x < y){
            return
        }
        else if(x > y){
            num = x % y
            whole = w + (x-num)/y

            x = num
            w = whole
            if ( x == 0){
                y = 0
            }
        }
        else if (x == y){
            x = 0
            y = 1
            w = 1
        }

    }
    override fun isProper(): Boolean {
        TODO("not implemented")
    }

    override fun toProper(): FractionOperator {
        TODO("not implemented")
    }

    override fun reduce() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isReduced(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun plus(other: FractionOperator): FractionOperator {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun plus(other: Int): FractionOperator {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun minus(other: FractionOperator): FractionOperator {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun minus(other: Int): FractionOperator {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unaryMinus(): FractionOperator {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun times(other: FractionOperator): FractionOperator {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun times(other: Int): FractionOperator {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun compareTo(other: FractionOperator): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(pos: Int): Optional<Int> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun invoke(len: Int): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isRepeating(): Optional<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
