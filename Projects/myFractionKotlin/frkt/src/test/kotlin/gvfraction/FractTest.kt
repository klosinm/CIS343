/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package gvfraction


import java.lang.IllegalArgumentException
import kotlin.test.*

class FractTest {
    var factory = FractionFactory.getInstance()

    init {
        // TODO register the class that implements
        // the FractionOperator interface
        factory.registerClass(KlosinmFraction::class)
    }

    @Test
    fun defaultConstructorInitializesCorrectly() {
        val a = factory.create()
        assertNotNull(a)
        assertEquals(0, a.whole())
        assertEquals(0, a.numerator())
        assertEquals(1, a.denominator())
        assertTrue(a.isPositive())
    }

    @Test
    fun constructorFromWholeNumberInitializesCorrectly() {
        val a = factory.create(7)
        assertNotNull(a)
        assertEquals(7, a.whole())
        assertEquals(0, a.numerator())
        assertEquals(1, a.denominator())
        assertTrue(a.isPositive())
    }

    @Test
    fun constructorFromNegativeWholeNumberInitializesCorrectly() {
        val a = factory.create(-7)
        assertNotNull(a)
        assertEquals(7, a.whole())
        assertEquals(0, a.numerator())
        assertEquals(1, a.denominator())
        assertFalse(a.isPositive())
    }

    @Test
    fun constructorFromStringPositiveWholeNumber() {
        val a = factory.create("45")
        assertNotNull(a)
        assertEquals(45, a.whole())
        assertEquals(0, a.numerator())
        assertEquals(1, a.denominator())
        assertTrue(a.isPositive())
    }

    @Test
    fun constructorFromStringPositivePureFraction() {
        val a = factory.create("23/79")
        assertNotNull(a)
        assertEquals(0, a.whole())
        assertEquals(23, a.numerator())
        assertEquals(79, a.denominator())
        assertTrue(a.isPositive())
    }

    @Test
    fun constructorFromStringPositiveMixedFraction() {
        val a = factory.create("12 45/31")
        assertNotNull(a)
        assertEquals(12, a.whole())
        assertEquals(45, a.numerator())
        assertEquals(31, a.denominator())
        assertTrue(a.isPositive())
    }

    @Test
    fun constructorFromStringNegativeWholeNumber() {
        val a = factory.create("-45")
        assertNotNull(a)
        assertEquals(45, a.whole())
        assertEquals(0, a.numerator())
        assertEquals(1, a.denominator())
        assertFalse(a.isPositive())
    }

    @Test
    fun constructorFromStringNegativePureFraction() {
        val a = factory.create("-23/79")
        assertNotNull(a)
        assertEquals(0, a.whole())
        assertEquals(23, a.numerator())
        assertEquals(79, a.denominator())
        assertFalse(a.isPositive())
    }

    @Test
    fun constructorFromStringMNegativeMixedFraction() {
        val a = factory.create("-12 45/31")
        assertNotNull(a)
        assertEquals(12, a.whole())
        assertEquals(45, a.numerator())
        assertEquals(31, a.denominator())
        assertFalse(a.isPositive())
    }

    @Test
    fun constructorFromStringPosPureFractionZeroDenominator() {
        assertFailsWith(IllegalArgumentException::class) {
            factory.create("15/0")
        }
    }

    @Test
    fun constructorFromStringPosMixedFractionZeroDenominator() {
        assertFailsWith(IllegalArgumentException::class) {
            factory.create("34 15/0")
        }
    }

    @Test
    fun constructorFromStringNegPureFractionZeroDenominator() {
        assertFailsWith(IllegalArgumentException::class) {
            factory.create("-15/0")
        }
    }

    @Test
    fun constructorFromStringNegMixedFractionZeroDenominator() {
        assertFailsWith(IllegalArgumentException::class) {
            factory.create("-34 15/0")
        }
    }

    @Test
    fun properOnWholeNumber() {
        val a = factory.create(2);
        assertFalse(a.isProper())
    }

    @Test
    fun makeProperDoesNotConvertToProper() {
        val a = factory.create("3 12/3")
        a.makeProper()
        assertFalse(a.isProper())
    }

