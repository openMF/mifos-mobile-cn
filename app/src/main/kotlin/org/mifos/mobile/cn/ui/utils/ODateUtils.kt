package org.mifos.mobile.cn.ui.utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object ODateUtils {
    val TAG = ODateUtils::class.java.simpleName
    val DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss"
    val DEFAULT_DATE_FORMAT = "yyyy-MM-dd"
    val DEFAULT_TIME_FORMAT = "HH:mm:ss"
    val DATE_DISPLAY_FORMAT = "dd/MM/yyyy"
    val DATETIME_DISPLAY_FORMAT = "dd/MM/yyyy HH:mm"
    val DATETIME_SERVER_FORMAT = "yyyy/MM/dd HH:mm"
    val DATE_FORMAT_SORTABLE = "yyyyMMdd_HHmmss_SSS"
    val EXTERNAL_STORAGE_FOLDER = "Judiciary Notes Foss"
    val DATE_REGEX_FORMAT = "[0-9]{2}/[0-9]{2}/[0-9]{4}"
    val DATETIME_REGEX_FORMAT = "[0-9]{2}/[0-9]{2}/[0-9]{4} [0-9]{2}:[0-9]{2}"
    val DATE_HINT_FORMAT = "Date format should be dd/mm/yyyy e.g. 20/09/2014"


    /**
     * Return Current date string in "yyyy-MM-dd HH:mm:ss" format
     *
     * @return current date string (Default timezone)
     */
    val date: String
        get() = getDate(Date(), DEFAULT_FORMAT)

    /**
     * Returns UTC date string in "yyyy-MM-dd HH:mm:ss" format.
     *
     * @return string, UTC Date
     */
    val utcDate: String
        get() = getUTCDate(Date(), DEFAULT_FORMAT)

    fun getFutureDate(date: String, second: Int): String {
        return getFutureDate(date, DEFAULT_FORMAT, second)
    }

    /**
     * Returns current date string in given format
     *
     * @param format, date format
     * @return current date string (Default timezone)
     */
    fun getDate(format: String): String {
        return getDate(Date(), format)
    }

    /**
     * Returns current date string in given format
     *
     * @param date,          date object
     * @param defaultFormat, date format
     * @return current date string (default timezone)
     */
    fun getDate(date: Date, defaultFormat: String): String {
        return createDate(date, defaultFormat, false)
    }

    fun getFutureDate(date: String, defaultFormat: String, second: Int): String {
        return createFutureDate(date, defaultFormat, false, second)
    }

    /**
     * Return UTC date in given format
     *
     * @param format, date format
     * @return UTC date string
     */
    fun getUTCDate(format: String): String {
        return getUTCDate(Date(), format)
    }

    /**
     * Returns UTC Date string in given date format
     *
     * @param date,          Date object
     * @param defaultFormat, Date pattern format
     * @return UTC date string
     */
    fun getUTCDate(date: Date, defaultFormat: String): String {
        return createDate(date, defaultFormat, true)
    }

    /**
     * Convert UTC date to default timezone
     *
     * @param date       UTC date string
     * @param dateFormat default date format
     * @param toFormat   converting date format
     * @return string converted date string
     */
    @JvmOverloads
    fun convertToDefault(date: String, dateFormat: String, toFormat: String = dateFormat): String {
        return createDate(createDateObject(date, dateFormat, false), toFormat, false)
    }

    /**
     * Convert default timezone date to UTC timezone
     *
     * @param date,      date in string
     * @param dateFormat default date format
     * @param toFormat   display format
     * @return string, returns string converted to UTC
     */
    @JvmOverloads
    fun convertToUTC(date: String, dateFormat: String, toFormat: String = dateFormat): String {
        return createDate(createDateObject(date, dateFormat, true), toFormat, true)
    }

    fun parseDate(date: String, dateFormat: String, toFormat: String): String {
        return createDate(createDateObject(date, dateFormat, false), toFormat, true)
    }

    /**
     * Create Date instance from given date string.
     *
     * @param date               date in string
     * @param dateFormat,        original date format
     * @param hasDefaultTimezone if date is in default timezone than true, otherwise false
     * @return Date, returns Date object with given date
     */
    fun createDateObject(date: String, dateFormat: String,
                         hasDefaultTimezone: Boolean?): Date? {
        var dateObj: Date? = null
        try {
            val simpleDateFormat = SimpleDateFormat(dateFormat)
            if ((!hasDefaultTimezone!!)) {
                simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT")
            }
            dateObj = simpleDateFormat.parse(date)
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }

        return dateObj
    }

    /**
     * Returns date before given days
     *
     * @param days days to before
     * @return string date string before days
     */
    fun getDateBefore(days: Int): String {
        val today = Date()
        val cal = GregorianCalendar()
        cal.time = today
        cal.add(Calendar.DAY_OF_MONTH, days * -1)
        val date = cal.time
        val gmtFormat = SimpleDateFormat()
        gmtFormat.applyPattern("yyyy-MM-dd 00:00:00")
        val gmtTime = TimeZone.getTimeZone("GMT")
        gmtFormat.timeZone = gmtTime
        return gmtFormat.format(date)
    }

    fun setDateTime(originalDate: Date, hour: Int, minute: Int, second: Int): Date {
        val cal = GregorianCalendar()
        cal.time = originalDate
        cal.set(Calendar.HOUR, hour)
        cal.set(Calendar.MINUTE, minute)
        cal.set(Calendar.SECOND, second)
        cal.set(Calendar.MILLISECOND, 0)
        return cal.time
    }

    fun getDateDayBeforeAfterUTC(utcDate: String, days: Int): String {
        val dt = createDateObject(utcDate, DEFAULT_FORMAT, false)
        val cal = GregorianCalendar()
        cal.time = dt
        cal.add(Calendar.DAY_OF_MONTH, days)
        return createDate(cal.time, DEFAULT_FORMAT, true)
    }

    fun getDateDayBefore(originalDate: Date, days: Int): Date {
        val cal = GregorianCalendar()
        cal.time = originalDate
        cal.add(Calendar.DAY_OF_MONTH, days * -1)
        return cal.time
    }

    fun getCurrentDateWithHour(addHour: Int): String {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR)
        cal.set(Calendar.HOUR, hour + addHour)
        val date = cal.time
        return createDate(date, DEFAULT_FORMAT, true)
    }

    fun getDateMinuteBefore(originalDate: Date, minutes: Int): Date {
        val cal = GregorianCalendar()
        cal.time = originalDate
        cal.add(Calendar.MINUTE, minutes * -1)
        return cal.time
    }

    private fun createDate(date: Date?, defaultFormat: String, utc: Boolean?): String {
        val gmtFormat = SimpleDateFormat()
        gmtFormat.applyPattern(defaultFormat)
        val gmtTime = if (utc!!) TimeZone.getTimeZone("GMT") else TimeZone.getDefault()
        gmtFormat.timeZone = gmtTime
        return gmtFormat.format(date)
    }

    private fun createFutureDate(date: String, defaultFormat: String, utc: Boolean?,
                                 second: Int): String {
        val gmtFormat = SimpleDateFormat()
        val previousDateFormat = SimpleDateFormat(defaultFormat)
        gmtFormat.applyPattern(defaultFormat)
        val gmtTime = if (utc!!) TimeZone.getTimeZone("GMT") else TimeZone.getDefault()
        gmtFormat.timeZone = gmtTime
        val calendar = Calendar.getInstance()
        try {
            calendar.time = previousDateFormat.parse(date)
        } catch (e: ParseException) {
            Log.d(TAG, e.localizedMessage)
        }

        calendar.add(Calendar.SECOND, second)
        return gmtFormat.format(calendar.time)
    }

    fun floatToDuration(durationInFloat: String): String {
        var durationInFloat = durationInFloat
        durationInFloat = String.format("%2.2f", java.lang.Float.parseFloat(durationInFloat))
        val parts = durationInFloat.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val minute = java.lang.Long.parseLong(parts[0])
        val seconds = 60 * java.lang.Long.parseLong(parts[1]) / 100
        return String.format("%02d:%02d", minute, seconds)
    }

    fun durationToFloat(duration: String): String {
        val parts = duration.split("\\:".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (parts.size == 2) {
            var minute = java.lang.Long.parseLong(parts[0])
            var seconds = java.lang.Long.parseLong(parts[1])
            if (seconds == 60L) {
                minute += 1
                seconds = 0
            } else {
                seconds = 100 * seconds / 60
            }
            return String.format("%d.%d", minute, seconds)
        }
        return "false"
    }

    fun durationToFloat(milliseconds: Long): String {
        var minute = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        var seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                TimeUnit.MINUTES.toSeconds(minute)
        if (seconds == 60L) {
            minute += 1
            seconds = 0
        } else {
            seconds = 100 * seconds / 60
        }
        return String.format("%d.%d", minute, seconds)
    }
}
/**
 * Convert UTC date to default timezone date
 *
 * @param date       date in string
 * @param dateFormat default date format
 * @return string converted date string
 */
/**
 * Convert to UTC date
 *
 * @param date       date in string
 * @param dateFormat default date format
 * @return string date string in UTC timezone
 */