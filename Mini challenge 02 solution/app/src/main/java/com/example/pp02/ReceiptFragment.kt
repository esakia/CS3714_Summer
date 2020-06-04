package com.example.pp02


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders


/**
 * A simple [Fragment] subclass.
 *
 */
class ReceiptFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


      val v = inflater.inflate(R.layout.fragment_receipt, container, false)
        val model = activity?.run {
            ViewModelProviders.of(this).get(MyViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        (v.findViewById(R.id.receipt) as TextView).text = getString(R.string.order_total)+model.orderTotal+"\n"+getString(R.string.balance_remaining)+model.cashInWallet
        model.finishTransaction()
        return v
    }


}
