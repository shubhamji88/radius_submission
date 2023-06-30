package com.example.radius.screens.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.radius.R
import com.example.radius.data.model.OptionModel
import com.example.radius.databinding.OptionsItemBinding

class OptionsAdapter(
    private val onOptionClick: (String, String) -> Boolean, private val facilityId: String,
    private val onOptionUncheck: (String, String) -> Unit
) :
    ListAdapter<OptionModel, OptionsAdapter.ViewHolder>(OptionsRecyclerViewDiffCallBack()) {


    class OptionsRecyclerViewDiffCallBack : DiffUtil.ItemCallback<OptionModel>() {
        override fun areItemsTheSame(oldItem: OptionModel, newItem: OptionModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OptionModel, newItem: OptionModel): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder private constructor(private val binding: OptionsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private fun getImageDrawable(icon: String): Int {
            return when (icon) {
                "apartment" -> R.drawable.apartment
                "condo" -> R.drawable.condo
                "boat" -> R.drawable.boat
                "land" -> R.drawable.land
                "rooms" -> R.drawable.rooms
                "no-room" -> R.drawable.no_room
                "swimming" -> R.drawable.swimming
                "garage" -> R.drawable.garage
                else -> R.drawable.apartment
            }
        }

        fun bind(
            item: OptionModel,
            onOptionClick: (String, String) -> Boolean,
            facilityId: String,
            onOptionUncheck: (String, String) -> Unit
        ) {
            binding.nameTV.text = item.name
            binding.rootCV.setBackgroundColor(Color.parseColor("#ADD8E6"))

            binding.iconIV.setImageResource(getImageDrawable(item.icon))

            binding.rootCV.setOnClickListener {
                val backgroundColor = (binding.rootCV.background as? ColorDrawable)?.color
                val checkColor = Color.parseColor("#00FF00")
                if (backgroundColor == checkColor) {
                    binding.rootCV.setBackgroundColor(Color.parseColor("#ADD8E6"))
                    onOptionUncheck(facilityId, item.id)
                } else if (onOptionClick(facilityId, item.id)) {
                    binding.rootCV.setBackgroundColor(checkColor)
                }
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = OptionsItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onOptionClick, facilityId, onOptionUncheck)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
}