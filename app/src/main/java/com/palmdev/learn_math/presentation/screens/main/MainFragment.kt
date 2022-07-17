package com.palmdev.learn_math.presentation.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnLearnTable.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_selectTableFragment)
        }
        binding.btnTraining.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_selectTrainingFragment)
        }
        binding.btnExam.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_startExamFragment)
        }
    }
}