package com.example.exercise04

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.exercise04.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        val textHome: TextView = view.findViewById(R.id.text_home)
        val textView: TextView = view.findViewById(R.id.textView)

        sharedViewModel.sharedText.observe(viewLifecycleOwner) { newText ->
            textHome.text = newText
        }

        sharedViewModel.sharedText2.observe(viewLifecycleOwner) { newText ->
            textView.text = newText
        }

        val data = requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val selectedImage = data.getString("image", "")
        if (selectedImage != "") {
            Glide.with(this).load(selectedImage).into(binding.imageView2)
        } else {
            binding.imageView2.setImageResource(R.drawable.main_img)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
