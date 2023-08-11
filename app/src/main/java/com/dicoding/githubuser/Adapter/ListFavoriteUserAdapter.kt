package com.dicoding.githubuser.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.databinding.ItemRowFavUserBinding
import com.dicoding.githubuser.Adapter.ListFavoriteUserAdapter.FavUserViewHolder
import com.dicoding.githubuser.Database.FavoriteUser
import com.dicoding.githubuser.Ui.DetailActivity
import de.hdodenhof.circleimageview.CircleImageView

@Suppress("DEPRECATION")
class ListFavoriteUserAdapter(private val listFavUser : List<FavoriteUser>) : RecyclerView.Adapter<FavUserViewHolder>() {

    inner class FavUserViewHolder(binding : ItemRowFavUserBinding) : RecyclerView.ViewHolder(binding.root){
        val userImage : CircleImageView = binding.imgItemPhoto
        val userName : TextView = binding.tvItemName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavUserViewHolder {
        val binding = ItemRowFavUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavUserViewHolder(binding)
    }

    override fun getItemCount(): Int = listFavUser.size

    override fun onBindViewHolder(holder: FavUserViewHolder, position: Int) {
        val (id, name, imageUrl) = listFavUser[position]

        holder.userName.text = name
        Glide.with(holder.itemView)
            .load(imageUrl)
            .into(holder.userImage)

        holder.itemView.setOnClickListener{
            val intentToDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentToDetail.putExtra(DetailActivity.USERNAME, listFavUser[holder.adapterPosition].username)
            holder.itemView.context.startActivity(intentToDetail)
        }
    }
}
















