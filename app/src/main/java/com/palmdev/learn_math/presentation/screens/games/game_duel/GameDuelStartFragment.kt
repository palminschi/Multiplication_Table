package com.palmdev.learn_math.presentation.screens.games.game_duel

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentGameDuelStartBinding
import com.palmdev.learn_math.utils.*

class GameDuelStartFragment : Fragment() {

    private lateinit var binding: FragmentGameDuelStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameDuelStartBinding.inflate(layoutInflater, container, false)
        FirebaseEvents().setScreenViewEvent(screenName = "Duel Start")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.easy.isChecked = true
        binding.multiply.isChecked = true

        binding.btnStart.setOnClickListener {
            var operation = Operation.MULTIPLICATION
            var minNumber = 0
            var maxNumber = 10

            if (binding.multiply.isChecked) operation = Operation.MULTIPLICATION
            else if (binding.divide.isChecked) operation = Operation.DIVISION
            else if (binding.plus.isChecked) operation = Operation.ADDITION
            else if (binding.minus.isChecked) operation = Operation.SUBTRACTION

            if (binding.easy.isChecked) {
                minNumber = 0
                maxNumber = 10
            }
            else if (binding.medium.isChecked) {
                minNumber = 8
                maxNumber = 20
            }
            else if (binding.hard.isChecked) {
                minNumber = 11
                maxNumber = 99
            }

            findNavController().navigate(
                R.id.action_gameDuelStartFragment_to_gameDuelFragment,
                bundleOf(
                    ARG_OPERATION to operation,
                    ARG_MIN_NUMBER to minNumber,
                    ARG_MAX_NUMBER to maxNumber
                )
            )
        }
    }
}