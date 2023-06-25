package rs.raf.vezbe11.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.vezbe11.data.models.PlannerItem
import rs.raf.vezbe11.data.models.entities.MealEntity
import rs.raf.vezbe11.databinding.LayoutItemMealBinding
import rs.raf.vezbe11.databinding.LayoutPlanItemBinding
import rs.raf.vezbe11.presentation.view.recycler.diff.MealDiffCallback
import rs.raf.vezbe11.presentation.view.recycler.diff.PlannerDiffCallback
import rs.raf.vezbe11.presentation.view.recycler.viewholder.PlannerItemViewHolder
import rs.raf.vezbe11.presentation.view.recycler.viewholder.PlannerMealViewHolder

class PlannerMealAdapter(private val listener: PlannerMealAdapter.OnItemClickListener) :
    ListAdapter<MealEntity, PlannerMealViewHolder>(
        MealDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlannerMealViewHolder {
        val itemBinding = LayoutItemMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlannerMealViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: PlannerMealViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnItemClickListener {
        fun returnChoice(position: Int)
    }
}