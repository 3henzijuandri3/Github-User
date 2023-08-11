package com.dicoding.githubuser.Ui

import com.dicoding.githubuser.Adapter.ListFollowAdapter
import com.dicoding.githubuser.Adapter.User
import com.dicoding.githubuser.ApiSettings.FollowResponseItem
import com.dicoding.githubuser.ViewModels.FollowViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.Helper.ViewModelFactory
import com.dicoding.githubuser.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    companion object {
        var ARG_POSITION = "1"
        var ARG_USERNAME = "username"
        var KEY_USERNAME : String ?= null
    }

    private var _fragmentFollowBinding : FragmentFollowBinding ?= null
    private val binding get() = _fragmentFollowBinding

    private lateinit var followViewModel: FollowViewModel

    private var position : Int ?= null
    private var username : String ?= null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _fragmentFollowBinding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Proses inisiasi posisi tab dan username
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        KEY_USERNAME = username

        // Pembuatan RecylerView
        val layoutManager = LinearLayoutManager(requireActivity())
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding?.rvUserFollow?.layoutManager = layoutManager
        binding?.rvUserFollow?.addItemDecoration(itemDecoration)

        // Pembuatan View Model
        followViewModel = obtainViewModel(requireContext() as AppCompatActivity)

        if(position == 1){

            followViewModel.followerResponse.observe(viewLifecycleOwner) { followerResponse ->
                setUserData(followerResponse)
            }

        } else {
            followViewModel.followingResponse.observe(viewLifecycleOwner) { followingResponse ->
                setUserData(followingResponse)
            }
        }

        followViewModel.isLoading.observe(viewLifecycleOwner) { showLoading(it) }
    }

    private fun setUserData(user : List<FollowResponseItem>){
        val listUser = getFollowGithub(user)
        val adapter = ListFollowAdapter(listUser)
        binding?.rvUserFollow?.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): FollowViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FollowViewModel::class.java)
    }

    private fun getFollowGithub(userGithub: List<FollowResponseItem>): ArrayList<User> {
        val listUser = ArrayList<User>()

        for(userData in userGithub){
            val user = User(userData.login, userData.avatarUrl)
            listUser.add(user)
        }

        return listUser
    }

    private fun showLoading(state: Boolean) { binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE }
}




































