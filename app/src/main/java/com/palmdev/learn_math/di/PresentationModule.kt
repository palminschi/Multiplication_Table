package com.palmdev.learn_math.di

import com.palmdev.learn_math.presentation.screens.end.EndViewModel
import com.palmdev.learn_math.presentation.screens.exercise_input.ExerciseInputViewModel
import com.palmdev.learn_math.presentation.screens.exercise_select.ExerciseSelectViewModel
import com.palmdev.learn_math.presentation.screens.exercise_true_or_false.ExerciseTrueOrFalseViewModel
import com.palmdev.learn_math.presentation.screens.learn_table.LearnTableViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        LearnTableViewModel(
            multiplicationRepository = get(),
            divisionRepository = get()
        )
    }
    viewModel {
        ExerciseSelectViewModel(
            multiplicationRepository = get(),
            divisionRepository = get()
        )
    }
    viewModel {
        ExerciseTrueOrFalseViewModel(
            multiplicationRepository = get(),
            divisionRepository = get()
        )
    }
    viewModel {
        ExerciseInputViewModel(
            multiplicationRepository = get(),
            divisionRepository = get()
        )
    }
    viewModel {
        EndViewModel(
            resultsRepository = get()
        )
    }
}