package com.dicoding.githubuser.ApiSettings

import com.google.gson.annotations.SerializedName

data class UserResponse(
	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

data class ItemsItem(

	@field:SerializedName("repos_url")
	val reposUrl: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

)