    @Test
    fun makeProperOnWholeNumber() {
        val a = factory.create(10);
        a.makeProper()

        assertFalse(a.isProper())
        assertEquals(10, a.whole())
        assertEquals(0, a.numerator())
        assertEquals(1, a.denominator())
        assertTrue(a.isPositive())
    }

    @Test
    fun makeProperIntoWholeNumber() {
        val a = factory.create("400/25");
        a.makeProper()
        assertFalse(a.isProper())
        assertEquals(16, a.whole())
        assertEquals(0, a.numerator())
        assertEquals(1, a.denominator())
        assertTrue(a.isPositive())
    }

    @Test
    fun makeProperShouldNotAlterProperFraction() {
        val a = factory.create("6/17")
        a.makeProper()
        assertTrue(a.isPositive())
        assertEquals(0, a.whole())
        assertEquals(6, a.numerator())
        assertEquals(17, a.denominator())
        assertTrue(a.isProper())
    }

    @Test
    fun makeProperShouldAlterImproperFraction() {
        val a = factory.create("37/12")
        a.makeProper()
        assertTrue(a.isPositive())
        assertEquals(3, a.whole())
        assertEquals(1, a.numerator())
        assertEquals(12, a.denominator())
        assertTrue(a.isProper())
    }

    @Test
    fun toProperOnProperFractionShallNotAlterData() {
        val test = factory.create("13/79")
        val out = test.toProper()
        assertTrue(out.isPositive())
        assertEquals(0, out.whole())
        assertEquals(13, out.numerator())
        assertEquals(79, out.denominator())
    }

    @Test
    fun toProperImproperFractionShallAlterData() {
        val test = factory.create("131/12")
        val out = test.toProper()
        assertTrue(out.isPositive())
        assertEquals(10, out.whole())
        assertEquals(11, out.numerator())
        assertEquals(12, out.denominator())
    }

    @Test
    fun checkIfReducedPos() {
        val a = factory.create("15/35")
        val b = factory.create("15/35")
        assertFalse(a.isReduced())
        assertFalse(b.isReduced())
        assertTrue(a.isPositive())
        assertTrue(b.isPositive())
    }

    @Test
    fun checkIfReducedNeg() {
        val a = factory.create("-15/35")
        val b = factory.create("-15/35")
        assertFalse(a.isReduced())
        assertFalse(a.isReduced())
        assertFalse(a.isPositive())
        assertFalse(b.isPositive())
    }

    @Test
    fun reduceWhenFractionHasCommonFactorPos() {
        val a = factory.create("270/390")
        a.reduce()

        assertTrue(a.isPositive())
        assertTrue(a.isReduced())
        assertEquals(9, a.numerator())
        assertEquals(13, a.denominator())
    }

    @Test
    fun reduceWhenFractionHasCommonFactorNeg() {
        val a = factory.create("-270/390")
        a.reduce()

        assertFalse(a.isPositive())
        assertTrue(a.isReduced())
        assertEquals(9, a.numerator())
        assertEquals(13, a.denominator())
    }

    @Test
    fun reduceWhenFractionWithRelativePrimPos() {
        val a = factory.create("270/391")
        a.reduce()

        assertTrue(a.isPositive())
        assertTrue(a.isReduced())
        assertEquals(270, a.numerator())
        assertEquals(391, a.denominator())
    }

    @Test
    fun reduceWhenFractionWithRelativePrimNeg() {
        val a = factory.create("-270/391")
        a.reduce()

        assertFalse(a.isPositive())
        assertTrue(a.isReduced())
        assertEquals(270, a.numerator())
        assertEquals(391, a.denominator())
    }


    @Test
    fun addPositiveFractionAndPositiveInt() {
        val a = factory.create("2 6/13")
        val b = a + 8
        assertTrue(b.isPositive())
        assertTrue(b.isReduced())
        assertTrue(b.isProper())
        assertEquals(10, b.whole())
        assertEquals(6, b.numerator())
        assertEquals(13, b.denominator())
    }

    @Test
    fun addPositiveFractionAndNegativeInt() {
        val a = factory.create("2 6/13")
        val b = a + (-2)
        assertTrue(b.isPositive())
        assertTrue(b.isReduced())
        assertTrue(b.isProper())
        assertEquals(0, b.whole())
        assertEquals(6, b.numerator())
        assertEquals(13, b.denominator())
    }

