package gvfraction
import gvfraction.FractionOperator
import java.util.*
import kotlin.math.absoluteValue

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

        if ( ((this.x).compareTo(this.y) > 0) || this.x == 0){
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
        var num1: Int = 0
        var num2: Int = 0

        var negVal2: Int = 0
        if(other.isPositive() == true){
            negVal2 = 1
        }
        else{
            negVal2 = -1
        }
        //finding consistent denominator
        var lcd: Int = lcm(dem1, dem2)

        //making fract improper
        if (fract.w < 0)
        {
              num1 = ((fract.w * fract.y) - fract.x)
        }
        else if (fract.w >= 0)
        {
              num1 = ((fract.w * fract.y) + fract.x)
        }
        /*System.out.println("this.w = " + fract.w)
        System.out.println("other.whole() = " + other.whole()*negVal2)*/
        //making fract2 improper
        if (other.whole() < 0)
        {
             num2 = ((other.whole() * other.denominator()) - other.numerator())
        }
        else if (other.whole() >= 0)
        {
             num2 = ((other.whole() * other.denominator() + other.numerator()))
        }
       /* System.out.println("num1 = " + num1)
        System.out.println("num2 = " + num2)*/
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

        fract.x = sumNum.absoluteValue
        fract.y = lcd
        fract.w = 0
       /* System.out.println("Numerator1 = " + numerator1)
        System.out.println("Numerator2 = " + numerator2)
        System.out.println("Sum Num/ x value = " + sumNum)
        System.out.println("lcd/ y value= " + lcd)
        System.out.println("Fract.w = " + fract.w )*/
        fract.reduce()
        /*System.out.println("F+F Fract w after reduce = " + fract.w)
        System.out.println("F+F Fract x after reduce = " + fract.x)
        System.out.println("F+F Fract y after reduce = " + fract.y)*/

        fract.makeProper()
        /*System.out.println("F+F Fract w after proper = " + fract.w)
        System.out.println("F+F Fract x after proper = " + fract.x)
        System.out.println("F+F Fract y after proper = " + fract.y)*/

        return fract
    }

    //Fraction + int
    override fun plus(num: Int): FractionOperator {
        var fract = this
        //make fraction a improper
        var num1 = 0
        if (fract.w < 0) {
            num1 = fract.w * fract.y - fract.x
        } else if (fract.w >= 0) {
            num1 = fract.w * fract.y + fract.x
        }
        val valNum = (num * fract.y)
        val newNum = num1 + valNum

        val Positive = (fract.x + fract.w * fract.y) + (num * fract.y)
       /* System.out.println("Improper numerator: " + num1)
        System.out.println("Number numerator: " + valNum)
        System.out.println("Sum of numerator: " + newNum)*/
        if (Positive < 0) {
            fract.isPos = false
        } else {
            fract.isPos = true
        }
        fract.w = 0
        fract.x = newNum.absoluteValue
       /* System.out.println("Fract w before  = " + fract.w)
        System.out.println("Fract x before = " + fract.x)
        System.out.println("Fract y before = " + fract.y)*/
        fract.makeProper()
      /*  System.out.println("Fract w after proper = " + fract.w)
        System.out.println("Fract x after proper = " + fract.x)
        System.out.println("Fract y after proper = " + fract.y)*/
        fract.reduce()
         /*System.out.println("Fract w after reduce = " + fract.w)
         System.out.println("Fract x after reduce = " + fract.x)
         System.out.println("Fract y after reduce = " + fract.y)*/
        return fract
    }

    //Fraction - Fraction
    override fun minus(other: FractionOperator): FractionOperator {
        var fract = this
        var fract2 = other
        //see if other value is negative
        var negVal2: Int = 0
        if(other.isPositive() == true){
            negVal2 = 1
        }
        else{
            negVal2 = -1
        }
        //make improper
        val imp1: Int = (fract.y * fract.w).absoluteValue + (fract.x).absoluteValue
        val imp2: Int = (fract2.denominator() * fract2.whole()) + (fract2.numerator())

        //new  numerator
        val num1: Int = imp2 * fract.y
        val num2: Int = imp1 * fract2.denominator()
1
        val sumNum = num2 - num1
       /* System.out.println("imp1= " + imp1)
        System.out.println("imp2= " + imp2)
        System.out.println("num1= " + num1)
        System.out.println("num2= " + num2)
        System.out.println("sumNum= " + sumNum)
        System.out.println("F-F Fract w before checking isPos = " + fract.w)
        System.out.println("F-F negVal2  before checking isPos = " + negVal2)
        System.out.println("F-F Fract  = " + fract.w)
        System.out.println("F-F other.whole()*negVal2  = " + other.whole()*negVal2)
        System.out.println("fract.y - other.whole*neg = " + (fract.w - (other.whole()*negVal2)))*/
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
    override fun minus(number: Int): FractionOperator {
        var fract = this

        //make fraction a improper
        var num1: Int = fract.x + (fract.w * fract.y)
        var valNum: Int = number * fract.y

        var newNum: Int = num1 - valNum

        if (newNum < 0)
        {
            fract.isPos = false
        }
        else
        {
            fract.isPos = true
        }
        fract.w = 0
        fract.x = newNum
        fract.w = fract.w.absoluteValue
        fract.x = fract.x.absoluteValue
        fract.y = fract.y.absoluteValue
        System.out.println("Fract.w = " + fract.w )
        fract.reduce()
        System.out.println("F-i Fract w after reduce = " + fract.w)
        System.out.println("F-i Fract x after reduce = " + fract.x)
        System.out.println("F-i Fract y after reduce = " + fract.y)

        fract.makeProper()
        System.out.println("F-i Fract w after proper = " + fract.w)
        System.out.println("F-i Fract x after proper = " + fract.x)
        System.out.println("F-i Fract y after proper = " + fract.y)
        return fract
    }


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
        var negVal2: Int = 0
        if(other.isPositive() == true){
            negVal2 = 1
        }
        else{
            negVal2 = -1
        }
        System.out.println("this.w = " + fract.w)
        System.out.println("other.whole() = " + other.whole()*negVal2)

        if (fract.w < 0 && negVal2 < 0) {
            fract.isPos = true
        } else if (fract.x < 0 || fract.w < 0 || negVal2 < 0 ) {
            fract.isPos = false
        } else {
            fract.isPos = true
        }

        //make fraction a improper

        val num1: Int = (fract.x).absoluteValue + (fract.w * fract.y).absoluteValue
        val num2: Int = (fract2.numerator()).absoluteValue + (fract2.whole() * fract2.denominator()).absoluteValue

        val dem: Int = fract2.denominator() * fract.y //finding  denominator

        val num = num1 * num2 //finding  numerator
        //Return Value
        fract.x = num
        fract.y = dem
        fract.w = 0
        System.out.println("Fract.w = " + fract.w )
        System.out.println("num1 = " + num1)
        System.out.println("num2 = " + num2)
        System.out.println("num = " + num)
        System.out.println("dem = " + dem)

        fract.reduce()
        System.out.println("F*F Fract w after reduce = " + fract.w)
        System.out.println("F*F Fract x after reduce = " + fract.x)
        System.out.println("F*F Fract y after reduce = " + fract.y)

        fract.makeProper()
        System.out.println("F*F Fract w after proper = " + fract.w)
        System.out.println("F*F Fract x after proper = " + fract.x)
        System.out.println("F*F Fract y after proper = " + fract.y)
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
        fract.w = fract.w * other
        fract.x = fract.x * other

        fract.w = fract.w.absoluteValue
        fract.x = fract.x.absoluteValue
        fract.y = fract.y.absoluteValue
        fract.reduce()
        fract.makeProper()
        return fract
    }

    //Fraction < Fraction ?
    override fun compareTo(other: FractionOperator): Int {
        var fract = this
        var fract2 = other
        //if fract2 is negative
        var negVal2: Int = 0
        if(other.isPositive() == true){
            negVal2 = 1
        }
        else{
            negVal2 = -1
        }
        var num1: Int = fract.x + fract.w * fract.y * fract2.denominator()
        var num2: Int = fract2.numerator() + fract2.whole() * fract2.denominator() * negVal2 * fract.y

        fract.x = num1
       // fract2.numerator() = num2

        fract.reduce()
        fract2.reduce()
        //finding consistent denominator
        val lcd = lcm(fract.denominator(), fract2.denominator())

        //make numerators match new denominator
        num1 = lcd / fract.y * num1
        num2 = lcd / fract2.denominator() * num2

        if (num1.compareTo(num2) < 0) {
            return 1 //true
        } else {
            return -1 //false
        }
    }

    /**_____________________________________________________________________**/
    override fun get(pos: Int): Optional<Int> {
       /* var NA: Int? = null
        if (pos == 0){
            if(w != 0){
                return w
            }
            else{
                return NA
            }
        }
        else if (pos == 1){
            if (x != 0)
            {
                return x
            }
            else
            {
                return NA
            }
        }
        else if (pos == 2)
        {
            if (y != 1)
            {
                return y
            }
            else
            {
                return NA
            }
        }
        else{
            return NA
        }*/
        TODO("not implemented")
    }

    override fun invoke(len: Int): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isRepeating(): Optional<String> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
