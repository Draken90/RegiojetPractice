package webtest.base

import org.apache.commons.lang3.StringUtils

/**
 * New Line alias for Line Separator
 */
val NL: String = System.lineSeparator()

/**
 * From all lines in text removes blank chars from begin and end, also removes all blank lines
 */
fun String.trimAllLines(): String =
    lines()
        .map { it.trim() }
        .filter { it != "" }
        .joinToString(NL)

fun String.startsWithAny(vararg searchStrings: String): Boolean =
    StringUtils.startsWithAny(this, *searchStrings)

/**
 * Indents all lines from left with value in param 'indent'
 */
fun String.indentLines(indent: String): String = lines().indent(indent)

/**
 * Indents all lines from left with space of size 'charsFromLeft'
 */
fun String.indentLines(charsFromLeft: Int): String = indentLines(" ".repeat(charsFromLeft))

/**
 * Indents all lines from left with space of size 'charsFromLeft' except first line.
 */
fun String.indentLinesExceptFirst(charsFromLeft: Int): String =
    indentLines(" ".repeat(charsFromLeft))
        .removeRange(0, charsFromLeft)

/**
 * Indents all lines from left with value in param 'indent'
 */
fun List<String>.indent(indent: String): String = joinToString(separator = NL) { indent + it }

/**
 * Indents all lines from left with space of size 'charsFromLeft'
 */
fun List<String>.indent(charsFromLeft: Int): String = indent(" ".repeat(charsFromLeft))

/**
 * Indents all lines from left with space of size 'charsFromLeft' except first line.
 */
fun List<String>.indentLinesExceptFirst(charsFromLeft: Int): String {
    val indent = " ".repeat(charsFromLeft)
    return mapIndexed { idx, line ->
        if (idx == 0) line
        else indent + line
    }.joinToString(NL)
}
