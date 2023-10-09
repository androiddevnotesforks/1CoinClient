package com.finance_tracker.finance_tracker.features.registration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.finance_tracker.finance_tracker.features.email_auth.enter_email.EnterEmailScreen
import com.finance_tracker.finance_tracker.features.email_auth.enter_otp.EnterOtpScreen
import com.finance_tracker.finance_tracker.features.welcome.WelcomeScreen

@Composable
fun RegistrationScreen(
    component: RegistrationComponent
) {
    val childStack by component.childStack.subscribeAsState()
    Children(
        stack = childStack,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is RegistrationComponent.Child.WelcomeChild -> {
                WelcomeScreen(component = child.component)
            }

            is RegistrationComponent.Child.EnterEmailChild -> {
                EnterEmailScreen(
                    component = child.component
                )
            }

            is RegistrationComponent.Child.EnterOtpChild -> {
                EnterOtpScreen(
                    component = child.component
                )
            }
        }
    }
}