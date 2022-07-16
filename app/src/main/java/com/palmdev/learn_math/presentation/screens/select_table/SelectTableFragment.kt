package com.palmdev.learn_math.presentation.screens.select_table

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentSelectTableBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SelectTableFragment : Fragment() {

    private lateinit var binding: FragmentSelectTableBinding
    private val viewModel: SelectTableViewModel by viewModel()
    private val numberAdapter by lazy { NumberAdapter(maxNumber = 30) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectTableBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        binding.recyclerView.adapter = numberAdapter
    }

}