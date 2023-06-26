package rs.raf.vezbe11.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.vezbe11.data.models.entities.MealEntity
import rs.raf.vezbe11.databinding.LayoutItemMealBinding
import rs.raf.vezbe11.presentation.view.recycler.adapter.PlannerMealAdapter

class PlannerMealViewHolder(private val itemBinding:LayoutItemMealBinding, private val listener: PlannerMealAdapter.OnItemClickListener) : RecyclerView.ViewHolder(itemBinding.root)  {

    fun bind(meal: MealEntity) {
        itemBinding.mealNameTv.text = meal.strMeal
        Glide.with(itemView.context).load(meal.strMealThumb).circleCrop().into(itemBinding.iconMealIvRc)
        itemBinding.caloriesTv.text = meal.sumOfCalories.toString()
        itemView.setOnClickListener{
            listener.returnChoice(meal)
        }

    }

}