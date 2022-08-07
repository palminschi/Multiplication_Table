package com.palmdev.learn_math.presentation.screens.games.select_game

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentSelectGameBinding
import com.palmdev.learn_math.utils.ARG_IS_GAME
import com.palmdev.learn_math.utils.ARG_MAX_NUMBER
import com.palmdev.learn_math.utils.ARG_MIN_NUMBER
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectGameFragment : Fragment() {

    private lateinit var binding: FragmentSelectGameBinding
    private val viewModel: SelectGameViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectGameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRecords()
        binding.btnGameDuel.setOnClickListener {
            findNavController().navigate(R.id.action_selectGameFragment_to_gameDuelStartFragment)
        }
        binding.btnGame60sec.setOnClickListener {
            findNavController().navigate(
                R.id.action_selectGameFragment_to_game60secFragment,
                bundleOf(
                    ARG_MIN_NUMBER to 0,
                    ARG_MAX_NUMBER to 5,
                    ARG_IS_GAME to true
                )
            )
        }
        viewModel.recordGame60sec.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.recordGame60sec.text = "${getText(R.string.best)} $it"
            } else {
                binding.recordGame60sec.text = "${getText(R.string.best)} ${getText(R.string.questionMark)}"
            }
        }
    }
}