package com.dicoding.githubuser.Adapter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class User(
    var namaUser : String,
    var image : String,
): Parcelable