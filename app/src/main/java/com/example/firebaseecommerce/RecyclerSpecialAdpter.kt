package com.example.firebaseecommerce

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseecommerce.databinding.SpecialofferRowBinding

class RecyclerSpecialAdpter(val context: Context, val arrProductData: ArrayList<Specialoffer_Model>): RecyclerView.Adapter<RecyclerSpecialAdpter.ViewHolder>() {
    class ViewHolder(val binding: SpecialofferRowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SpecialofferRowBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return arrProductData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.productImg.setImageResource(arrProductData[position].img)
        holder.binding.productName.text = arrProductData[position].productName
        holder.binding.productPrice.text = arrProductData[position].productPrice
    }
}