package com.palmdev.learn_math.di

import com.palmdev.learn_math.data.local.repository.games.*
import com.palmdev.learn_math.domain.repository.games.*
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val gamesModule = module {

    single<GameScoresRepository> {
        GameScoresRepositoryImpl(
            game60secRepository = get(),
            gameMoreOrLessRepository = get(),
            gameHardMathRepository = get(),
            gameCatchRepository = get()
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
    single<GameHardMathRepository> {
        GameHardMathRepositoryImpl(context = androidApplication())
    }
    single<GameCatchRepository> {
        GameCatchRepositoryImpl(context = androidApplication())
    }
}