package org.mifos.mobile.cn.ui.mifos.loanApplication

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

// Use object so we have a singleton instance
object DataBus {

    private val publisher = PublishSubject.create<Any>()

    fun publish(event: Any) {
        publisher.onNext(event)
    }

    // Listen should return an Observable and not the publisher
    // Using ofType we filter only events that match that class type
    fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}