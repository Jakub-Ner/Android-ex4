package com.example.exercise04.ui.ViewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.exercise04.R
import com.example.exercise04.SharedViewModel

private const val F1 = "param1"
private const val F2 = "param2"

class Fragment2 : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var sharedViewModel: SharedViewModel

    // Get a reference to the shared HomeViewModel

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
        return inflater.inflate(R.layout.fragment_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val invitationField: EditText = view.findViewById(R.id.invitationField)
        val infoField: EditText = view.findViewById(R.id.infoField)

        // Update shared text when text in EditText changes
        invitationField.addTextChangedListener {
            sharedViewModel.updateText(it.toString())
        }

        infoField.addTextChangedListener {
            sharedViewModel.updateText2(it.toString())
        }

    }

    companion object {
        fun newInstance(s: String, s1: String) =
            Fragment2().apply {
                arguments = Bundle().apply {
                    putString(F1, param1)
                    putString(F2, param2)
                }
            }
    }
}
