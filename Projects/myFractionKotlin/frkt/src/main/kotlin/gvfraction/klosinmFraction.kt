package gvfraction
import gvfraction.FractionOperator
import java.util.*
import kotlin.math.absoluteValue
/*_________________
Monica Klosin
Dec 2 2019
Fraction Maker - Kotlin
__________________*/
class KlosinmFraction() : FractionOperator {

    //default Constructor
    var w: Int = 0
    var x: Int = 0
    var y: Int = 1
    var isPos: Boolean = true

    //constructor that initializes the whole number of the fraction
    constructor(a: Int) : this() {
        w = a
        x = 0
        y = 1
        if (w < 0)
            this.isPos = false
        else
            this.isPos = true
    }

    constructor(s: String) : this() {
        //split values by spaces and "/" into array
        val number = s.split(" ", "/").toTypedArray()

        //if size 3, then w x/y
        if (number.size == 3) {
            this.w = number[0].toInt()
            this.x = number[1].toInt()
            this.y = number[2].toInt()
        }
        //if size 2, then x/y
        else if (number.size == 2) {
            this.w = 0
            this.x = number[0].toInt()
            this.y = number[1].toInt()
        }
        //if size 1. then w
        else if (number.size == 1) {
            this.w = number[0].toInt()
            this.x = 0
            this.y = 1
        } else {
            throw IllegalArgumentException("Not valid input!")
        }

        //divide by 0 error
        if (this.y == 0)
            throw IllegalArgumentException("Can't divide by 0!")

        //Positive or Not
        if (this.w < 0 || this.x < 0)
            this.isPos = false
        else
            this.isPos = true
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

    /**_____________________________________________________________________**/
    /**--Proper--**/
    override fun makeProper() {
        var num: Int
        var whole: Int

        if (this.x < this.y) {
            return
        } else if (this.x > this.y) {
            num = this.x.rem(this.y)
            whole = this.w + (this.x - num) / this.y

            this.x = num
            this.w = whole
            if (this.x == 0) {
                this.y = 1
            }
        } else if (this.x == this.y) {
            this.x = 0
            this.y = 1
            this.w = 1
        }
    }

    override fun isProper(): Boolean {
        if (((this.x).compareTo(this.y) > 0) || this.x == 0)
            return false
        else
            return true
    }

    override fun toProper(): FractionOperator {
        var funct = this
        funct.makeProper()
        return funct
    }

    /**_____________________________________________________________________**/
    //greatest common factor
    fun gcf(a: Int, b: Int): Int {
        if (a.rem(b) == 0)
            return (b).absoluteValue
        else
            return gcf(b, a.rem(b))
    }

    //least common mutiple
    fun lcm(a: Int, b: Int): Int {
        return (a / gcf(a, b)) * b
    }

    //make improper
    fun makeImproper(): Int {
        var num1: Int
        if (this.w < 0)
            num1 = ((this.w * this.y) - this.x.absoluteValue)
        else if (this.w == 0)
            num1 = this.x
        else
            num1 = ((this.w * this.y) + this.x)
        return num1
    }
    /**_____________________________________________________________________**/
    /**--Reduce--**/
    override fun reduce() {
        val divide: Int = gcf(this.x, this.y)
        val top: Int = this.x / divide
        val bottom: Int = this.y / divide
        this.x = top
        this.y = bottom
    }

    override fun isReduced(): Boolean {
        val a: Int = gcf(this.x, this.y)
        if (a == 1)
            return true
        else
            return false
    }

    /**_____________________________________________________________________**/
    //Fraction + Fraction
    override fun plus(other: FractionOperator): FractionOperator {
        var fract = this
        val fract2 = other as KlosinmFraction

        //finding consistent denominator
        var lcd: Int = lcm(fract.y, fract2.y)

        //making fractions improper and have common denominator
        var numerator1: Int = (lcd / fract.y) * fract.makeImproper()
        var numerator2: Int = (lcd / fract2.y) * fract2.makeImproper()

        //Sum of both numerators
        var sumNum: Int = numerator2 + numerator1

        if (sumNum < 0 || fract.w < 0)
            fract.isPos = false
        else
            fract.isPos = true

        fract.w = 0
        fract.x = sumNum.absoluteValue
        fract.y = lcd

        fract.reduce()
        fract.makeProper()

        return fract
    }

    //Fraction + int
    override fun plus(other: Int): FractionOperator {
        var fract = this
        //check if fraction is positive or not
        val Positive = (fract.x + fract.w * fract.y) + (other * fract.y)
        if (Positive < 0) {
            fract.isPos = false
        } else {
            fract.isPos = true
        }

        //add fraction numerator with whole numerator as a fraction with same denominator
        val newNum = fract.makeImproper() + (other * fract.y)

        fract.w = 0
        fract.x = newNum.absoluteValue

        fract.makeProper()
        fract.reduce()

        return fract
    }

    //Fraction - Fraction
    override fun minus(other: FractionOperator): FractionOperator {
        var fract = this
        val fract2 = other as KlosinmFraction

        //new  numerator with common denominator
        val num1: Int = fract2.makeImproper().absoluteValue * fract.y
        val num2: Int = fract.makeImproper().absoluteValue * fract2.y
        val sumNum = num2 - num1
        //new denominator
        val dem: Int = fract.y * fract2.y

        //see if fraction is positive or negative
        if (sumNum == 0 || ((fract.w - fract2.w) >= 0))
            fract.isPos = true
        else if (fract.w < 0 || sumNum < 0)
            fract.isPos = false
        else
            fract.isPos = true

        fract.w = 0
        fract.x = sumNum.absoluteValue
        fract.y = dem.absoluteValue

        fract.reduce()
        fract.makeProper()

        return fract
    }

    //Fraction - int
    override fun minus(other: Int): FractionOperator {
        var fract = this
        //make fraction improper, return improper numerator
        var numeratorF: Int = fract.makeImproper()

        //find the value of whole number numerator with the denominator of the fraction
        var numeratorW: Int = other * fract.y

        //fraction numerator - whole numerator
        var newNum: Int = numeratorF.absoluteValue - numeratorW.absoluteValue

        //Find if fraction - int is negative or not
        var newNumBool: Int = numeratorF - numeratorW
        if (newNumBool < 0)
            fract.isPos = false
        else
            fract.isPos = true

        fract.w = 0
        fract.x = newNum.absoluteValue

        fract.reduce()
        fract.makeProper()

        return fract
    }

    //change sign
    override fun unaryMinus(): FractionOperator {
        var fract = this
        //value WAS negative, make pos
        if (fract.w < 0 || fract.x < 0) {
            fract.isPos = true
            fract.w *=  -1
            fract.x *=  -1
        }
        //value WAS positive, make negative
        else if (fract.w > 0 || fract.x > 0) {
            fract.isPos = false
            fract.x *=  -1
            fract.w *=  -1
        }

        return fract
    }

    //Fraction * Fraction
    override fun times(other: FractionOperator): FractionOperator {
        var fract = this
        val fract2 = other as KlosinmFraction

        //see if value is negative or not
        if (fract.w < 0 && fract2.w < 0)
            fract.isPos = true
        else if (fract.x < 0 || fract.w < 0 || fract2.w < 0)
            fract.isPos = false
        else
            fract.isPos = true

        //new numerator
        fract.x = (fract.makeImproper() * fract2.makeImproper()).absoluteValue
        //new denominator
        fract.y = fract2.y * fract.y
        fract.w = 0

        fract.reduce()
        fract.makeProper()

        return fract
    }

    //Fraction * int
    override fun times(other: Int): FractionOperator {
        var fract = this
        if (other < 0 || fract.w < 0 || fract.x < 0)
            fract.isPos = false
        if ((other < 0 && fract.w < 0) || other == 0 || (other < 0 && fract.x < 0) || (other >= 0 && fract.w >= 0 && fract.x >= 0))
            fract.isPos = true
        else
            fract.isPos = false

        fract.w = (fract.w * other).absoluteValue
        fract.x = (fract.x * other).absoluteValue
        fract.y = fract.y.absoluteValue
        fract.reduce()
        fract.makeProper()

        return fract
    }

    //Fraction <,=,> Fraction
    override fun compareTo(other: FractionOperator): Int {
        var fract = this
        val fract2 = other as KlosinmFraction

        //make fractions improper and  have same denominator
        var impnum1 = fract.makeImproper() * fract2.denominator()
        var impnum2 = fract2.makeImproper() * fract.y

        // less than
        if (impnum1.compareTo(impnum2) < 0)
            return -1
        // equals
        else if (impnum1.equals(impnum2))
            return 0
        // greater than
        else if (impnum1.compareTo(impnum2) > 0)
            return 1
        else
            return -1
    }

    override fun equals(other: Any?): Boolean {
        other as FractionOperator
        var temp = compareTo(other)
        if (temp == 0) {
            return true
        } else {
            return false
        }
    }

    /**_____________________________________________________________________**/
    override fun get(pos: Int): Optional<Int> {
        if (pos == 0) {
            if ((w) != 0) {
                return Optional.of(w)
            } else {
                return Optional.empty()
            }
        } else if (pos == 1) {
            if ((x) != 0) {
                return Optional.of(x)
            } else {
                return Optional.empty()
            }
        } else if (pos == 2) {
            if (y != 1) {
                return Optional.of(y)
            } else {
                return Optional.empty()
            }
        } else {
            return Optional.empty()
        }
    }

    override fun invoke(len: Int): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isRepeating(): Optional<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}