package com.palmdev.learn_math.presentation.screens.learn_table

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.learn_math.data.local.repository.DivisionRepository
import com.palmdev.learn_math.data.local.repository.MultiplicationRepository

class LearnTableViewModel(
    private val multiplicationRepository: MultiplicationRepository,
    private val divisionRepository: DivisionRepository,
) : ViewModel() {

    val table = MutableLiveData<String>()

    fun getMultiplicationTable(withNumber: Int) {
        table.value = multiplicationRepository.getTable(withNumber)
    }

    fun getDivisionTable(withNumber: Int) {
        table.value = divisionRepository.getTable(withNumber)
    }
}