package com.finance_tracker.finance_tracker.data.data_sources

expect class GoogleAuthDataSource {

    fun isUserSignedIn(): Boolean
}