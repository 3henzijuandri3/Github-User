package com.dicoding.githubuser.Database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favUser : FavoriteUser)

    @Update
    fun update(favUser : FavoriteUser)

    @Delete
    fun delete(favUser : FavoriteUser)

    @Query("SELECT * from FavoriteUser ORDER BY id ASC")
    fun getAllFavUser() : LiveData<List<FavoriteUser>>

    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser>
}






















