package com.palmdev.learn_math.presentation.dialogs.review

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentReviewDialogBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReviewDialogFragment : DialogFragment() {


    private val viewModel: ReviewDialogViewModel by viewModel()
    private lateinit var binding: FragmentReviewDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.decorView?.setBackgroundColor(resources.getColor(R.color.transparent, null))
        binding = FragmentReviewDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.star1.setOnClickListener {
            dialog?.dismiss()
            viewModel.setUserRatedApp()
        }
        binding.star2.setOnClickListener {
            dialog?.dismiss()
            viewModel.setUserRatedApp()
        }
        binding.star3.setOnClickListener {
            dialog?.dismiss()
            viewModel.setUserRatedApp()
        }
        binding.star4.setOnClickListener {
            dialog?.dismiss()
            viewModel.rateApp()
            viewModel.setUserRatedApp()
        }
        binding.star5.setOnClickListener {
            dialog?.dismiss()
            viewModel.rateApp()
            viewModel.setUserRatedApp()
        }
    }


}