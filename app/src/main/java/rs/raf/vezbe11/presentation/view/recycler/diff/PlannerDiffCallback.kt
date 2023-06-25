package rs.raf.vezbe11.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.vezbe11.data.models.PlannerItem

class PlannerDiffCallback: DiffUtil.ItemCallback<PlannerItem>(){
    override fun areItemsTheSame(oldItem: PlannerItem, newItem: PlannerItem): Boolean {
        return oldItem.day == newItem.day && oldItem.meal == newItem.meal
    }

    override fun areContentsTheSame(oldItem: PlannerItem, newItem: PlannerItem): Boolean {
        return oldItem.day == newItem.day && oldItem.meal == newItem.meal
    }
}