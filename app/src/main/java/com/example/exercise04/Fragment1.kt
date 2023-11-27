package com.example.exercise04

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

private const val F1 = "param1"
private const val F2 = "param2"


class Fragment1 : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(F1)
            param2 = it.getString(F2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.f1_btn).setOnClickListener{ _ ->
            val value = view.findViewById<RatingBar>(R.id.ratingBar).rating
            parentFragmentManager.setFragmentResult("msgfromchild",
                bundleOf("msg1" to ("value from child = $value"))
            )
        }
    }

    companion object {
        fun newInstance(s: String, s1: String) =
            Fragment1().apply {
                arguments = Bundle().apply {
                    putString(F1, param1)
                    putString(F2, param2)
                }
            }
    }
}