    @Test
    fun addNegativeFractionAndPositiveInt() {
        val a = factory.create("-2 6/13")
        val b = a + 8
        assertTrue(b.isPositive())
        assertTrue(b.isReduced())
        assertTrue(b.isProper())
        assertEquals(5, b.whole())
        assertEquals(7, b.numerator())
        assertEquals(13, b.denominator())
    }

    @Test
    fun addNegativeFractionAndNegativeInt() {
        val a = factory.create("-2 6/13")
        val b = a + (-8)
        assertFalse(b.isPositive())
        assertTrue(b.isReduced())
        assertTrue(b.isProper())
        assertEquals(10, b.whole())
        assertEquals(6, b.numerator())
        assertEquals(13, b.denominator())
    }

    @Test
    fun addPositiveIntAndPosFraction() {
        val a = factory.create("2 6/13")
        val b = 15 + a
        assertTrue(b.isPositive())
        assertTrue(b.isReduced())
        assertTrue(b.isProper())
        assertEquals(17, b.whole())
        assertEquals(6, b.numerator())
        assertEquals(13, b.denominator())
    }

    @Test
    fun addNegIntAndPosFraction() {
        val a = factory.create("2 6/13")
        val b = -14 + a
        assertFalse(b.isPositive())
        assertTrue(b.isReduced())
        assertTrue(b.isProper())
        assertEquals(11, b.whole())
        assertEquals(7, b.numerator())
        assertEquals(13, b.denominator())
    }

    @Test
    fun addPositiveIntAndNegFraction() {
        val a = factory.create("-10 8/9")
        val b = 5 + a // 45/9 - 98/9 = -53/9
        assertFalse(b.isPositive())
        assertTrue(b.isReduced())
        assertTrue(b.isProper())
        assertEquals(5, b.whole())
        assertEquals(8, b.numerator())
        assertEquals(9, b.denominator())
    }

    @Test
    fun addNegIntAndNegFraction() {
        val a = factory.create("-8 13/14")
        val b = -11 + a // -154/14 - 125/14 = -279/14
        assertFalse(b.isPositive())
        assertTrue(b.isReduced())
        assertTrue(b.isProper())
        assertEquals(19, b.whole())
        assertEquals(13, b.numerator())
        assertEquals(14, b.denominator())
    }

    @Test
    fun addTwoPosFractions() {
        val a = factory.create("21 5/6")
        val b = factory.create("9 3/4")
        val sum = a + b
        assertTrue(sum.isPositive())
        assertTrue(sum.isReduced())
        assertTrue(sum.isProper())
        assertEquals(31, sum.whole())
        assertEquals(7, sum.numerator())
        assertEquals(12, sum.denominator())
    }

    @Test
    fun addTwoNegFractions() {
        val a = factory.create("-17 5/8")
        val b = factory.create("-3 3/14")
        val sum = a + b
        assertFalse(sum.isPositive())
        assertTrue(sum.isReduced())
        assertTrue(sum.isProper())
        assertEquals(20, sum.whole())
        assertEquals(47, sum.numerator())
        assertEquals(56, sum.denominator())
    }

    @Test
    fun addTwoFractsOfDifferentSignNegSum() {
        val a = factory.create("-10 5/7")
        val b = factory.create("5 3/14")
        val sum = a + b
        assertFalse(sum.isPositive())
        assertTrue(sum.isReduced())
        assertTrue(sum.isProper())
        assertEquals(5, sum.whole())
        assertEquals(1, sum.numerator())
        assertEquals(2, sum.denominator())
    }

    @Test
    fun addTwoFractsOfDifferentSignPosSum() {
        val a = factory.create("11 5/7")
        val b = factory.create("-9 3/14")
        val sum = a + b
        assertTrue(sum.isPositive())
        assertTrue(sum.isReduced())
        assertTrue(sum.isProper())
        assertEquals(2, sum.whole())
        assertEquals(1, sum.numerator())
        assertEquals(2, sum.denominator())
    }

