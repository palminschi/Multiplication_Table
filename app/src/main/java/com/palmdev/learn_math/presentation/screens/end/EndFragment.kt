package com.palmdev.learn_math.presentation.screens.end

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.databinding.FragmentEndBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EndFragment : Fragment() {

    private val viewModel: EndViewModel by viewModel()
    private lateinit var binding: FragmentEndBinding
    private var customOnBackPressed: OnBackPressedCallback? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEndBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
            findNavController().popBackStack()
        }
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