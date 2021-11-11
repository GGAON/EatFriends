package com.zeronine.project1.widget.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zeronine.project1.databinding.ActivityOrderBinding
import com.zeronine.project1.databinding.ItemFoodBinding
import com.zeronine.project1.widget.model.FoodListModel

class FoodListAdapter :
    ListAdapter<FoodListModel, FoodListAdapter.ViewHolder>(diffUtil) {

    private lateinit var orderBinding: ActivityOrderBinding

    inner class ViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(foodListModel: FoodListModel) {
            binding.foodNameTextView.text = foodListModel.foodName
            binding.foodDetailTextView.text = foodListModel.foodDetail
            binding.priceTextView.text = "${foodListModel.foodPrice} 원"
//            binding.itemFood.setOnClickListener{
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        orderBinding = ActivityOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(
            ItemFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<FoodListModel>() {
            override fun areItemsTheSame(
                oldItem: FoodListModel,
                newItem: FoodListModel
            ): Boolean {
                //고유키값이 같은지 확인
                return oldItem.foodId == newItem.foodId
            }

            override fun areContentsTheSame(
                oldItem: FoodListModel,
                newItem: FoodListModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}