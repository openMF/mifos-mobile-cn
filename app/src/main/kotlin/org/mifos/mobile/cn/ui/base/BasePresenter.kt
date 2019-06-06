package org.mifos.mobile.cn.ui.base

import android.content.Context
import android.util.Log
import android.widget.Toast
import org.mifos.mobile.cn.exceptions.NoConnectivityException

/**
 * Base class that implements the CasePresenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
open class BasePresenter<T : MvpView> constructor(var context: Context) : Presenter<T> {

    private var mvpView: T? = null
    val getMvpView: T
        get() { return mvpView ?: throw MvpViewNotAttachedException() }

    fun isViewAttached(): Boolean {
        return mvpView != null
    }

    override fun attachView(mvpView: T) {
        this.mvpView = mvpView
    }

    override fun detachView() {
        mvpView = null
    }

    fun checkViewAttached() {
        if (!isViewAttached()) throw MvpViewNotAttachedException()
    }

    fun showExceptionError(throwable: Throwable, errorMessage: String) {
        if (throwable is NoConnectivityException) {
            Toast.makeText(context,"No Internet Connectivity",Toast.LENGTH_SHORT).show()
        } else {
            Log.e("Error in Presenter",errorMessage,throwable)
        }
    }

    class MvpViewNotAttachedException : RuntimeException(
            "Please call Presenter.attachView(MvpView) before requesting data to the Presenter")
}