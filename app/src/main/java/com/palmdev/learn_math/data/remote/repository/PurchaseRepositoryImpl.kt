package com.palmdev.learn_math.data.remote.repository

import android.content.Context
import android.widget.Toast
import com.android.billingclient.api.*
import com.palmdev.learn_math.domain.repository.UserDataRepository
import com.palmdev.learn_math.data.remote.utils.BillingSecurity
import com.palmdev.learn_math.domain.repository.PurchaseRepository
import com.palmdev.learn_math.utils.*
import java.io.IOException

class PurchaseRepositoryImpl(
    private val context: Context,
    private val userDataRepository: UserDataRepository
) : PurchaseRepository, PurchasesUpdatedListener {

    private val sharedPrefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    private var billingClient: BillingClient? = null

    override fun buyPremiumAccount() {
        if (billingClient?.isReady == true) {
            initiatePurchase()
        } else {
            billingClient = BillingClient.newBuilder(context).enablePendingPurchases()
                .setListener(this).build()
            billingClient!!.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        initiatePurchase()
                    } else {
                        Toast.makeText(
                            context,
                            "Error " + billingResult.debugMessage,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onBillingServiceDisconnected() {}
            })
        }
    }

    override fun buyPremiumAccountByCoins(price: Int): Boolean {
        val coins = userDataRepository.coins
        return if (coins >= price) {
            userDataRepository.setIsPremiumUser(true)
            userDataRepository.removeCoins(amount = price)
            true
        } else {
            false
        }
    }

    init {
        initPrice()
    }

    override fun getPrice(): String {
        return sharedPrefs.getString(PREMIUM_PRICE, "1.99$") ?: "1.99$"
    }

    private fun initPrice() {
        val skuList: MutableList<String> = ArrayList()
        skuList.add(PURCHASE_PRODUCT_ID)
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)
        billingClient?.querySkuDetailsAsync(params.build()) { _, skuDetailsList ->
            skuDetailsList?.get(0)?.price?.let {
                sharedPrefs.edit().putString(PREMIUM_PRICE, it).apply()
            }
        }
    }

    private fun initiatePurchase() {
        val skuList: MutableList<String> = ArrayList()
        skuList.add(PURCHASE_PRODUCT_ID)
        val params = SkuDetailsParams.newBuilder()
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP)
        billingClient!!.querySkuDetailsAsync(
            params.build()
        ) { billingResult, skuDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                if (skuDetailsList != null && skuDetailsList.size > 0) {
                    val flowParams = BillingFlowParams.newBuilder()
                        .setSkuDetails(skuDetailsList[0])
                        .build()
                    billingClient!!.launchBillingFlow(MAIN, flowParams)
                } else {
                    //try to add item/product id "purchase" inside managed product in google play console
                    Toast.makeText(
                        context,
                        "Purchase Item not Found",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    context,
                    " Error " + billingResult.debugMessage, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onPurchasesUpdated(billingResult: BillingResult, purchases: List<Purchase>?) {
        //if item newly purchased
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
            handlePurchases(purchases)
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            val queryAlreadyPurchasesResult =
                billingClient!!.queryPurchases(BillingClient.SkuType.INAPP)
            val alreadyPurchases = queryAlreadyPurchasesResult.purchasesList
            alreadyPurchases?.let { handlePurchases(it) }
        } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED) {
            Toast.makeText(context, "Purchase Canceled", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                context,
                "Error " + billingResult.debugMessage,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun handlePurchases(purchases: List<Purchase>) {
        for (purchase in purchases) {
            //if item is purchased
            if (PURCHASE_PRODUCT_ID == purchase.sku && purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                if (!verifyValidSignature(purchase.originalJson, purchase.signature)) {
                    // Invalid purchase
                    // show error to user
                    Toast.makeText(
                        context,
                        "Error : Invalid Purchase",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }
                // else purchase is valid
                //if item is purchased and not acknowledged
                if (!purchase.isAcknowledged) {
                    val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                        .setPurchaseToken(purchase.purchaseToken)
                        .build()
                    billingClient!!.acknowledgePurchase(acknowledgePurchaseParams, ackPurchase)
                } else {
                    // Grant entitlement to the user on item purchase
                    if (!userDataRepository.isPremiumUser) {
                        userDataRepository.setIsPremiumUser(true)
                        Toast.makeText(
                            context,
                            "Item Purchased",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else if (PURCHASE_PRODUCT_ID == purchase.sku && purchase.purchaseState == Purchase.PurchaseState.PENDING) {
                Toast.makeText(
                    context,
                    "Purchase is Pending. Please complete Transaction", Toast.LENGTH_SHORT
                ).show()
            } else if (PURCHASE_PRODUCT_ID == purchase.sku && purchase.purchaseState == Purchase.PurchaseState.UNSPECIFIED_STATE) {
                userDataRepository.setIsPremiumUser(false)
                //=-
                Toast.makeText(
                    context,
                    "Purchase Status Unknown",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private var ackPurchase = AcknowledgePurchaseResponseListener { billingResult ->
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            //if purchase is acknowledged
            // Grant entitlement to the user. and restart activity
            userDataRepository.setIsPremiumUser(true)
            Toast.makeText(context, "Item Purchased", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verifyValidSignature(signedData: String, signature: String): Boolean {
        return try {
            // To get key go to Developer Console > Select your app > Development Tools > Services & APIs.
            val base64Key = BASE64_KEY
            BillingSecurity.verifyPurchase(base64Key, signedData, signature)
        } catch (e: IOException) {
            false
        }
    }


}