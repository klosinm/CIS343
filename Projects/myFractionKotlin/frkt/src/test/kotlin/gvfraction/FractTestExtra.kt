package gvfraction


import kotlin.test.assertEquals
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FractTestExtra {
    var factory = FractionFactory.getInstance()

    init {
        // TODO: register your class
        //factory.registerClass(YourFraction::class)
    }

    @Test
    fun decimalExpansionZero() {
        val zero = factory.create()
        for (k in 1..20) {
            assertEquals("0", zero(k))
        }
    }

    @Test
    fun decimalExpansionPositiveMixedFraction() {
        val a = factory.create("123 1/2")
        assertEquals("124", a(2))
        assertEquals("124", a(3))
        assertEquals("124.", a(4))
        assertEquals("123.5", a(5))
        assertEquals("123.50", a(6))
        assertEquals("123.500", a(7))
        assertEquals("123.5000", a(8))
    }

    @Test
    fun decimalExpansionNegativeMixedFraction() {
        val a = factory.create("-123 1/2")
        assertEquals("-124", a(2))
        assertEquals("-124", a(3))
        assertEquals("-124", a(4))
        assertEquals("-124.", a(5))
        assertEquals("-123.5", a(6))
        assertEquals("-123.50", a(7))
        assertEquals("-123.500", a(8))
        assertEquals("-123.5000", a(9))
    }

    @Test
    fun decimalExpansionPositiveRepeatingFraction() {
        val a = factory.create("10 2/3")
        assertEquals("11", a(1))
        assertEquals("11", a(2))
        assertEquals("11.", a(3))
        assertEquals("10.7", a(4))
        assertEquals("10.67", a(5))
        assertEquals("10.667", a(6))
        assertEquals("10.6667", a(7))
        assertEquals("10.66667", a(8))
        assertEquals("10.666667", a(9))
    }

    @Test
    fun decimalExpansionNegativeRepeatingFraction() {
        val a = factory.create("-10 2/3")
        assertEquals("-11", a(1))
        assertEquals("-11", a(2))
        assertEquals("-11", a(3))
        assertEquals("-11.", a(4))
        assertEquals("-10.7", a(5))
        assertEquals("-10.67", a(6))
        assertEquals("-10.667", a(7))
        assertEquals("-10.6667", a(8))
        assertEquals("-10.66667", a(9))
    }

    @Test
    fun decimalExpansionPositiveRepeatingPureFraction() {
        val a = factory.create("2/3")
        assertEquals(".7", a(1))
        assertEquals(".7", a(2))
        assertEquals(".67", a(3))
        assertEquals(".667", a(4))
        assertEquals(".6667", a(5))
        assertEquals(".66667", a(6))
        assertEquals(".666667", a(7))
        assertEquals(".6666667", a(8))
        assertEquals(".66666667", a(9))
    }

    @Test
    fun decimalExpansionNegativeRepeatingPureFraction() {
        val a = factory.create("-2/3")
        assertEquals("-.7", a(1))
        assertEquals("-.7", a(2))
        assertEquals("-.7", a(3))
        assertEquals("-.67", a(4))
        assertEquals("-.667", a(5))
        assertEquals("-.6667", a(6))
        assertEquals("-.66667", a(7))
        assertEquals("-.666667", a(8))
        assertEquals("-.6666667", a(9))
    }

    @Test
    fun decimalExpansionRepeatGroup() {
        val a = factory.create("1/7")
        assertEquals(".1", a(1))
        assertEquals(".1", a(2))
        assertEquals(".14", a(3))
        assertEquals(".143", a(4))
        assertEquals(".1429", a(5))
        assertEquals(".14286", a(6))
        assertEquals(".142857", a(7))
        assertEquals(".1428571", a(8))
        assertEquals(".14285714", a(9))
        assertEquals(".142857143", a(10))
        assertEquals(".1428571429", a(11))
        assertEquals(".14285714286", a(12))
        assertEquals(".142857142857", a(13))
    }

    @Test
    fun moreRepeatingFractions() {
        val repeating = mapOf(3 to "0.3", 6 to "0.16",
                7 to "0.142857",
                9 to "0.1",
                11 to "0.09",
                12 to "0.083",
                13 to "0.076923",
                14 to "0.0714285",
                15 to "0.06",
                17 to "0.0588235294117647",
                18 to "0.05",
                19 to "0.052631578947368421")

        for (k in 2 .. 19) {
            val a = factory.create("""1/$k""")
            val out = a.isRepeating()
            val r = repeating[k]
            if (r.isNullOrEmpty())
                assertFalse(out.isPresent())
            else {
                assertTrue(out.isPresent())
                assertEquals(r, out.get())
            }
        }
    }
}