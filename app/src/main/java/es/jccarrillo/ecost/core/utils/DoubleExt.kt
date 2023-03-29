package es.jccarrillo.ecost.core.utils

fun String.toI18NDouble(): Double? {
    val clean = this
        .replace(",", ".")
        .trim()
    return clean.toDoubleOrNull()
}