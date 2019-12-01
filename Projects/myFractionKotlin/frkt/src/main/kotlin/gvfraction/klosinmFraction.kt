package gvfraction
import gvfraction.FractionOperator
import java.util.*
import kotlin.math.absoluteValue
/*_________________
Monica Klosin
Dec 1 2019
Fraction Maker - Kotlin
__________________*/
class KlosinmFraction() : FractionOperator{

    //default Constructor
         var w: Int = 0
         var x: Int = 0
         var y: Int = 1
         var isPos: Boolean = true

    //constructor that initializes the whole number of the fraction
    constructor(a: Int) : this(){
        w = a
        x = 0
        y = 1
        if (w < 0)
        {
            this.isPos = false
        }
        else
        {
            this.isPos = true
        }
    }

    constructor(s: String): this(){
        //split values by spaces and "/" into array
        val number = s.split(" ","/").toTypedArray()

        //if size 3, then w x/y
        if(number.size == 3){
            this.w = number[0].toInt()
            this.x = number[1].toInt()
            this.y = number[2].toInt()
        }
        //if size 2, then x/y
        else if(number.size == 2){
            this.w = 0
            this.x = number[0].toInt()
            this.y = number[1].toInt()
        }
        //if size 1. then w
        else if(number.size == 1){
            this.w = number[0].toInt()
            this.x = 0
            this.y = 1
        }
        else{
            throw IllegalArgumentException("Not valid input!")
        }

        //divide by 0 error
        if (this.y == 0)
        {
            throw IllegalArgumentException("Can't divide by 0!")
        }

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

        if( this.x < this.y){
            return
        }
        else if(this.x > this.y){
            num = this.x.rem(this.y)
            whole = this.w + (this.x-num)/this.y

            this.x = num
            this.w = whole
            if ( this.x == 0){
                this.y = 1
            }
        }
        else if (this.x == this.y){
            this.x = 0
            this.y = 1
            this.w = 1
        }

    }
    override fun isProper(): Boolean {
        if (((this.x).compareTo(this.y) > 0) || this.x == 0){
            return false
        }
        else{
            return true
        }
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
        {
            return (b).absoluteValue
        }
        else
        {
            return gcf(b, a.rem(b))
        }
    }
    //least common mutiple
    fun lcm(a: Int, b: Int): Int {
        return (a / gcf(a, b)) * b
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
        var dem1: Int = fract.y
        var dem2: Int = other.denominator()
        var num1: Int
        var num2: Int
        //see if fraction 2 is negative or not
        var negVal2: Int
        if(other.isPositive() == true){
            negVal2 = 1
        }
        else{
            negVal2 = -1
        }

        //finding consistent denominator
        var lcd: Int = lcm(dem1, dem2)

        //making fractions improper
        if (fract.w < 0)
        {
              num1 = ((fract.w * fract.y) - fract.x)
        }
        else {
              num1 = ((fract.w * fract.y) + fract.x)
        }
        if (other.whole() < 0)
        {
             num2 = ((other.whole() * other.denominator()) - other.numerator())
        }
        else {
             num2 = ((other.whole() * other.denominator() + other.numerator()))
        }

        //new numerator
         var numerator1: Int = (lcd / dem1) * num1
         var numerator2: Int = (lcd / dem2) * num2 * negVal2

        //New numerator
        var sumNum: Int = numerator2 + numerator1

        if (sumNum < 0 || fract.w < 0)
        {
            fract.isPos = false
        }
        else
        {
            fract.isPos = true
        }
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
        //make fraction improper
        var num1 = 0
        if (fract.w < 0) {
            num1 = fract.w * fract.y - fract.x
        } else if (fract.w >= 0) {
            num1 = fract.w * fract.y + fract.x
        }

        val valNum = (other * fract.y)
        val newNum = num1 + valNum

        val Positive = (fract.x + fract.w * fract.y) + (other * fract.y)
        if (Positive < 0) {
            fract.isPos = false
        } else {
            fract.isPos = true
        }

        fract.w = 0
        fract.x = newNum.absoluteValue

        fract.makeProper()
        fract.reduce()
        return fract
    }

    //Fraction - Fraction
    override fun minus(other: FractionOperator): FractionOperator {
        var fract = this
        var fract2 = other
        //see if other value is negative
        var negVal2: Int
        if(other.isPositive() == true){
            negVal2 = 1
        }
        else{
            negVal2 = -1
        }

        //make fractions  improper
        var imp1: Int
        var imp2: Int
        if(fract.w != 0) {
            imp1 = fract.x.absoluteValue + fract.w.absoluteValue * fract.y
        }
        else{
            imp1 = fract.x
        }
        if(fract2.whole() != 0) {
            imp2 = fract2.numerator() + fract2.whole() * fract2.denominator()
        }
        else{
            imp2 = fract2.numerator() * negVal2
        }

        //new  numerator
        val num1: Int = imp2 * fract.y
        val num2: Int = imp1 * fract2.denominator()

        val sumNum = num2 - num1

        if (sumNum == 0 || ((fract.w - (other.whole()*negVal2)) >= 0)) {
            fract.isPos = true
        }
        else if (fract.w < 0 || sumNum < 0) {
            fract.isPos = false
        }
        else {
            fract.isPos = true
        }
        //new demoniator
        val dem: Int = fract.y * fract2.denominator()

        //New whole
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

        //make fraction improper
        var num1: Int
        if(fract.w != 0) {
            num1 = fract.x.absoluteValue + fract.w.absoluteValue * fract.y
        }
        else{
            num1 = fract.x
        }

        var valNum: Int = other  * fract.y
        var newNum: Int = num1.absoluteValue - valNum.absoluteValue

        var newNumBool: Int = num1 - valNum
        if (newNumBool < 0)
        {
            fract.isPos = false
        }
        else
        {
            fract.isPos = true
        }
        fract.w = 0
        fract.x = newNum.absoluteValue
        fract.y = fract.y.absoluteValue

        fract.reduce()
        fract.makeProper()
        return fract
    }

