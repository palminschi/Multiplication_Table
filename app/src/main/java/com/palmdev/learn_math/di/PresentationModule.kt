package com.palmdev.learn_math.di

import com.palmdev.learn_math.presentation.dialogs.continue_game.ContinueViewModel
import com.palmdev.learn_math.presentation.dialogs.hint_table.HintTableViewModel
import com.palmdev.learn_math.presentation.dialogs.review.ReviewDialogViewModel
import com.palmdev.learn_math.presentation.screens.end.EndViewModel
import com.palmdev.learn_math.presentation.screens.exercise_input.ExerciseInputViewModel
import com.palmdev.learn_math.presentation.screens.exercise_select.ExerciseSelectViewModel
import com.palmdev.learn_math.presentation.screens.exercise_true_or_false.ExerciseTrueOrFalseViewModel
import com.palmdev.learn_math.presentation.screens.games.game_2048.Continue2048ViewModel
import com.palmdev.learn_math.presentation.screens.games.game_2048.Game2048OverViewModel
import com.palmdev.learn_math.presentation.screens.games.game_2048.Game2048ViewModel
import com.palmdev.learn_math.presentation.screens.games.game_60sec.Game60secEndViewModel
import com.palmdev.learn_math.presentation.screens.games.game_60sec.Game60secViewModel
import com.palmdev.learn_math.presentation.screens.games.game_catch.GameCatchViewModel
import com.palmdev.learn_math.presentation.screens.games.game_duel.GameDuelEndViewModel
import com.palmdev.learn_math.presentation.screens.games.game_duel.GameDuelViewModel
import com.palmdev.learn_math.presentation.screens.games.game_hard_math.GameHardMathViewModel
import com.palmdev.learn_math.presentation.screens.games.game_more_or_less.GameMoreOrLessViewModel
import com.palmdev.learn_math.presentation.screens.games.select_game.SelectGameViewModel
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
            subtractionRepository = get(),
            adsRepository = get()
        )
    }
    viewModel {
        ExerciseTrueOrFalseViewModel(
            multiplicationRepository = get(),
            divisionRepository = get(),
            additionRepository = get(),
            subtractionRepository = get(),
            adsRepository = get()
        )
    }
    viewModel {
        ExerciseInputViewModel(
            multiplicationRepository = get(),
            divisionRepository = get(),
            additionRepository = get(),
            subtractionRepository = get(),
            adsRepository = get()
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
            subtractionRepository = get(),
            adsRepository = get()
        )
    }
    viewModel {
        HintTableViewModel(
            multiplicationRepository = get(),
            divisionRepository = get()
        )
    }
    viewModel {
        Game60secViewModel(
            game60secRepository = get(),
            adsRepository = get()
        )
    }
    viewModel {
        Game60secEndViewModel(
            adsRepository = get(),
            userDataRepository = get(),
            game60secRepository = get()
        )
    }
    viewModel {
        SelectGameViewModel(
            get(),
            adsRepository = get()
        )
    }
    viewModel {
        GameMoreOrLessViewModel(
            gameMoreOrLessRepository = get(),
            adsRepository = get()
        )
    }
    viewModel {
        ContinueViewModel(
            adsRepository = get()
        )
    }
    viewModel {
        GameHardMathViewModel(
            gameGameHardMathRepository = get(),
            adsRepository = get()
        )
    }
    viewModel {
        GameCatchViewModel(
            gameCatchRepository = get(),
            adsRepository = get()
        )
    }
    viewModel {
        GameDuelEndViewModel(
            adsRepository = get()
        )
    }
    viewModel {
        Game2048OverViewModel(
            adsRepository = get()
        )
    }
    viewModel {
        Game2048ViewModel(
            adsRepository = get()
        )
    }
    viewModel {
        Continue2048ViewModel(
            adsRepository = get()
        )
    }

}