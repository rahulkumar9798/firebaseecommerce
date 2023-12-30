package com.example.firebaseecommerce

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseecommerce.databinding.CategoryRowBinding

class RecyclerCategoryAdapter(val context: Context, val arrCatList:ArrayList<CategoryModal>) : RecyclerView.Adapter<RecyclerCategoryAdapter.ViewHolder>() {
    class ViewHolder (val binding: CategoryRowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CategoryRowBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return arrCatList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.binding.catImg.setImageResource(arrCatList[position].imgPath)
        holder.binding.catName.text =arrCatList[position].catName
    }
}