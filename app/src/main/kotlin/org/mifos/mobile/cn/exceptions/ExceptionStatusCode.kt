package xyz.idtlabs.icommit.fieldui.exceptions

import retrofit2.HttpException

/**
 * @author Rajan Maurya
 * On 07/05/18.
 */
object ExceptionStatusCode {

    fun isHttp401Error(throwable: Throwable): Boolean {
        return (throwable as HttpException).code() == 401
    }

    fun isHttp500Error(throwable: Throwable): Boolean {
        return (throwable as HttpException).code() == 500
    }
}