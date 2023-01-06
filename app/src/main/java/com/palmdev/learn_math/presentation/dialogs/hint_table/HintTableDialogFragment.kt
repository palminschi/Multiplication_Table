package com.palmdev.learn_math.presentation.dialogs.hint_table

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.DialogFragmentHintTableBinding
import com.palmdev.learn_math.utils.ARG_OPERATION
import com.palmdev.learn_math.utils.ARG_WITH_NUMBER
import com.palmdev.learn_math.utils.Operation
import org.koin.androidx.viewmodel.ext.android.viewModel

class HintTableDialogFragment : DialogFragment() {

    private val viewModel: HintTableViewModel by viewModel()
    private lateinit var binding: DialogFragmentHintTableBinding
    private var withNumber = 1
    private var operation = Operation.MULTIPLICATION

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.decorView?.setBackgroundColor(resources.getColor(R.color.transparent, null))
        binding = DialogFragmentHintTableBinding.inflate(layoutInflater, container, false)
        withNumber = arguments?.getInt(ARG_WITH_NUMBER) ?: 1
        operation = arguments?.getSerializable(ARG_OPERATION) as Operation
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTable(withNumber, operation)
        viewModel.table.observe(viewLifecycleOwner) {
            binding.tvTable.text = it
        }
        binding.btnClose.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}