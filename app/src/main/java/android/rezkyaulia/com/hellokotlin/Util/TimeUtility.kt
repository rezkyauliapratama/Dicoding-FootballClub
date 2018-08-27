package android.rezkyaulia.com.hellokotlin.Util

import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeUtility @Inject
constructor() {


    fun getUserFriendlyDate(date: Date): String {
        val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy")
        return simpleDateFormat.format(date)
    }

    fun convertStringToDate(str: String): Date {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.parse(str)

    }


}
