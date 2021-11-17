package com.zeronine.project1.widget.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeronine.project1.databinding.ItemFriendsBinding
import com.zeronine.project1.screen.friends.FriendsFragment
import com.zeronine.project1.widget.model.FriendsModel

class FriendsAdapter
    : ListAdapter<FriendsModel, FriendsAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemFriendsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(friendsModel: FriendsModel) {
            binding.friendNameView.text = friendsModel.FriendName

        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<FriendsModel>() {
            override fun areItemsTheSame(oldItem: FriendsModel, newItem: FriendsModel): Boolean {
                return oldItem.FriendId == newItem.FriendId
            }

            override fun areContentsTheSame(oldItem: FriendsModel, newItem: FriendsModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsAdapter.ViewHolder {
        return ViewHolder(
            ItemFriendsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FriendsAdapter.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
        holder.bind(currentList[position])
    }

    //Click Listener
    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    private lateinit var itemClickListener : OnItemClickListener
    fun setItemClickListener(itemClickListener:OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}