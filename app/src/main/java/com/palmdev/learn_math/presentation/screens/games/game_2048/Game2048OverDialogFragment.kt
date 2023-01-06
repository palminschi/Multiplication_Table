package com.palmdev.learn_math.presentation.screens.games.game_2048

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.DialogGame2048OverBinding
import com.palmdev.learn_math.utils.ARG_START_NEW_GAME
import org.koin.androidx.viewmodel.ext.android.viewModel

class Game2048OverDialogFragment : DialogFragment() {

    private val viewModel: Game2048OverViewModel by viewModel()
    private lateinit var binding: DialogGame2048OverBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogGame2048OverBinding.inflate(layoutInflater, container, false)
        dialog?.window?.decorView?.setBackgroundColor(resources.getColor(R.color.transparent, null))
        dialog?.setCancelable(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNewGame.setOnClickListener {
            viewModel.showRewardedAd()
            findNavController().navigate(
                R.id.game2048Fragment,
                bundleOf(ARG_START_NEW_GAME to true),
                NavOptions.Builder().setPopUpTo(R.id.mainFragment, false).build()
            )
            dialog?.dismiss()
        }


        binding.undoButton.setOnClickListener {
            // TODO: UNDO
        }
    }

}