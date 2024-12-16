package com.example.capstoneproduct.ui.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstoneproduct.R
import com.example.capstoneproduct.data.umkm.DataItem
import com.example.capstoneproduct.databinding.RvRowBinding
import com.example.capstoneproduct.ui.details.DetailsUmkmActivity

class UmkmAdapter : ListAdapter<DataItem, UmkmAdapter.ListViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = RvRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailsUmkmActivity::class.java)
            intent.putExtra("UMKM_ID", data.umkmId)

            intent.putExtra("companyName", data.companyName)
            intent.putExtra("location", data.location)
            intent.putExtra("sector", data.sector)
            intent.putExtra("description", data.description)
            intent.putExtra("foundingDate", data.foundingDate)
            intent.putExtra("preferredInvestmentType", data.preferredInvestmentType)
            intent.putExtra("stage", data.stage)
            intent.putExtra("founderName", data.founderName)
            intent.putExtra("amountSeeking", data.amountSeeking)
            intent.putExtra("intendedUseOfFunds", data.intendedUseOfFunds)
            intent.putExtra("img", data.imgUrl)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    class ListViewHolder(private val binding: RvRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItem) {
            binding.tvName.text = data.companyName
            binding.tvSector.text = data.sector
            Glide.with(binding.root.context)
                .load(data.imgUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.baseline_broken_image_24)
                .into(binding.imgView)
            Log.d("ListViewHolder", "Image URL: ${data.imgUrl}")
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}