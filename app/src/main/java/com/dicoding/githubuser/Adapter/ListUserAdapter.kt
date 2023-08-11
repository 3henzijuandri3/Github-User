package com.dicoding.githubuser.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.Ui.DetailActivity
import com.dicoding.githubuser.R
import com.dicoding.githubuser.databinding.ItemRowUserBinding
import de.hdodenhof.circleimageview.CircleImageView

@Suppress("DEPRECATION")
class ListUserAdapter(private val listUser : List<User>) : RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        val userImage : CircleImageView = binding.imgItemPhoto
        val userName : TextView = binding.tvItemName
        val userLinkProfile: TextView = binding.tvItemProfile
        val cardView : CardView = binding.cardView
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListUserAdapter.ViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listUser.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, imageUrl) = listUser[position]

        holder.userName.text = name
        holder.userLinkProfile.text = getUserLink(name)

        Glide.with(holder.itemView)
            .load(imageUrl)
            .into(holder.userImage)

        holder.cardView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,
            R.anim.scroll_animation
        ))

        holder.itemView.setOnClickListener{
            val intentToDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentToDetail.putExtra(DetailActivity.USERNAME, listUser[holder.adapterPosition].namaUser)
            holder.itemView.context.startActivity(intentToDetail)
        }
    }

    private fun getUserLink(name: String) = "Link Profile : https://github.com/$name"

}


























