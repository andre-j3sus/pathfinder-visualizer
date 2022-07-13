// ktlint-disable filename
/**
 * Returns the string without the last word.
 */
fun String.dropLastWord(): String = this.dropLastWhile { it != ' ' }
