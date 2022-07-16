package com.palmdev.learn_math.presentation.screens.learn_table

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.palmdev.learn_math.data.repository.MultiplicationRepository

class LearnTableViewModel(private val multiplicationRepository: MultiplicationRepository) :
    ViewModel() {

    val table = MutableLiveData<String>()

    fun getMultiplicationTable(withNumber: Int) {
        table.value = multiplicationRepository.getTable(withNumber)
    }
}