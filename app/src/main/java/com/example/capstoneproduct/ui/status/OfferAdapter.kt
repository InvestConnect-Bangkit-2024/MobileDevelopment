package com.example.capstoneproduct.ui.status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproduct.R
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
            binding.tvSector.text = offer.amount.toString()
            binding.tvName.text = offer.companyName
            Glide.with(binding.root.context)
                .load(offer.imgUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.baseline_broken_image_24)
                .into(binding.imgView)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<DataItem>() {
        override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem.userId == newItem.userId // Assuming DataItem has an id property
        }

        override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
            return oldItem == newItem
        }
    }
}