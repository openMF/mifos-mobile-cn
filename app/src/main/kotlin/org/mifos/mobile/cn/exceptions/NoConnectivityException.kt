package org.mifos.mobile.cn.exceptions

import java.io.IOException

class NoConnectivityException : IOException() {

    override fun getLocalizedMessage(): String {
        return "No connectivity exception"
    }
}