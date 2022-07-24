package com.palmdev.learn_math.di

import com.palmdev.learn_math.data.local.repository.*
import com.palmdev.learn_math.data.local.storage.UserDataStorage
import com.palmdev.learn_math.data.remote.repository.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {

    single <MultiplicationRepository> {
        MultiplicationRepositoryImpl()
    }

    single <DivisionRepository> {
        DivisionRepositoryImpl()
    }

    single <ResultsRepository> {
        ResultsRepositoryImpl(
            resultsDao = get(),
            userDataStorage = get()
        )
    }

    single {
        UserDataStorage(context = androidApplication())
    }

    single<AdditionRepository> {
        AdditionRepositoryImpl()
    }
    single<SubtractionRepository> {
        SubtractionRepositoryImpl()
    }
    single<ReviewRepository> {
        ReviewRepositoryImpl(application = androidApplication())
    }

    single<AdsRepository> {
        AdsRepositoryImpl(
            context = androidApplication(),
            userDataRepository = get()
        )
    }

    single<UserDataRepository> {
        UserDataRepositoryImpl(
            context = androidApplication(),
            userDataStorage = get()
        )
    }

    single<PurchaseRepository> {
        PurchaseRepositoryImpl(
            context = androidApplication(),
            userDataRepository = get()
        )
    }

}