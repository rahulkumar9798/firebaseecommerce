package com.example.firebaseecommerce

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebaseecommerce.databinding.ProfileImgRowBinding
import com.squareup.picasso.Picasso

class RecyclerProfilePicAdapter(val context: Context, val arrProimg : ArrayList<UserProfilePicModel>) : RecyclerView.Adapter<RecyclerProfilePicAdapter.ViewHolder>() {
    class ViewHolder (val binding: ProfileImgRowBinding) : RecyclerView.ViewHolder(binding.root)  {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ProfileImgRowBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return arrProimg.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(arrProimg[position].imgPath).into(holder.binding.proImg)

        //Glide.with(requireContext()).load(imgpath).into(binding.imageView)
       // Picasso.get().load(arrProimg[position].img!!.toString()).into(holder.binding.proImg)
    }
}