package com.example.pp02


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController


class OrderFragment : Fragment(),View.OnClickListener {


    private lateinit var model: MyViewModel



    private lateinit var orangeCountTextView: TextView
    private lateinit var appleCountTextView: TextView
    private lateinit var walletTextView: TextView
    private lateinit var viewF: View
    private var apples = 0
    private var oranges = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       viewF = inflater.inflate(R.layout.fragment_order, container, false)
        setUpButtonUpListeners(viewF)

        oranges=0
        apples=0
        orangeCountTextView = (viewF.findViewById(R.id.orangeCount) as TextView)
        appleCountTextView = (viewF.findViewById(R.id.appleCount) as TextView)
        walletTextView = (viewF.findViewById(R.id.wallet) as TextView)

        model = activity?.run {
            ViewModelProviders.of(this).get(MyViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        walletTextView.text = resources.getString(R.string.wallet)+model.cashInWallet.toString()
        return viewF
    }

    // the entire fragment is registered as a listener for the buttons. Notice the 'when' block (read more about it here: (https://kotlinlang.org/docs/reference/control-flow.html#when-expression). This is
    // a shorter way of 'switch-case'
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.applePlus -> apples++
            R.id.appleMinus -> if(apples>0) apples--
            R.id.orangePlus -> oranges++
            R.id.orangeMinus -> if(oranges>0) oranges--
            R.id.order -> callNavigation(v)
        }
        appleCountTextView.text = apples.toString()
        orangeCountTextView.text = oranges.toString()
    }

    private fun callNavigation(v: View?) {


            val cost  = apples*MyViewModel.apple_price+oranges*MyViewModel.orange_price


        v?.findNavController()?.navigate(R.id.action_orderFragment_to_confirmFragment, bundleOf("cost" to cost.toFloat()))

    }



    // registering all of the buttons with a single listener.
    private fun setUpButtonUpListeners(v: View?){
        (v?.findViewById(R.id.applePlus) as Button).setOnClickListener(this)
        (v.findViewById(R.id.appleMinus) as Button).setOnClickListener(this)
        (v.findViewById(R.id.orangePlus) as Button).setOnClickListener(this)
        (v.findViewById(R.id.orangeMinus) as Button).setOnClickListener(this)
        (v.findViewById(R.id.order) as Button).setOnClickListener(this)
    }
}
