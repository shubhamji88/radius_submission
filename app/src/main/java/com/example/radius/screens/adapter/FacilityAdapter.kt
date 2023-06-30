package com.example.radius.screens.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.radius.data.model.FacilityModel
import com.example.radius.databinding.ItemRecyclerViewBinding

class FacilityAdapter(
    private val onOptionCheck: (String, String) -> Boolean,
    private val onOptionUncheck: (String, String) -> Unit
) :
    ListAdapter<FacilityModel, FacilityAdapter.ViewHolder>(FamRecyclerViewDiffCallBack()) {


    class FamRecyclerViewDiffCallBack : DiffUtil.ItemCallback<FacilityModel>() {
        override fun areItemsTheSame(oldItem: FacilityModel, newItem: FacilityModel): Boolean {
            return oldItem.facilityId == newItem.facilityId
        }

        override fun areContentsTheSame(oldItem: FacilityModel, newItem: FacilityModel): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder private constructor(private val binding: ItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: FacilityModel,
            onOptionCheck: (String, String) -> Boolean,
            onOptionUncheck: (String, String) -> Unit
        ) {
            binding.titleTV.text = item.name
            val optionsAdapter = OptionsAdapter(onOptionCheck, item.facilityId, onOptionUncheck)
            binding.optionsRV.adapter = optionsAdapter
            optionsAdapter.submitList(item.options.toList())

            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRecyclerViewBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onOptionCheck, onOptionUncheck)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}