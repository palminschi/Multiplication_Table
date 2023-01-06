package com.palmdev.learn_math.presentation.screens.games.game_2048

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.palmdev.learn_math.R
import com.palmdev.learn_math.data.games.game2048.StateHandler
import com.palmdev.learn_math.databinding.DialogContinue2048Binding
import org.koin.androidx.viewmodel.ext.android.viewModel

class Continue2048DialogFragment : DialogFragment() {


    private val viewModel: Continue2048ViewModel by viewModel()
    private lateinit var binding : DialogContinue2048Binding
    private val stateHandler = StateHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogContinue2048Binding.inflate(layoutInflater, container, false)
        dialog?.window?.decorView?.setBackgroundColor(resources.getColor(R.color.transparent, null))
        dialog?.setCancelable(false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnContinue.setOnClickListener {
            viewModel.showAd()
            stateHandler.continuingGame = true
            dialog?.dismiss()
        }
    }

}