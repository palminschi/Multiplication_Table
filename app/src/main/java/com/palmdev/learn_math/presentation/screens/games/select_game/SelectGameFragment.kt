package com.palmdev.learn_math.presentation.screens.games.select_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentSelectGameBinding

class SelectGameFragment : Fragment() {

    private lateinit var binding: FragmentSelectGameBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectGameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGameDuel.setOnClickListener {
            findNavController().navigate(R.id.action_selectGameFragment_to_gameDuelStartFragment)
        }
    }
}