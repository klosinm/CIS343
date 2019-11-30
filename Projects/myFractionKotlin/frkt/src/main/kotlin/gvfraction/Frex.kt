package gvfraction
// Fraction extension functions

operator fun Int.minus(fr: FractionOperator): FractionOperator {
    return (-fr).minus(-this)
}
operator fun Int.plus(fr: FractionOperator): FractionOperator {
    return fr.plus(this)
}

operator fun Int.times(fr: FractionOperator): FractionOperator {
    return fr.times(this)
}