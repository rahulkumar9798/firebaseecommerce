package com.example.firebaseecommerce.BottomNavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.firebaseecommerce.databinding.FragmentBottomCartBinding

class BottomCartFragment : Fragment() {
    lateinit var binding: FragmentBottomCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBottomCartBinding.inflate(layoutInflater, container, false)


        // Inflate the layout for this fragment
        return binding.root
    }


}