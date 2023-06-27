package rs.raf.vezbe11.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.vezbe11.data.models.entities.MealEntity
import rs.raf.vezbe11.databinding.LayoutItemMealBinding

import rs.raf.vezbe11.presentation.view.recycler.diff.MealDiffCallback
import rs.raf.vezbe11.presentation.view.recycler.viewholder.FilterMealViewHolder
import rs.raf.vezbe11.presentation.view.recycler.viewholder.MealViewHolder

class FilterMealAdapter(private val listener: FilterMealAdapter.OnItemClickListener)  : ListAdapter<MealEntity, FilterMealViewHolder>(MealDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterMealViewHolder {
        val itemBinding = LayoutItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterMealViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: FilterMealViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnItemClickListener {
        fun onItemClick(text: String)
    }


}