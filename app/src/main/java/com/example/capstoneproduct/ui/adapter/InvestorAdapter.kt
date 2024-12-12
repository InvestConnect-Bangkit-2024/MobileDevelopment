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
import com.example.capstoneproduct.data.investors.DataItem
import com.example.capstoneproduct.databinding.RvRowBinding
import com.example.capstoneproduct.ui.details.DetailsActivity

class InvestorAdapter : ListAdapter<DataItem, InvestorAdapter.ListViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val binding = RvRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailsActivity::class.java)
            intent.putExtra("name", data?.investorName)
            intent.putExtra("sector", data?.investmentFocus)
            intent.putExtra("img", data?.imgUrl)
            intent.putExtra("location", data?.location)
            intent.putExtra("stages", data?.stages)
            intent.putExtra("thesis", data?.thesis)
            intent.putExtra("dealType", data.dealType)
            intent.putExtra("totalDeals", data?.totalDeals)
            intent.putExtra("totalInvestments", data?.totalInvestments)
            intent.putExtra("geographic", data?.geographicFocus)
            intent.putExtra("criteria", data?.criteria)
            intent.putExtra("phone", data?.phoneNumber)
            holder.itemView.context.startActivity(intent)
        }
    }

    class ListViewHolder(private val binding: RvRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItem) {
            binding.tvName.text = data.investorName
            binding.tvSector.text = data.investmentFocus
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