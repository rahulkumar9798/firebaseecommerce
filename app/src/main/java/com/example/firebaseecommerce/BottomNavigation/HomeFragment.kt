package com.example.firebaseecommerce.BottomNavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseecommerce.CategoryModal
import com.example.firebaseecommerce.R
import com.example.firebaseecommerce.RecyclerCategoryAdapter
import com.example.firebaseecommerce.RecyclerSpecialAdpter
import com.example.firebaseecommerce.Specialoffer_Model
import com.example.firebaseecommerce.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)


        var arrCatList =ArrayList<CategoryModal>().apply {
            add(CategoryModal(R.drawable.shoes, "shose"))
            add(CategoryModal(R.drawable.shoes, "shose"))
            add(CategoryModal(R.drawable.shoes, "shose"))
            add(CategoryModal(R.drawable.shoes, "shose"))
            add(CategoryModal(R.drawable.shoes, "shose"))
            add(CategoryModal(R.drawable.shoes, "shose"))
        }

        binding.recyclerCategory.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.recyclerCategory.adapter = RecyclerCategoryAdapter(requireContext(), arrCatList)



        var arrProductList = ArrayList<Specialoffer_Model>().apply {

            add(Specialoffer_Model(R.drawable.earbut, "wireless HeadPhone", "Rs 500/-"))
            add(Specialoffer_Model(R.drawable.earbut, "wireless HeadPhone", "Rs 500/-"))
            add(Specialoffer_Model(R.drawable.earbut, "wireless HeadPhone", "Rs 500/-"))
            add(Specialoffer_Model(R.drawable.earbut, "wireless HeadPhone", "Rs 500/-"))
            add(Specialoffer_Model(R.drawable.earbut, "wireless HeadPhone", "Rs 500/-"))
            add(Specialoffer_Model(R.drawable.earbut, "wireless HeadPhone", "Rs 500/-"))

        }


        binding.recyclerSpecial.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerSpecial.adapter = RecyclerSpecialAdpter(requireContext(), arrProductList)



        // Inflate the layout for this fragment
        return binding.root
    }


}