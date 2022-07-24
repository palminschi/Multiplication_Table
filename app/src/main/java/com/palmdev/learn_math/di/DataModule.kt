package com.palmdev.learn_math.di

import com.palmdev.learn_math.data.local.repository.*
import com.palmdev.learn_math.data.local.storage.UserDataStorage
import com.palmdev.learn_math.data.remote.repository.AdsRepository
import com.palmdev.learn_math.data.remote.repository.AdsRepositoryImpl
import com.palmdev.learn_math.data.remote.repository.ReviewRepository
import com.palmdev.learn_math.data.remote.repository.ReviewRepositoryImpl
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
        AdsRepositoryImpl(context = androidApplication())
    }

}