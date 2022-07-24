package com.palmdev.learn_math.presentation.screens.purchase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.palmdev.learn_math.R
import com.palmdev.learn_math.databinding.FragmentPurchaseBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PurchaseFragment : Fragment() {

    private lateinit var binding: FragmentPurchaseBinding
    private val viewModel: PurchaseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPurchaseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel.initCoins()
        viewModel.coins.observe(viewLifecycleOwner) {
            binding.coins.text = it.toString()
        }
        viewModel.isPremiumUser.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.tvGetPremium.text = getText(R.string.youArePremiumUser)
                    binding.tvPremiumInfo.visibility = View.GONE
                    binding.tvDisableAds.visibility = View.GONE
                    binding.btnBuyPremium.visibility = View.GONE
                    binding.btnBuyPremiumByCoins.visibility = View.GONE
                    binding.tvOR.visibility = View.GONE
                }
                false -> {
                    binding.tvGetPremium.text = getText(R.string.getPremium)
                    binding.tvPremiumInfo.visibility = View.VISIBLE
                    binding.tvDisableAds.visibility = View.VISIBLE
                    binding.btnBuyPremium.visibility = View.VISIBLE
                    binding.btnBuyPremiumByCoins.visibility = View.VISIBLE
                    binding.tvOR.visibility = View.VISIBLE
                }
            }
        }
        binding.btnBuyPremium.setOnClickListener {
            viewModel.buyPremium()
        }
        binding.btnBuyPremiumByCoins.setOnClickListener {
            viewModel.buyPremiumByCoins()
        }
    }

}