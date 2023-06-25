package rs.raf.vezbe11.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import rs.raf.vezbe11.data.models.PlannerItem
import rs.raf.vezbe11.databinding.LayoutPlanItemBinding
import rs.raf.vezbe11.presentation.view.recycler.adapter.PlannerAdapter

class PlannerItemViewHolder(private val itemBinding: LayoutPlanItemBinding, private val listener: PlannerAdapter.OnItemClickListener) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(plannerItem: PlannerItem) {
//        itemBinding.plannerItemMealTV.text = plannerItem.meal?.strMeal
//        itemBinding.plannerItemCaloriesTV.text = plannerItem.meal?.strCategory
        itemBinding.plannerItemMealTV.text = "Chickengdfhfhdhdhdg"
        itemBinding.plannerItemCaloriesTV.text = "120,00"



        itemView.setOnClickListener() {
            val position = adapterPosition
            listener.chooseMeal(position)
        }
    }
}