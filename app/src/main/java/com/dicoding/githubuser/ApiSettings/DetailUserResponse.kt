package com.dicoding.githubuser.ApiSettings

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(

	@field:SerializedName("gists_url")
	val gistsUrl: String,

	@field:SerializedName("repos_url")
	val reposUrl: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("public_gists")
	val publicGists: Int,

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String,
)
