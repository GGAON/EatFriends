package com.zeronine.project1.widget.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeronine.project1.databinding.ItemGroupsettingBinding
import com.zeronine.project1.databinding.ItemMemberBinding
import com.zeronine.project1.widget.model.GroupSettingModel
import com.zeronine.project1.widget.model.MemberModel

class MemberAdapter
    : ListAdapter<MemberModel,MemberAdapter.ViewHolder >(diffUtil) {


    inner class ViewHolder(private val binding: ItemMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(MemberModel: MemberModel) {
                Log.d("Checking Recruiting", "It is ${MemberModel.MemberId}")
//                binding.memberNameView.text = ""
        }
    }



    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<MemberModel>() {
            override fun areItemsTheSame(
                oldItem: MemberModel,
                newItem: MemberModel
            ): Boolean {
                //고유키값이 같은지 확인
                return oldItem.MemberId == newItem.MemberId
            }

            override fun areContentsTheSame(
                oldItem: MemberModel,
                newItem: MemberModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMemberBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}