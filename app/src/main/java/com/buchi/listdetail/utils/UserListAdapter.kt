package com.buchi.listdetail.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.buchi.listdetail.data.model.MainEntity
import com.buchi.listdetail.databinding.ItemUserBinding
import com.bumptech.glide.Glide

class UserListAdapter(private val listListener: UserListListener) : ListAdapter<MainEntity.User, UserListAdapter.UserListViewHolder>(diffConfig) {
    companion object {
        val diffConfig = object : DiffUtil.ItemCallback<MainEntity.User>() {
            override fun areItemsTheSame(
                oldItem: MainEntity.User,
                newItem: MainEntity.User
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: MainEntity.User,
                newItem: MainEntity.User
            ): Boolean {
                return oldItem == newItem
            }
        }

        fun from(parent: ViewGroup): UserListViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemUserBinding.inflate(layoutInflater, parent, false)
            return UserListViewHolder(binding)
        }
    }

    class UserListViewHolder(private val itemUserBinding: ItemUserBinding): RecyclerView.ViewHolder(itemUserBinding.root) {

        fun bind(user: MainEntity.User) {
            itemUserBinding.userName.text = "${user.firstName} ${user.lastName}"
            itemUserBinding.userEmail.text = user.email
//            itemUserBinding.textView3.text = user.email
            Glide.with(itemUserBinding.root)
                .load(user.picture)
//                .placeholder(R.layout.)
                .into(itemUserBinding.userPicture)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        return from(parent)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            listListener.onItemClicked(getItem(position))
        }
    }

    interface UserListListener{
        fun onItemClicked(item: MainEntity.User)
    }
}