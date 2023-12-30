package com.example.firebaseecommerce.productDetails

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseecommerce.databinding.ColorRowBinding

class RecyclerColorAdapter(val context: Context, val arrColors : ArrayList<ColorModal>) : RecyclerView.Adapter<RecyclerColorAdapter.ViewHolder>(){
    class ViewHolder(val binding: ColorRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ColorRowBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return arrColors.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.cardColor.setCardBackgroundColor(context.getColor(arrColors[position].colorPath))
    }
}