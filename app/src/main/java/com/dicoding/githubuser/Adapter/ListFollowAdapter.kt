package com.dicoding.githubuser.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.databinding.ItemRowUserBinding
import de.hdodenhof.circleimageview.CircleImageView

class ListFollowAdapter(private val listFollow : List<User>) : RecyclerView.Adapter<ListFollowAdapter.ViewHolder>() {

    inner class ViewHolder(binding : ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        val userImage : CircleImageView = binding.imgItemPhoto
        val userName : TextView = binding.tvItemName
        val userLinkProfile: TextView = binding.tvItemProfile
    }

    override fun getItemCount() = listFollow.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, imageUrl) = listFollow[position]

        holder.userName.text = name
        holder.userLinkProfile.text = getUserLink(name)
        Glide.with(holder.itemView)
            .load(imageUrl)
            .into(holder.userImage)
    }

    private fun getUserLink(name: String) = "Link Profile : https://github.com/$name"

}