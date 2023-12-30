package com.example.firebaseecommerce.BottomNavigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.firebaseecommerce.R
import com.example.firebaseecommerce.databinding.FragmentFavouriteBinding
import com.example.firebaseecommerce.productDetails.ProductDetailsActivity


class FavouriteFragment : Fragment() {
    lateinit var binding: FragmentFavouriteBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouriteBinding.inflate(layoutInflater,  container, false)


        // Inflate the layout for this fragment
        return binding.root
    }


}