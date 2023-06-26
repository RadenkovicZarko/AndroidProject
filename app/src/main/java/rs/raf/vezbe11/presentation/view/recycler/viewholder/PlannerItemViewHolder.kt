package rs.raf.vezbe11.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import rs.raf.vezbe11.data.models.PlannerItem
import rs.raf.vezbe11.databinding.LayoutPlanItemBinding
import rs.raf.vezbe11.presentation.view.recycler.adapter.PlannerAdapter

class PlannerItemViewHolder(
    private val itemBinding: LayoutPlanItemBinding,
    private val listener: PlannerAdapter.OnItemClickListener
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(plannerItem: PlannerItem) {
        itemBinding.plannerItemMealTV.text = plannerItem.meal?.strMeal
        if (plannerItem.meal != null && (plannerItem.meal?.sumOfCalories == null || plannerItem.meal?.sumOfCalories == 0.0))
            itemBinding.plannerItemCaloriesTV.text = "No calories"
        else{
            if(plannerItem.meal != null)
                itemBinding.plannerItemCaloriesTV.text = "${plannerItem.meal?.sumOfCalories} kcal"
            else
                itemBinding.plannerItemCaloriesTV.text = "No meal"
        }


        itemView.setOnClickListener() {
            val position = adapterPosition
            listener.chooseMeal(position)
        }
    }
}