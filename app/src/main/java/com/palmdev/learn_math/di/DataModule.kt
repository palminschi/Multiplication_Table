package com.palmdev.learn_math.di

import com.palmdev.learn_math.data.repository.*
import org.koin.dsl.module

val dataModule = module {

    single <MultiplicationRepository> {
        MultiplicationRepositoryImpl()
    }

    single <DivisionRepository> {
        DivisionRepositoryImpl()
    }

    single <ResultsRepository> {
        ResultsRepositoryImpl(resultsDao = get())
    }

}