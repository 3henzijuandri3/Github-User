package com.dicoding.githubuser.ApiSettings

import com.google.gson.annotations.SerializedName

data class FollowResponse(

	@field:SerializedName("FollowResponse")
	val followResponse: List<FollowResponseItem>
)

data class FollowResponseItem(

	@field:SerializedName("repos_url")
	val reposUrl: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,
)
