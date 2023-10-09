package com.finance_tracker.finance_tracker.features.registration.di

import com.finance_tracker.finance_tracker.features.registration.RegistrationViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val registrationModule = module {
    factoryOf(::RegistrationViewModel)
}