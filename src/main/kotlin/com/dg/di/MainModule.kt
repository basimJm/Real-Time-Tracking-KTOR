package com.dg.di

import com.dg.controller.TrackingController
import org.koin.dsl.module

val mainModule = module {
    single {
        TrackingController()
    }
    single {

    }
}