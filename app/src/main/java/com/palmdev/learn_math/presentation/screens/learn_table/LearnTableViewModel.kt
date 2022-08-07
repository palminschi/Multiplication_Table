package com.palmdev.learn_math.presentation.screens.learn_table

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.learn_math.domain.repository.DivisionRepository
import com.palmdev.learn_math.domain.repository.MultiplicationRepository
import com.palmdev.learn_math.utils.FirebaseEvents

class LearnTableViewModel(
    private val multiplicationRepository: MultiplicationRepository,
    private val divisionRepository: DivisionRepository,
) : ViewModel() {

    val table = MutableLiveData<String>()

    init {
        FirebaseEvents().setScreenViewEvent(screenName = "Learn Table")
    }

    fun getMultiplicationTable(withNumber: Int) {
        table.value = multiplicationRepository.getTable(withNumber)
    }

    fun getDivisionTable(withNumber: Int) {
        table.value = divisionRepository.getTable(withNumber)
    }
}