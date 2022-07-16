package com.palmdev.learn_math.presentation.screens.select_table

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.ItemNumberBinding
import com.palmdev.learn_math.presentation.screens.learn_table.LearnTableFragment

class NumberAdapter(private val maxNumber: Int) :
    RecyclerView.Adapter<NumberAdapter.NumberHolder>() {


    class NumberHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemNumberBinding.bind(view)

        fun bind(position: Int) {

            binding.tvNumber.text = (position + 1).toString()

            binding.root.setOnClickListener {
                it.findNavController().navigate(
                    R.id.action_selectTableFragment_to_learnTableFragment,
                    bundleOf(LearnTableFragment.ARG_SELECTED_NUMBER to position + 1)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_number, null)
        return NumberHolder(view)
    }

    override fun onBindViewHolder(holder: NumberHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return maxNumber
    }
}