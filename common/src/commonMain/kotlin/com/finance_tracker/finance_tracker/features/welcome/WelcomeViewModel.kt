package com.finance_tracker.finance_tracker.features.welcome

import com.finance_tracker.finance_tracker.core.common.view_models.ComponentViewModel
import com.finance_tracker.finance_tracker.domain.interactors.AuthInteractor
import com.finance_tracker.finance_tracker.features.welcome.analytics.WelcomeAnalytics
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val welcomeAnalytics: WelcomeAnalytics,
    private val authInteractor: AuthInteractor
): ComponentViewModel<WelcomeAction, WelcomeComponent.Action>() {

    init {
        welcomeAnalytics.trackScreenOpen()
    }

    fun onContinueWithGoogleClick() {
        welcomeAnalytics.trackContinueWithGoogleClick()
    }

    fun onGoogleSignInSuccess(token: String) {
        viewModelScope.launch {
            authInteractor.sendGoogleAuthToken(token)
        }
    }

    fun onGoogleSignInError(exception: Exception) {
        Napier.e(message = "GoogleSignInError", throwable = exception)
    }

    fun onContinueWithEmailClick() {
        welcomeAnalytics.trackContinueWithEmailClick()
        componentAction = WelcomeComponent.Action.OpenEmailAuthScreen
    }

    fun onSkipClick() {
        welcomeAnalytics.trackSkipClick()
        componentAction = WelcomeComponent.Action.OpenMainScreen
    }
}