    @Test
    fun subtractIntFromFraction() {
        val one = factory.create("2 6/13")
        val sum = one - 5

        assertFalse(sum.isPositive())
        assertTrue(sum.isReduced())
        assertTrue(sum.isProper())
        assertEquals(2, sum.whole())
        assertEquals(7, sum.numerator())
        assertEquals(13, sum.denominator())
    }

    @Test
    fun subtractFractFromInt() {
        val one = factory.create("2 6/13")
        val sum = 15 - one

        assertTrue(sum.isPositive())
        assertTrue(sum.isReduced())
        assertTrue(sum.isProper())
        assertEquals(12, sum.whole())
        assertEquals(7, sum.numerator())
        assertEquals(13, sum.denominator())
    }

    @Test
    fun subtractTwoPosFractsPosResult() {
        val a = factory.create("33 5/6")
        val b = factory.create("9 3/4")
        val sum = a - b

        assertTrue(sum.isPositive())
        assertTrue(sum.isReduced())
        assertTrue(sum.isProper())
        assertEquals(24, sum.whole())
        assertEquals(1, sum.numerator())
        assertEquals(12, sum.denominator())
    }

    @Test
    fun subtractTwoPosFractsNegResult() {
        val a = factory.create("33 5/6")
        val b = factory.create("9 3/4")
        val sum = b - a

        assertFalse(sum.isPositive())
        assertTrue(sum.isReduced())
        assertTrue(sum.isProper())
        assertEquals(24, sum.whole())
        assertEquals(1, sum.numerator())
        assertEquals(12, sum.denominator())
    }

    @Test
    fun subtractTwoNegFractsNegResult() {
        val a = factory.create("-17 5/8")
        val b = factory.create("-3 3/14")
        val sum = a - b

        assertFalse(sum.isPositive())
        assertTrue(sum.isReduced())
        assertTrue(sum.isProper())
        assertEquals(14, sum.whole())
        assertEquals(23, sum.numerator())
        assertEquals(56, sum.denominator())
    }

    @Test
    fun subtractTwoNegFractsPosResult() {
        val a = factory.create("-17 5/8")
        val b = factory.create("-3 3/14")
        val sum = b - a

        assertTrue(sum.isPositive())
        assertTrue(sum.isReduced())
        assertTrue(sum.isProper())
        assertEquals(14, sum.whole())
        assertEquals(23, sum.numerator())
        assertEquals(56, sum.denominator())
    }

    @Test
    fun subtractToZero() {
        val a = factory.create("-17 5/8")
        val sum = a - a

        assertTrue(sum.isPositive())
        assertTrue(sum.isReduced())
        assertFalse(sum.isProper())
        assertEquals(0, sum.whole())
        assertEquals(0, sum.numerator())
        assertEquals(1, sum.denominator())
    }

    @Test
    fun unaryMinusPositiveWholeNumber() {
        val a = factory.create("517")
        val b = -a
        assertFalse(b.isPositive())
        assertEquals(517, b.whole())
        assertEquals(0, b.numerator())
        assertEquals(1, b.denominator())
    }

    @Test
    fun unaryMinusNegativeWholeNumber() {
        val a = factory.create("-517")
        val b = -a
        assertTrue(b.isPositive())
        assertEquals(517, b.whole())
        assertEquals(0, b.numerator())
        assertEquals(1, b.denominator())
    }

    @Test
    fun unaryMinusPositivePureFraction() {
        val a = factory.create("51/73")
        val b = -a
        assertFalse(b.isPositive())
        assertEquals(0, b.whole())
        assertEquals(51, b.numerator())
        assertEquals(73, b.denominator())
    }

    @Test
    fun unaryMinusNegativePureFraction() {
        val a = factory.create("-51/73")
        val b = -a
        assertTrue(b.isPositive())
        assertEquals(0, b.whole())
        assertEquals(51, b.numerator())
        assertEquals(73, b.denominator())
    }

    @Test
    fun unaryMinusPositiveMixedFraction() {
        val a = factory.create("19 51/73")
        val b = -a
        assertFalse(b.isPositive())
        assertEquals(19, b.whole())
        assertEquals(51, b.numerator())
        assertEquals(73, b.denominator())
    }

