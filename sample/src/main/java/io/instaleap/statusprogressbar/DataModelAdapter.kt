package io.instaleap.statusprogressbar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.instaleap.statusprogressbar.databinding.ItemDataListBinding

class DataModelAdapter : ListAdapter<DataModelView, DataModelAdapter.ItemViewHolder>(
    DiffItemCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDataListBinding.inflate(inflater, parent , false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ItemViewHolder(private val binding: ItemDataListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DataModelView) {
            with(binding) {
                linearStateBarProducts.setDataModelView(item.dataList, item.totalValue)
            }
        }
    }
}

object DiffItemCallback: DiffUtil.ItemCallback<DataModelView>() {
    override fun areItemsTheSame(oldItem: DataModelView, newItem: DataModelView): Boolean {
        return true
    }

    override fun areContentsTheSame(oldItem: DataModelView, newItem: DataModelView): Boolean {
        return true
    }
}