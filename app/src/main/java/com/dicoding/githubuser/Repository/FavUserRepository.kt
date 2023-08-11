package com.dicoding.githubuser.Repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.githubuser.Database.FavUserDao
import com.dicoding.githubuser.Database.FavUserRoomDatabase
import com.dicoding.githubuser.Database.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavUserRepository(application: Application) {
    private val mFavUserDao : FavUserDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavUserRoomDatabase.getDatabase(application)
        mFavUserDao = db.favUserDao()
    }

    fun getAllFavUser() : LiveData<List<FavoriteUser>> = mFavUserDao.getAllFavUser()

    fun insert(favUser : FavoriteUser) = executorService.execute { mFavUserDao.insert(favUser) }

    fun update(favUser : FavoriteUser) = executorService.execute { mFavUserDao.update(favUser) }

    fun delete(favUser: FavoriteUser) = executorService.execute { mFavUserDao.delete(favUser) }

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = mFavUserDao.getFavoriteUserByUsername(username)

}
































