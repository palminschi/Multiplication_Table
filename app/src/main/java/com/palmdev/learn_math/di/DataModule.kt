package com.palmdev.learn_math.di

import com.palmdev.learn_math.data.repository.DivisionRepository
import com.palmdev.learn_math.data.repository.DivisionRepositoryImpl
import com.palmdev.learn_math.data.repository.MultiplicationRepository
import com.palmdev.learn_math.data.repository.MultiplicationRepositoryImpl
import org.koin.dsl.module

val dataModule = module {

    single <MultiplicationRepository> {
        MultiplicationRepositoryImpl()
    }

    single <DivisionRepository> {
        DivisionRepositoryImpl()
    }

}