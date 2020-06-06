package com.example.pp02


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.pp02.R.string.*


class ConfirmationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val v = inflater.inflate(R.layout.fragment_confirm, container, false)


       val model = activity?.run {
            ViewModelProviders.of(this).get(MyViewModel::class.java)
        } ?: throw Exception("Invalid Activity")


        // "%.2f".format(..) lets you round to 2 decimal places.
        // ?: is the 'Elvis' operator -- read here:https://kotlinlang.org/docs/reference/null-safety.html#elvis-operator
        val cost = "%.4f".format(arguments?.getFloat("cost") ?: 0f).toFloat()
        (v.findViewById(R.id.message) as TextView).text =
            "${this.getString(confirm_message)}$cost${this.getString(confirm_proceed)}"

        (v.findViewById(R.id.purchase) as Button).setOnClickListener{


            if(model.cashInWallet>cost){
                model.addOrder(cost)
                v.findNavController().navigate(R.id.action_confirmFragment_to_receiptFragment)
               }
            else{
                Toast.makeText(this.context, this.getString(low_funds), Toast.LENGTH_SHORT).show()
              }
        }

        return v
    }


}
