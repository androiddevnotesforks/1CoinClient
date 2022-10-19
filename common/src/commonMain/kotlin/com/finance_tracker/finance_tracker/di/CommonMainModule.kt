package com.finance_tracker.finance_tracker.di

import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.ksp.generated.module

fun commonModules() = listOf(CommonMainModule().module)

@Module
@ComponentScan(value = "com.finance_tracker.finance_tracker")
class CommonMainModule