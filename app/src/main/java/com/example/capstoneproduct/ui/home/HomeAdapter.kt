package com.example.capstoneproduct.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproduct.R
import com.example.capstoneproduct.data.response.home.CompaniesItem
import com.example.capstoneproduct.databinding.RvRowBinding

class HomeAdapter : ListAdapter<CompaniesItem, HomeAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = RvRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val companyItem = getItem(position)
        holder.bind(companyItem)
    }

    class ListViewHolder(private val binding: RvRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CompaniesItem) {
            binding.tvName.text = item.name
            binding.tvSector.text = item.sector
            Glide.with(binding.root.context)
                .load(item.imgURL)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.baseline_broken_image_24)
                .into(binding.imgView)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CompaniesItem>() {
            override fun areItemsTheSame(oldItem: CompaniesItem, newItem: CompaniesItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CompaniesItem, newItem: CompaniesItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}