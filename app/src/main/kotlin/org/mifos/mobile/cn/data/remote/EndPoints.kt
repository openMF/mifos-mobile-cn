package org.mifos.mobile.cn.data.remote

object EndPoints {

    /*
     * API End Paths
     * <- This section manage the different type of end points
     */
    const val API_AUTH_PATH = "/customer"
    const val API_IDENTITY_PATH = "/identity/v1"
    const val API_CUSTOMER_PATH = "/api/customer/v1"
    const val API_DEPOSIT_PATH = "/api/deposit/v1"
    const val API_PORTFOLIO_PATH = "/api/portfolio/v1"
    const val API_ACCOUNTING_PATH = "/api/accounting/v1"
    const val API_TELLER_PATH = "/api/teller/v1"

    // For Payment-Hub related calls
    const val INTEROPERATION = "interoperation"
    const val ACCOUNTS = "accounts"
    const val TRANSFER = "transfer"
    const val PARTY_REGISTRATION = "partyRegistration"
    const val TRANSACTION_REQUEST = "transactionRequest"
}
