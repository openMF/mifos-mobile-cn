package org.mifos.mobile.cn.data.datamanager

import io.reactivex.Completable
import io.reactivex.CompletableSource
import io.reactivex.Observable
import io.reactivex.functions.Function
import org.mifos.mobile.cn.data.local.PreferenceKey
import org.mifos.mobile.cn.data.local.PreferencesHelper
import org.mifos.mobile.cn.exceptions.ExceptionStatusCode

open class MifosBaseDataManager(private  var dataManagerAuth: DataManagerAuth, preferencesHelper: PreferencesHelper) {
    private  var preferencesHelper: PreferencesHelper = preferencesHelper

    fun <T> authenticatedObservableApi(observable: Observable<T>): Observable<T> {
        return observable.onErrorResumeNext(refreshTokenAndRetryObser<T>(observable))
    }

    fun authenticatedCompletableApi(completable: Completable): Completable {
        return completable.onErrorResumeNext(refreshTokenAndRetryCompletable(completable))
    }

    fun <T> refreshTokenAndRetryObser(
            toBeResumed: Observable<T>): Function<Throwable, out Observable<out T>> {
        return Function { throwable ->
            // Here check if the error thrown really is a 403
            if (ExceptionStatusCode.isHttp403Error(throwable)) {
                preferencesHelper.putBoolean(PreferenceKey.PREF_KEY_REFRESH_ACCESS_TOKEN, true)
                return@Function dataManagerAuth.refreshToken().concatMap { authentication ->
                    preferencesHelper.putBoolean(
                            PreferenceKey.PREF_KEY_REFRESH_ACCESS_TOKEN, false)
                    preferencesHelper.putAccessToken(
                            authentication.accessToken)
                    preferencesHelper.putSignInUser(authentication)
                    toBeResumed
                }
            }
            // re-throw this error because it's not recoverable from here
            Observable.error(throwable)
        }
    }

    fun refreshTokenAndRetryCompletable(
            toBeResumed: Completable): Function<Throwable, out CompletableSource> {
        return Function { throwable ->
            // Here check if the error thrown really is a 403
            if (ExceptionStatusCode.isHttp403Error(throwable)) {
                preferencesHelper.putBoolean(PreferenceKey.PREF_KEY_REFRESH_ACCESS_TOKEN, true)
                return@Function dataManagerAuth.refreshToken().flatMapCompletable { authentication ->
                    preferencesHelper.putBoolean(
                            PreferenceKey.PREF_KEY_REFRESH_ACCESS_TOKEN, false)
                    preferencesHelper.putAccessToken(
                            authentication.accessToken)
                    preferencesHelper.putSignInUser(authentication)
                    toBeResumed
                }
            }
            // re-throw this error because it's not recoverable from here
            Completable.error(throwable)
        }
    }


}
