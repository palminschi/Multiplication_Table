package com.palmdev.learn_math.di

import com.palmdev.learn_math.data.local.repository.games.Game60secRepositoryImpl
import com.palmdev.learn_math.data.local.repository.games.GameRecordsRepositoryImpl
import com.palmdev.learn_math.domain.repository.games.Game60secRepository
import com.palmdev.learn_math.domain.repository.games.GameRecordsRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val gamesModule = module {

    single<GameRecordsRepository> {
        GameRecordsRepositoryImpl(
            game60secRepository = get()
        )
    }
    single<Game60secRepository> {
        Game60secRepositoryImpl(
            context = androidApplication(),
            multiplicationRepository = get(),
            divisionRepository = get(),
            additionRepository = get(),
            subtractionRepository = get()
        )
    }
}