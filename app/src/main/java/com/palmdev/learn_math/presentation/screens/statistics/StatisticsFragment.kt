package com.palmdev.learn_math.presentation.screens.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentStatisticsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatisticsFragment : Fragment() {

    private val viewModel: StatisticsViewModel by viewModel()
    private lateinit var binding: FragmentStatisticsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel.getResults()

        viewModel.examResults.observe(viewLifecycleOwner) {
            binding.tvCompletedExams.text = "${getText(R.string.completedExams)} ${it.totalAmount}"
            binding.tvSuccessfully.text = "${getText(R.string.successfullyPassed)} ${it.passed}"
            binding.tvFailedExams.text =
                "${getText(R.string.failedExams)} ${it.totalAmount - it.passed}"
        }

        viewModel.multiplicationAvgTime.observe(viewLifecycleOwner) {
            binding.multiplicationAvgTime.text = "${it.toString().take(4)} s"
        }
        viewModel.multiplicationTotalExercises.observe(viewLifecycleOwner) {
            binding.multiplicationExercises.text = it.toString()
        }
        viewModel.multiplicationCorrectAnswersPercent.observe(viewLifecycleOwner) {
            binding.multiplicationCorrectAnswers.text = "$it%"
            binding.multiplicationProgress.progress = it
        }

        viewModel.divisionAvgTime.observe(viewLifecycleOwner) {
            binding.divisionAvgTime.text = "${it.toString().take(4)} s"
        }
        viewModel.divisionTotalExercises.observe(viewLifecycleOwner) {
            binding.divisionExercises.text = it.toString()
        }
        viewModel.divisionCorrectAnswersPercent.observe(viewLifecycleOwner) {
            binding.divisionCorrectAnswers.text = "$it%"
            binding.divisionProgress.progress = it
        }

        viewModel.additionAvgTime.observe(viewLifecycleOwner) {
            binding.additionAvgTime.text = "${it.toString().take(4)} s"
        }
        viewModel.additionTotalExercises.observe(viewLifecycleOwner) {
            binding.additionExercises.text = it.toString()
        }
        viewModel.additionCorrectAnswersPercent.observe(viewLifecycleOwner) {
            binding.additionCorrectAnswers.text = "$it%"
            binding.additionProgress.progress = it
        }

        viewModel.subtractionAvgTime.observe(viewLifecycleOwner) {
            binding.subtractionAvgTime.text = "${it.toString().take(4)} s"
        }
        viewModel.subtractionTotalExercises.observe(viewLifecycleOwner) {
            binding.subtractionExercises.text = it.toString()
        }
        viewModel.subtractionCorrectAnswersPercent.observe(viewLifecycleOwner) {
            binding.subtractionCorrectAnswers.text = "$it%"
            binding.subtractionProgress.progress = it
        }
    }

    override fun onDestroyView() {
        viewModel.showInterstitialAd()
        super.onDestroyView()
    }
}