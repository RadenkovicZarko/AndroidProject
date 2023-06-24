package rs.raf.vezbe11.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.vezbe11.data.models.entities.MealEntity
import rs.raf.vezbe11.data.models.entities.PersonalMealEntity

class PersonalMealDiffCallback: DiffUtil.ItemCallback<PersonalMealEntity>(){
    override fun areItemsTheSame(oldItem: PersonalMealEntity, newItem: PersonalMealEntity): Boolean {
        return oldItem.idPersonalMeal == newItem.idPersonalMeal
    }

    override fun areContentsTheSame(oldItem: PersonalMealEntity, newItem: PersonalMealEntity): Boolean {
        return oldItem.idPersonalMeal == newItem.idPersonalMeal
    }

}