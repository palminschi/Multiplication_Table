package com.palmdev.learn_math.presentation.screens.end

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentEndBinding
import com.palmdev.learn_math.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class EndFragment : Fragment() {

    private val viewModel: EndViewModel by viewModel()
    private lateinit var binding: FragmentEndBinding
    private var customOnBackPressed: OnBackPressedCallback? = null
    private var avgAnswerTime = 0.0
    private var correctAnswers = 0
    private var wrongAnswers = 0
    private var operation = Operation.MULTIPLICATION
    private var earnedCoins = 0
    private var examOrTraining = TRAINING
    private var withNumber: Int? = null
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEndBinding.inflate(layoutInflater, container, false)
        avgAnswerTime = arguments?.getDouble(ARG_AVG_TIME) ?: 0.0
        correctAnswers = arguments?.getInt(ARG_RIGHT_ANSWERS) ?: 0
        wrongAnswers = arguments?.getInt(ARG_WRONG_ANSWERS) ?: 0
        arguments?.getSerializable(ARG_OPERATION)?.let {
            operation = it as Operation
        }
        examOrTraining = arguments?.getString(ARG_EXAM_OR_TRAINING) ?: TRAINING
        withNumber = arguments?.getInt(ARG_WITH_NUMBER)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initCustomOnBackPressed()
        saveData()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        binding.correctAnswers.text = correctAnswers.toString()
        binding.wrongAnswers.text = wrongAnswers.toString()
        binding.answerTime.text = avgAnswerTime.toString().take(4) + " s"

        if (examOrTraining == EXAM) {
            binding.tvCongratulations.text = if (correctAnswers < 5) getText(R.string.failed)
            else getText(R.string.passed)
        } else {
            binding.tvCongratulations.text = when (correctAnswers) {
                in 0..1 -> getText(R.string.tryAgain)
                in 2..4 -> getText(R.string.good)
                in 5..7 -> getText(R.string.great)
                in 8..10 -> getText(R.string.perfect)
                else -> getText(R.string.perfect)
            }
        }

        earnedCoins = if (examOrTraining == EXAM) correctAnswers * 10
        else correctAnswers * 5
        binding.tvEarnedCoins.text = earnedCoins.toString()
        viewModel.coins.observe(viewLifecycleOwner) {
            binding.coins.text = it.toString()
        }

        binding.btnHome.setOnClickListener {
            findNavController().navigate(R.id.action_endFragment_to_mainFragment)
        }
        binding.btnAgain.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
        }
        if (withNumber != null && withNumber != 0) {
            binding.btnGoTo.visibility = View.VISIBLE
            binding.btnGoTo.text = "${getText(R.string.goToNextNumber)}${withNumber!! + 1}"
            binding.btnGoTo.setOnClickListener {
                findNavController().navigate(
                    R.id.action_endFragment_to_learnTableFragment,
                    bundleOf(ARG_SELECTED_NUMBER to withNumber!! + 1)
                )
            }
        } else binding.btnGoTo.visibility = View.GONE

        viewModel.isFirstEndGame.observe(viewLifecycleOwner) { isFirst ->
            if (isFirst) {
                handler.postDelayed(
                    {
                        viewModel.showInterstitialAd()
                        viewModel.setNotFirstEndGame()
                    },
                    1000
                )
            } else {
                viewModel.userRatedApp.observe(viewLifecycleOwner) { rated ->
                    if (!rated) findNavController().navigate(R.id.action_endFragment_to_reviewDialogFragment)
                    else {
                        handler.postDelayed(
                            { viewModel.showInterstitialAd() },
                            1000
                        )
                    }
                }
            }
        }

    }

    private fun saveData() {
        viewModel.saveResults(
            operation = operation,
            correctAnswers = correctAnswers,
            wrongAnswers = wrongAnswers,
            avgAnswerTime = avgAnswerTime
        )
        if (examOrTraining == EXAM) {
            viewModel.saveExamResult(correctAnswers)
        }
        viewModel.addCoins(amount = earnedCoins)
        viewModel.getCoins()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }

    private fun initCustomOnBackPressed() {
        customOnBackPressed = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
                findNavController().popBackStack()
            }
        }
        customOnBackPressed?.let {
            activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, it)
        }
    }
}