    @Test
    fun unaryMinusNegativeMixedFraction() {
        val a = factory.create("-19 51/73")
        val b = -a
        assertTrue(b.isPositive())
        assertEquals(19, b.whole())
        assertEquals(51, b.numerator())
        assertEquals(73, b.denominator())
    }

    @Test
    fun multiplyPosFractionAndPosInt() {
        val a = factory.create("2 9/15")
        val prod = a * 4

        assertTrue(prod.isPositive())
        assertTrue(prod.isProper())
        assertTrue(prod.isReduced())
        assertEquals(10, prod.whole())
        assertEquals(2, prod.numerator())
        assertEquals(5, prod.denominator())
    }

    @Test
    fun multiplyPosFractionAndNegInt() {
        val a = factory.create("2 9/15")
        val prod = a * -4

        assertFalse(prod.isPositive())
        assertTrue(prod.isProper())
        assertTrue(prod.isReduced())
        assertEquals(10, prod.whole())
        assertEquals(2, prod.numerator())
        assertEquals(5, prod.denominator())
    }

    @Test
    fun multiplyNegFractionAndNegInt() {
        val a = factory.create("-2 9/15")
        val prod = a * -4

        assertTrue(prod.isPositive())
        assertTrue(prod.isProper())
        assertTrue(prod.isReduced())
        assertEquals(10, prod.whole())
        assertEquals(2, prod.numerator())
        assertEquals(5, prod.denominator())
    }


    @Test
    fun multiplyPosIntAndPosFraction() {
        val a = factory.create("2 9/15")
        val prod = 4 * a

        assertTrue(prod.isPositive())
        assertTrue(prod.isProper())
        assertTrue(prod.isReduced())
        assertEquals(10, prod.whole())
        assertEquals(2, prod.numerator())
        assertEquals(5, prod.denominator())
    }

    @Test
    fun multiplyNegIntAndPosFraction() {
        val a = factory.create("2 9/15")
        val prod = -4 * a

        assertFalse(prod.isPositive())
        assertTrue(prod.isProper())
        assertTrue(prod.isReduced())
        assertEquals(10, prod.whole())
        assertEquals(2, prod.numerator())
        assertEquals(5, prod.denominator())
    }

    @Test
    fun multiplyNegIntAndNegFraction() {
        val a = factory.create("-2 9/15")
        val prod = -4 * a

        assertTrue(prod.isPositive())
        assertTrue(prod.isProper())
        assertTrue(prod.isReduced())
        assertEquals(10, prod.whole())
        assertEquals(2, prod.numerator())
        assertEquals(5, prod.denominator())
    }

    @Test
    fun multiplyWithZero() {
        val a = factory.create("-2 9/15")
        val prod = a * 0

        assertTrue(prod.isPositive())
        assertFalse(prod.isProper())
        assertTrue(prod.isReduced())
        assertEquals(0, prod.whole())
        assertEquals(0, prod.numerator())
        assertEquals(1, prod.denominator())

    }

    @Test
    fun multiplyWithOne() {
        val a = factory.create("-2 9/15")
        val prod = a * 1

        assertFalse(prod.isPositive())
        assertTrue(prod.isProper())
        assertTrue(prod.isReduced())
        assertEquals(2, prod.whole())
        assertEquals(3, prod.numerator())
        assertEquals(5, prod.denominator())

    }

    @Test
    fun multiplyTwoPosFractions() {
        val a = factory.create("3 7/11")
        val b = factory.create("5 8/9")
        val prod = a * b

        assertTrue(prod.isPositive())
        assertTrue(prod.isProper())
        assertTrue(prod.isReduced())
        assertEquals(21, prod.whole())
        assertEquals(41, prod.numerator())
        assertEquals(99, prod.denominator())

    }

    @Test
    fun multiplyTwoNegFractions() {
        val a = factory.create("-3 7/11")
        val b = factory.create("-5 8/9")
        val prod = a * b

        assertTrue(prod.isPositive())
        assertTrue(prod.isProper())
        assertTrue(prod.isReduced())
        assertEquals(21, prod.whole())
        assertEquals(41, prod.numerator())
        assertEquals(99, prod.denominator())

    }

