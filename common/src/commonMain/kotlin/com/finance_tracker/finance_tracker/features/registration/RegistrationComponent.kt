package com.finance_tracker.finance_tracker.features.registration

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.finance_tracker.finance_tracker.core.common.BaseComponent
import com.finance_tracker.finance_tracker.core.common.injectViewModel
import com.finance_tracker.finance_tracker.features.email_auth.enter_email.EnterEmailComponent
import com.finance_tracker.finance_tracker.features.email_auth.enter_otp.EnterOtpComponent
import com.finance_tracker.finance_tracker.features.welcome.WelcomeComponent
import kotlinx.serialization.Serializable

class RegistrationComponent(
    componentContext: ComponentContext,
    private val openMainScreen: () -> Unit
) : BaseComponent<RegistrationViewModel>(componentContext) {

    override val viewModel: RegistrationViewModel = injectViewModel()

    private val navigation = StackNavigation<Config>()

    val childStack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Welcome,
            handleBackButton = true, // Pop the back stack on back button press
            childFactory = ::createChild,
        )

    private fun createChild(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            Config.Welcome -> {
                Child.WelcomeChild(
                    component = WelcomeComponent(
                        componentContext = componentContext,
                        openEmailAuthScreen = {
                            navigation.push(Config.EnterEmail)
                        },
                        openMainScreen = openMainScreen
                    )
                )
            }

            Config.EnterEmail -> {
                Child.EnterEmailChild(
                    component = EnterEmailComponent(
                        componentContext = componentContext,
                        openEnterOtpScreen = { email ->
                            navigation.push(Config.EnterOtp(email))
                        },
                        close = {
                            navigation.pop()
                        }
                    )
                )
            }

            is Config.EnterOtp -> {
                Child.EnterOtpChild(
                    component = EnterOtpComponent(
                        componentContext = componentContext,
                        email = config.email,
                        close = {
                            navigation.pop()
                        }
                    )
                )
            }
        }

    sealed class Child {
        class WelcomeChild(val component: WelcomeComponent) : Child()
        class EnterEmailChild(val component: EnterEmailComponent) : Child()
        class EnterOtpChild(val component: EnterOtpComponent) : Child()
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Welcome : Config()

        @Serializable
        data object EnterEmail : Config()

        @Serializable
        data class EnterOtp(val email: String) : Config()
    }
}