package com.palmdev.learn_math.presentation.screens.games.game_60sec

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentGame60secEndBinding
import com.palmdev.learn_math.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class Game60secEndFragment : Fragment() {

    private val viewModel: Game60secEndViewModel by viewModel()
    private lateinit var binding: FragmentGame60secEndBinding
    private var minNumber = 0
    private var maxNumber = 10
    private var operation: Operation? = null
    private var correctAnswers = 0
    private var wrongAnswers = 0
    private var earnedCoins = 0
    private var isGame = false
    private var customOnBackPressed: OnBackPressedCallback? = null
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGame60secEndBinding.inflate(layoutInflater, container, false)
        arguments?.getSerializable(ARG_OPERATION)?.let {
            operation = it as Operation
        }
        correctAnswers = arguments?.getInt(ARG_RIGHT_ANSWERS) ?: 0
        wrongAnswers = arguments?.getInt(ARG_WRONG_ANSWERS) ?: 0
        isGame = arguments?.getBoolean(ARG_IS_GAME) ?: false
        if (isGame) {
            minNumber = 0
            maxNumber = 5
        } else {
            minNumber = arguments?.getInt(ARG_MIN_NUMBER) ?: 0
            maxNumber = arguments?.getInt(ARG_MAX_NUMBER) ?: 10
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initCustomOnBackPressed()
        saveData()
        showAd()
    }

    private fun init() {
        viewModel.record.observe(viewLifecycleOwner) {
            binding.tvBest.text = it.toString()
        }
        viewModel.coins.observe(viewLifecycleOwner) {
            binding.coins.text = it.toString()
        }
        earnedCoins = correctAnswers * 3
        binding.tvEarnedCoins.text = earnedCoins.toString()
        binding.correctAnswers.text = correctAnswers.toString()
        binding.wrongAnswers.text = wrongAnswers.toString()
        binding.tvCongratulations.text =
            if (correctAnswers > wrongAnswers) getText(R.string.perfect)
            else if (correctAnswers < wrongAnswers) getText(R.string.tryAgain)
            else getText(R.string.good)

        binding.btnAgain.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
            findNavController().navigate(
                R.id.game60secFragment, bundleOf(
                    ARG_MIN_NUMBER to minNumber,
                    ARG_MAX_NUMBER to maxNumber,
                    ARG_OPERATION to operation
                )
            )
        }
        binding.btnHome.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
        }
    }

    private fun saveData() {
        if (isGame) viewModel.saveResult(correctAnswers)
        viewModel.addCoins(amount = earnedCoins)
    }

    private fun showAd() {
        handler.postDelayed(
            { viewModel.showInterstitialAd() },
            1000
        )
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