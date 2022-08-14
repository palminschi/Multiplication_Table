package com.palmdev.learn_math.presentation.dialogs.continue_game

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.DialogContinueBinding
import com.palmdev.learn_math.presentation.animations.ExpansionReductionAnim
import com.palmdev.learn_math.utils.ARG_BEST_SCORE
import com.palmdev.learn_math.utils.ARG_CONTINUE_WITH_SCORE
import org.koin.androidx.viewmodel.ext.android.viewModel

class ContinueDialogFragment : DialogFragment() {

    private val viewModel: ContinueViewModel by viewModel()
    private lateinit var binding: DialogContinueBinding
    private var continueScore = 0
    private var bestScore: Int? = null
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogContinueBinding.inflate(layoutInflater, container, false)
        continueScore = arguments?.getInt(ARG_CONTINUE_WITH_SCORE, 0) ?: 0
        bestScore = arguments?.getInt(ARG_BEST_SCORE, 0)
        dialog?.window?.decorView?.setBackgroundColor(resources.getColor(R.color.transparent, null))
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setCancelable(false)

        binding.tvCorrectAnswers.text = "${getText(R.string.correctAnswers)} $continueScore"
        binding.tvBestScore.text =
            if (bestScore == null) "${getText(R.string.best)} $continueScore"
            else if (bestScore!! < continueScore) "${getText(R.string.best)} $continueScore"
            else "${getText(R.string.best)} $bestScore"

        binding.btnYes.setOnClickListener {
            viewModel.showRewardedAd {
                findNavController().previousBackStackEntry?.destination?.id?.let { destinationId ->
                    val navOptions =
                        NavOptions.Builder().setPopUpTo(R.id.selectGameFragment, false).build()
                    findNavController().navigate(
                        destinationId,
                        bundleOf(ARG_CONTINUE_WITH_SCORE to continueScore),
                        navOptions
                    )
                }
            }
        }
        binding.btnNo.setOnClickListener {
            findNavController().popBackStack(R.id.selectGameFragment, false)
            viewModel.showInterstitialAd()
        }

        ExpansionReductionAnim.anim(binding.ivHeart, true)
        ExpansionReductionAnim.anim(binding.btnYes, true)

        handler.postDelayed({
            binding.btnNo.visibility = View.VISIBLE
        }, 2500)
    }

}