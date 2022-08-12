package com.palmdev.learn_math.di

import com.palmdev.learn_math.data.local.repository.games.Game60secRepositoryImpl
import com.palmdev.learn_math.data.local.repository.games.GameMoreOrLessRepositoryImpl
import com.palmdev.learn_math.data.local.repository.games.GameScoresRepositoryImpl
import com.palmdev.learn_math.domain.repository.games.Game60secRepository
import com.palmdev.learn_math.domain.repository.games.GameMoreOrLessRepository
import com.palmdev.learn_math.domain.repository.games.GameScoresRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val gamesModule = module {

    single<GameScoresRepository> {
        GameScoresRepositoryImpl(
            game60secRepository = get(),
            gameMoreOrLessRepository = get()
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
    single<GameMoreOrLessRepository> {
        GameMoreOrLessRepositoryImpl(context = androidApplication())
    }
}