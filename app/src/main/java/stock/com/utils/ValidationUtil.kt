package stock.com.utils

import java.util.regex.Matcher
import java.util.regex.Pattern

object ValidationUtil {

    fun isEmailValid(email: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(EMAIL_PATTERN)
        matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun isPhoneValid(phone: String): Boolean {
        val pattern: Pattern
        val matcher: Matcher
        val PHONE_PATTERN = "^[0-9]{7,12}$"
        pattern = Pattern.compile(PHONE_PATTERN)
        matcher = pattern.matcher(phone)
        return matcher.matches()
    }
}