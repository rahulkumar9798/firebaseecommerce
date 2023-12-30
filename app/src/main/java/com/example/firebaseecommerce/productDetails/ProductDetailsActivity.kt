package com.example.firebaseecommerce.productDetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseecommerce.R
import com.example.firebaseecommerce.databinding.ActivityProductDetailsBinding
import com.google.android.material.tabs.TabLayoutMediator

class ProductDetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductDetailsBinding

    val arrColors = ArrayList<ColorModal>().apply {
        add(ColorModal("F57C00", R.color.orange))
        add(ColorModal("7B1FA2", R.color.purple))
        add(ColorModal("1976D2", R.color.blue))
        add(ColorModal("ACA298", R.color.grey))
        add(ColorModal("C2185B", R.color.pink))
        add(ColorModal("D32F2F", R.color.red))
        add(ColorModal("FFFFFF", R.color.white))
        add(ColorModal("000000", R.color.black))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerColor.apply {
            layoutManager = LinearLayoutManager(this@ProductDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = RecyclerColorAdapter(this@ProductDetailsActivity, arrColors)
        }



        binding.viewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        TabLayoutMediator(binding.tabLayout, binding.viewPager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Description"
                }
                1 -> {
                    tab.text = "Specifications"
                }
                else -> {
                    tab.text = "Reviews"
                }
            }
        }.attach()

        binding.viewPager.currentItem = 0


    }
}