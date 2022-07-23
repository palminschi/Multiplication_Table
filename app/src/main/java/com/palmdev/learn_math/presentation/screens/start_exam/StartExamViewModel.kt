package com.palmdev.learn_math.presentation.screens.start_exam

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.learn_math.data.model.ExamResults
import com.palmdev.learn_math.data.repository.ResultsRepository

class StartExamViewModel(
    private val resultsRepository: ResultsRepository
) : ViewModel() {

    val examResults = MutableLiveData<ExamResults>()

    fun getResults(){
        examResults.value = resultsRepository.getExamResults()
    }
}