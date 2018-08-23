package android.rezkyaulia.com.hellokotlin.Util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeUtility @Inject
constructor() {

    val triggerEveryHours: Long
        get() {
            val cal = Calendar.getInstance()
            cal.add(Calendar.MINUTE, 1)
            return cal.timeInMillis
        }

    fun getUserFriendlyDate(date: Date): String {
        val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy")
        return simpleDateFormat.format(date)
    }

    fun getUserFriendlyDateTime(date: Date): String {
        val simpleDateFormat = SimpleDateFormat("yyyy MMM,dd HH:mm")
        return simpleDateFormat.format(date)
    }


    fun convertStringToDate(str: String): Date? {
        val format = SimpleDateFormat("yyyy-MM-dd")
        try {
            return format.parse(str)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return null
    }


}
