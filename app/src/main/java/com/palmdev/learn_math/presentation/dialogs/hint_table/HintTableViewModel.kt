package com.palmdev.learn_math.presentation.dialogs.hint_table

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.learn_math.domain.repository.DivisionRepository
import com.palmdev.learn_math.domain.repository.MultiplicationRepository
import com.palmdev.learn_math.utils.FirebaseEvents
import com.palmdev.learn_math.utils.Operation

class HintTableViewModel(
    private val multiplicationRepository: MultiplicationRepository,
    private val divisionRepository: DivisionRepository,
) : ViewModel() {

    val table = MutableLiveData<String>()

    init {
        FirebaseEvents().setScreenViewEvent(screenName = "Dialog Table")
    }

    fun getTable(withNumber: Int, operation: Operation) {
        when (operation) {
            Operation.MULTIPLICATION -> table.value = multiplicationRepository.getTable(withNumber)
            Operation.DIVISION -> table.value = divisionRepository.getTable(withNumber)
            else -> table.value = multiplicationRepository.getTable(withNumber)
        }
    }
}