    //change sign
    override fun unaryMinus(): FractionOperator {
        //value WAS negative, make pos
        var fract = this
        if (fract.w < 0 || fract.x < 0)
        {
            fract.isPos = true
            fract.w = fract.w * -1
            fract.x = fract.x * -1
        }
        //value WAS positive, make negative
        else if (fract.w > 0 || fract.x > 0)
        {
            fract.isPos = false
            fract.x = fract.x * -1
            fract.w = fract.w * -1
        }
        return (fract)
    }

    //Fraction * Fraction
    override fun times(other: FractionOperator): FractionOperator {
        var fract = this
        var fract2 = other
        //see if fract2 is negative
        var negVal2: Int
        if(other.isPositive() == true){
            negVal2 = 1
        }
        else{
            negVal2 = -1
        }

        if (fract.w < 0 && negVal2 < 0) {
            fract.isPos = true
        } else if (fract.x < 0 || fract.w < 0 || negVal2 < 0 ) {
            fract.isPos = false
        } else {
            fract.isPos = true
        }

        //make fractions improper
        var num1: Int
        var num2: Int
        if(fract.w != 0) {
            num1 = fract.x.absoluteValue + fract.w.absoluteValue * fract.y
        }
        else{
            num1 = fract.x
        }
        if(fract2.whole() != 0) {
            num2 = fract2.numerator() + fract2.whole() * fract2.denominator()
        }
        else{
            num2 = fract2.numerator() * negVal2
        }

        //finding  numerator
        val num = num1 * num2
        //finding  denominator
        val dem: Int = fract2.denominator() * fract.y

        //Return Value
        fract.x = num
        fract.y = dem
        fract.w = 0

        fract.reduce()
        fract.makeProper()
        return fract
    }

    //Fraction * int
    override fun times(other: Int): FractionOperator {
        var fract = this
        if (other < 0 || fract.w < 0 || fract.x < 0) {
            fract.isPos = false
        }
        if ((other < 0 && fract.w < 0) || other == 0 || (other < 0 && fract.x < 0) || (other >= 0 && fract.w >= 0 && fract.x >= 0)) {
            fract.isPos = true
        }
        else {
            fract.isPos = false
        }
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
        var fract2 = other
        var num1: Int
        var num2: Int

        //if fract2 is negative
        var negVal2: Int
        if(other.isPositive() == true){
            negVal2 = 1
        }
        else{
            negVal2 = -1
        }

        //making fractions improper
        if (fract.w < 0)
        {
            num1 = ((fract.w * fract.y) - fract.x)
        }
        else {
            num1 = ((fract.w * fract.y) + fract.x)
        }
        if (other.whole() < 0)
        {
            num2 = ((other.whole() * other.denominator()) - other.numerator())
            num2 = num2 * negVal2
        }
        else {
            num2 = ((other.whole() * other.denominator() + other.numerator()))
            num2 = num2 * negVal2
        }

        //make improper to compare numerators
        var impnum1 = num1* fract2.denominator()
        var impnum2 = num2 * fract.y

       /* System.out.println("F<F fract.w: " + fract.w)
        System.out.println("F<F fract.x: " + fract.x)
        System.out.println("F<F fract.y: " + fract.y)
        System.out.println("  " )
        System.out.println("F<F fract2.whole(): " + fract2.whole())
        System.out.println("F<F fract2.numerator(): " + fract2.numerator())
        System.out.println("F<F fract2.denominator(): " + fract2.denominator())
        System.out.println("  " )
        System.out.println("F<F num1: " + num1)
        System.out.println("F<F num2: " + num2)
        System.out.println("  " )
        System.out.println("F<F impnum1: " + impnum1)
        System.out.println("F<F impnum2: " + impnum2)
        System.out.println(" ")
        System.out.println("________________________ ")*/

        //num1 < num2 aka less than
        if (impnum1.compareTo(impnum2) < 0) {
            return -1
        }
        //equals
        if (impnum1.equals(impnum2)){
            return 0
        }
        //num1 > num2 aka greater than
        else if(impnum1.compareTo(impnum2) > 0){
            return 1

        }
        else{
            return -1

        }
    }

    override fun equals(other: Any?): Boolean{
        other as FractionOperator
        var fract = this
        var temp = compareTo(other)
        if(temp == 0){
            return true
        }
        else{
            return false
        }
    }

    /**_____________________________________________________________________**/
    override fun get(pos: Int): Optional<Int> {
        if (pos == 0){
            if((w) != 0){
                return Optional.of(w)
            }
            else{
                return Optional.empty()
            }
        }
        else if (pos == 1){
            if ((x) != 0)
            {
                return Optional.of(x)
            }
            else
            {
                return Optional.empty()
            }
        }
        else if (pos == 2)
        {
            if (y != 1)
            {
                return Optional.of(y)
            }
            else
            {
                return Optional.empty()
            }
        }
        else{
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
