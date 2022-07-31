package com.palmdev.learn_math.di

import com.palmdev.learn_math.presentation.dialogs.hint_table.HintTableViewModel
import com.palmdev.learn_math.presentation.dialogs.review.ReviewDialogViewModel
import com.palmdev.learn_math.presentation.screens.end.EndViewModel
import com.palmdev.learn_math.presentation.screens.exercise_input.ExerciseInputViewModel
import com.palmdev.learn_math.presentation.screens.exercise_select.ExerciseSelectViewModel
import com.palmdev.learn_math.presentation.screens.exercise_true_or_false.ExerciseTrueOrFalseViewModel
import com.palmdev.learn_math.presentation.screens.games.game_duel.GameDuelViewModel
import com.palmdev.learn_math.presentation.screens.learn_table.LearnTableViewModel
import com.palmdev.learn_math.presentation.screens.main.MainViewModel
import com.palmdev.learn_math.presentation.screens.purchase.PurchaseViewModel
import com.palmdev.learn_math.presentation.screens.start_exam.StartExamViewModel
import com.palmdev.learn_math.presentation.screens.statistics.StatisticsViewModel
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
            divisionRepository = get(),
            additionRepository = get(),
            subtractionRepository = get()
        )
    }
    viewModel {
        ExerciseTrueOrFalseViewModel(
            multiplicationRepository = get(),
            divisionRepository = get(),
            additionRepository = get(),
            subtractionRepository = get()
        )
    }
    viewModel {
        ExerciseInputViewModel(
            multiplicationRepository = get(),
            divisionRepository = get(),
            additionRepository = get(),
            subtractionRepository = get()
        )
    }
    viewModel {
        EndViewModel(
            resultsRepository = get(),
            reviewRepository = get(),
            adsRepository = get(),
            userDataRepository = get()
        )
    }
    viewModel {
        MainViewModel(
            resultsRepository = get(),
            adsRepository = get(),
            userDataRepository = get()
        )
    }
    viewModel {
        StartExamViewModel(
            resultsRepository = get()
        )
    }
    viewModel {
        StatisticsViewModel(
            resultsRepository = get(),
            adsRepository = get()
        )
    }
    viewModel {
        ReviewDialogViewModel(
            reviewRepository = get()
        )
    }
    viewModel {
        PurchaseViewModel(
            userDataRepository = get(),
            purchaseRepository = get(),
            adsRepository = get()
        )
    }
    viewModel {
        GameDuelViewModel(
            multiplicationRepository = get(),
            divisionRepository = get(),
            additionRepository = get(),
            subtractionRepository = get()
        )
    }
    viewModel {
        HintTableViewModel(
            multiplicationRepository = get(),
            divisionRepository = get()
        )
    }
}