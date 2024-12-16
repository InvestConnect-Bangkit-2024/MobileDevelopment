package com.example.capstoneproduct.ui.status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproduct.data.response.request.DataItem
import com.example.capstoneproduct.databinding.RvRowBinding

class OfferAdapter : ListAdapter<DataItem, OfferAdapter.OfferViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferViewHolder {
        val binding = RvRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OfferViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OfferViewHolder, position: Int) {
        val offer = getItem(position)
        holder.bind(offer)
    }

    class OfferViewHolder(private val binding: RvRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(offer: DataItem) {
            // Bind your data to the views here
            binding.tvSector.text = offer.createdAt// Example binding
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem // Assuming DataItem has an id property
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }
}