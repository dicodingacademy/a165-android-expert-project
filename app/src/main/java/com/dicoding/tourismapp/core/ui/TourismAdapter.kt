package com.dicoding.tourismapp.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.tourismapp.core.domain.model.Tourism
import com.dicoding.tourismapp.databinding.ItemListTourismBinding

class TourismAdapter : ListAdapter<Tourism, TourismAdapter.ListViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((Tourism) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            ItemListTourismBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ListViewHolder(private var binding: ItemListTourismBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Tourism) {
            Glide.with(itemView.context)
                .load(data.image)
                .into(binding.ivItemImage)
            binding.tvItemTitle.text = data.name
            binding.tvItemSubtitle.text = data.address
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(bindingAdapterPosition))
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Tourism> =
            object : DiffUtil.ItemCallback<Tourism>() {
                override fun areItemsTheSame(oldItem: Tourism, newItem: Tourism): Boolean {
                    return oldItem.tourismId == newItem.tourismId
                }

                override fun areContentsTheSame(oldItem: Tourism, newItem: Tourism): Boolean {
                    return oldItem == newItem
                }
            }
    }
}