    @Test
    fun multiplyTwoFractionsOppositeSign() {
        val a = factory.create("-3 7/11")
        val b = factory.create("5 8/9")
        val prod = a * b

        assertFalse(prod.isPositive())
        assertTrue(prod.isProper())
        assertTrue(prod.isReduced())
        assertEquals(21, prod.whole())
        assertEquals(41, prod.numerator())
        assertEquals(99, prod.denominator())
    }

    @Test
    fun equalityCompareToSelf() {
        val a = factory.create("-4 7/8")
        assertTrue(a == a)
    }

    @Test
    fun nonEqualityPositive() {
        val a = factory.create("4 7/8")
        val b = factory.create("4 77/89")
        assertFalse(a == b)
    }

    @Test
    fun nonEqualityNegative() {
        val a = factory.create("-4 7/8")
        val b = factory.create("-4 77/89")
        assertFalse(a == b)
    }

    @Test
    fun nonEqualityFlipSign() {
        val a = factory.create("-4 7/8")
        val b = factory.create("4 77/88")
        assertFalse(a == b)

    }

    @Test
    fun equalityCompareToUnreducedPos() {
        val a = factory.create("4 7/8")
        val b = factory.create("4 77/88")
        assertTrue(a == b)
    }

    @Test
    fun equalityCompareToUnreducedNeg() {
        val a = factory.create("-4 7/8")
        val b = factory.create("-4 77/88")
        assertTrue(a == b)
    }

    @Test
    fun equalityCompareDifferentSign() {
        val a = factory.create("-4 7/8")
        val b = factory.create("6 3/10")
        assertFalse(a == b)
    }

    @Test
    fun lessThanDifferentSign() {
        val a = factory.create("-10 5/7")
        val b = factory.create("3 17/20")
        assertTrue(a < b) //
        assertFalse(b < a)
    }

    @Test
    fun lessThanPositiveFraction() {
        val a = factory.create("10 5/7")
        val b = factory.create("3 17/20")
        assertFalse(a < b)
        assertTrue(b < a)
    }

    @Test
    fun lessThanNegativeMixedAndPure() {
        val a = factory.create("-5/6")
        val b = factory.create("-1 17/20")
        assertFalse(a < b)
        assertTrue(b < a)
    }

    @Test
    fun lessThanPositiveMixedAndPure() {
        val a = factory.create("15/3")
        val b = factory.create("1 20/20")
        assertFalse(a < b)
        assertTrue(b < a)
    }

    @Test
    fun lookUpPositive_wholenumber() {
        val a = factory.create("4")

        assertTrue(a[0].isPresent())
        assertEquals(4,a[0].get())
        assertFalse(a[1].isPresent())
        assertFalse(a[2].isPresent())
    }

    @Test fun lookUpPositiveFraction() {
        val a = factory.create("4/19")

        assertFalse(a[0].isPresent());
        assertTrue(a[1].isPresent());
        assertEquals(4, a[1].get());
        assertTrue(a[2].isPresent());
        assertEquals(19,a[2].get());
    }

    @Test
    fun lookUpPositiveMmixedFractions() {
        val a = factory.create("21 4/19")

        assertTrue(a[0].isPresent());
        assertEquals(21,a[0].get());
        assertTrue(a[1].isPresent());
        assertEquals(4,a[1].get());
        assertTrue(a[2].isPresent());
        assertEquals(19,a[2].get());
    }

    @Test
    fun lookUpNegativeWholeNumber() {
        val a = factory.create("-4")

        assertTrue(a[0].isPresent());
        assertEquals(-4,a[0].get());
        assertFalse(a[1].isPresent());
        assertFalse(a[2].isPresent());
    }

    @Test
    fun lookUpNegativeFraction() {
        val a = factory.create("-4/19")

        assertFalse(a[0].isPresent());
        assertTrue(a[1].isPresent());
        assertEquals(-4,a[1].get());
        assertTrue(a[2].isPresent());
        assertEquals(19,a[2].get());
    }

    @Test
    fun lookUpPositiveMixedFraction() {
        val a = factory.create("-21 4/19")

        assertTrue(a[0].isPresent());
        assertEquals(-21, a[0].get());
        assertTrue(a[1].isPresent());
        assertEquals(4, a[1].get());
        assertTrue(a[2].isPresent());
        assertEquals(19,a[2].get());
    }


}
