package com.palmdev.learn_math.presentation.screens.end_exam

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.palmdev.learn_math.databinding.FragmentEndExamBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EndExamFragment : Fragment() {

    private val viewModel: EndExamViewModel by viewModel()
    private lateinit var binding: FragmentEndExamBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEndExamBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}