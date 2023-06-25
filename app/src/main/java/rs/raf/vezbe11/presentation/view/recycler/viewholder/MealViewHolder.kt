package rs.raf.vezbe11.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.vezbe11.data.models.entities.CategoryEntity
import rs.raf.vezbe11.data.models.entities.MealEntity
import rs.raf.vezbe11.databinding.LayoutItemCategoryBinding
import rs.raf.vezbe11.databinding.LayoutItemMealBinding
import rs.raf.vezbe11.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.vezbe11.presentation.view.recycler.adapter.MealAdapter
import timber.log.Timber

class MealViewHolder (private val itemBinding: LayoutItemMealBinding, private val listener: MealAdapter.OnItemClickListener) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(meal: MealEntity) {
        itemBinding.mealNameTv.text = meal.strMeal
        Picasso.get().load(meal.strMealThumb).into(itemBinding.iconMealIvRc)
        itemBinding.caloriesTv.text = meal.sumOfCalories.toString()
        itemView.setOnClickListener{
            val position = adapterPosition
            val text = meal.idMeal
            listener.onItemClick(text)
        }

    }

}