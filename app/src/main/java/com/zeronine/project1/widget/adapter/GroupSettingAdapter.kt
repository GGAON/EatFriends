package com.zeronine.project1.widget.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeronine.project1.databinding.ItemGroupsettingBinding
import com.zeronine.project1.widget.model.GroupSettingModel

class GroupSettingAdapter :
    ListAdapter<GroupSettingModel, GroupSettingAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemGroupsettingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(groupSettingModel: GroupSettingModel) {
            if(groupSettingModel.recruiting == 1) { // 이 공동구매 세팅이 진행중(1)이라면 RecyclerView 에서 보여준다
                Log.d("Checking Recruiting", "It is ${groupSettingModel.recruiting}") //로그를 찍어서 True 를 잘 가져오는지 확인
                Log.d("GroupSettingAdapter", "${groupSettingModel.foodCategory}")
                binding.foodCategoryTextView.text = "Food category : ${groupSettingModel.foodCategory}"
                binding.totalPeopleTextView.text = "Total People : ${groupSettingModel.totalPeople}"
                binding.waitingTimeTextView.text = "Waiting Time : ${groupSettingModel.waitingTime} min"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGroupsettingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<GroupSettingModel>() {
            override fun areItemsTheSame(
                oldItem: GroupSettingModel,
                newItem: GroupSettingModel
            ): Boolean {
                //고유키값이 같은지 확인
                return oldItem.groupSettingId == newItem.groupSettingId
            }

            override fun areContentsTheSame(
                oldItem: GroupSettingModel,
                newItem: GroupSettingModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}