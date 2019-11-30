package gvfraction
// Fraction extension functions

operator fun Int.minus(fr: FractionOperator): FractionOperator {
    return (-fr).minus(-this)
   //return this.minus(fr)
}
operator fun Int.plus(fr: FractionOperator): FractionOperator {
    return fr.plus(this)
}

operator fun Int.times(fr: FractionOperator): FractionOperator {
    return fr.times(this)
   // return this.times(fr)
}