package com.shivam.ottvideoplayer.presentation.ui.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shivam.ottvideoplayer.R
import com.shivam.ottvideoplayer.databinding.ItemsUiBinding
import com.shivam.ottvideoplayer.domain.domain_model.DomainItem
import com.squareup.picasso.Picasso

class DataAdapter(
    private val onClick: (DomainItem) -> Unit,
    private val context: Context
) : ListAdapter<DomainItem, DataAdapter.ViewHolderClass>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        val binding = ItemsUiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolderClass(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClick)
    }

    class ViewHolderClass(
        private val binding: ItemsUiBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DomainItem, onClick: (DomainItem) -> Unit) {
            Picasso.get()
                .load(item.thumbnailUrl)
                .placeholder(R.drawable.funny_cartoon)
                .error(R.drawable.funny_cartoon)
                .into(binding.showImg)

            binding.showTitle.text = item.title

            binding.mainUi.setOnClickListener {
                onClick(item)
            }
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<DomainItem>() {
        override fun areItemsTheSame(oldItem: DomainItem, newItem: DomainItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DomainItem, newItem: DomainItem): Boolean {
            return oldItem == newItem
        }
    }
}
