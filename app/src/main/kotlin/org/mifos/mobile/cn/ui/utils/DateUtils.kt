package org.mifos.mobile.cn.ui.utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Manish Kumar
 * @since 09/July/2018
 */

object DateUtils {

    val LOG_TAG = DateUtils::class.java.simpleName


    val STANDARD_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    val DATE_TIME_FORMAT = "yyyy MMM dd HH:mm:ss"
    val OUTPUT_DATE_FORMAT = "dd MMM yyyy"
    val INPUT_DATE_FORMAT = "yyyy-MM-dd'Z'"
    val ACTIVITIES_DATE_FORMAT = "MMM dd, yyyy, HH:mm:ss a"

    val currentDate: String
        get() {
            val simpleDateFormat = SimpleDateFormat(OUTPUT_DATE_FORMAT,
                    Locale.ENGLISH)
            val calendar = Calendar.getInstance()
            return simpleDateFormat.format(calendar.time)
        }

    /**
     * Format date time string into "2013 Feb 28 13:24:56" format.
     *
     * @param dateString Standard Date Time String from server
     * @return String of Date time format 2013 Feb 28 13:24:56
     */
    fun getDateTime(dateString: String): String {
        var format = SimpleDateFormat(STANDARD_DATE_TIME_FORMAT, Locale.ENGLISH)
        var date = Date()
        try {
            date = format.parse(dateString)
        } catch (e: ParseException) {
            Log.d(LOG_TAG, e.localizedMessage)
        }

        format = SimpleDateFormat(DATE_TIME_FORMAT, Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return format.format(calendar.time)
    }

    fun isTokenExpired(tokenExpiration: String): Boolean {
        val format = SimpleDateFormat(STANDARD_DATE_TIME_FORMAT, Locale.ENGLISH)
        val date: Date
        try {
            date = format.parse(tokenExpiration)
            if (System.currentTimeMillis() > date.time) {
                return true
            }
        } catch (e: ParseException) {
            Log.d(LOG_TAG, e.localizedMessage)
        }

        return false
    }

    fun getDate(dateString: String, inputFormat: String, outFormat: String): String {
        var dateFormat = SimpleDateFormat(inputFormat, Locale.ENGLISH)
        var date = Date()
        try {
            date = dateFormat.parse(dateString)
        } catch (e: ParseException) {
            Log.d(LOG_TAG, e.localizedMessage)
        }

        dateFormat = SimpleDateFormat(outFormat, Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return dateFormat.format(calendar.time)
    }

    fun getDateInUTC(time: Calendar): String {
        val simpleDateFormat = SimpleDateFormat(STANDARD_DATE_TIME_FORMAT,
                Locale.ENGLISH)
        simpleDateFormat.timeZone = TimeZone.getTimeZone("UTC")
        return simpleDateFormat.format(time.time)
    }

    fun convertServerDate(calendar: Calendar): String {
        val simpleDateFormat = SimpleDateFormat(OUTPUT_DATE_FORMAT,
                Locale.ENGLISH)
        return simpleDateFormat.format(calendar.time)
    }
}
