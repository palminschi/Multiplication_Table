package com.palmdev.learn_math.di

import android.app.Application
import androidx.room.Room
import com.palmdev.learn_math.data.database.ResultsDao
import com.palmdev.learn_math.data.database.ResultsDatabase
import com.palmdev.learn_math.utils.DATABASE_NAME
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): ResultsDatabase {
        return Room
            .databaseBuilder(application, ResultsDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideResultsDao(database: ResultsDatabase): ResultsDao {
        return database.resultsDao()
    }

    single<ResultsDatabase> {
        provideDatabase(application = get())
    }

    single<ResultsDao> {
        provideResultsDao(database = get())
    }


}