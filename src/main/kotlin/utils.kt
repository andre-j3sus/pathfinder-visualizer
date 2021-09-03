
/**
 * Returns the string without the last word.
 */
fun String.dropLastWord(): String {
    return this.dropLastWhile { it != ' ' }
}
