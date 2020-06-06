package com.example.pp02

import androidx.lifecycle.ViewModel

class MyViewModel(var cashInWallet:Float = 25.0f, var orderTotal:Float=0f) : ViewModel() {

    //equivalent of 'public static float ...'
    companion object{
        const val orange_price = 4.99f
        const val apple_price = 2.49f
    }


     fun addOrder(transactionCost:Float) {
         orderTotal += transactionCost
         pay()

     }

    private fun pay(){ cashInWallet -= orderTotal }

    fun finishTransaction(){ orderTotal = 0f}
}