package rs.raf.vezbe11.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.vezbe11.data.models.PlannerItem
import rs.raf.vezbe11.databinding.LayoutPlanItemBinding
import rs.raf.vezbe11.presentation.view.recycler.diff.PlannerDiffCallback
import rs.raf.vezbe11.presentation.view.recycler.viewholder.PlannerItemViewHolder

class PlannerAdapter(private val listener: PlannerAdapter.OnItemClickListener): ListAdapter<PlannerItem, PlannerItemViewHolder>(
    PlannerDiffCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlannerItemViewHolder {
        val itemBinding = LayoutPlanItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        itemBinding.root.maxHeight = parent.measuredHeight / 3
        return PlannerItemViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: PlannerItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnItemClickListener {
        fun chooseMeal(position: Int)

    }
}

