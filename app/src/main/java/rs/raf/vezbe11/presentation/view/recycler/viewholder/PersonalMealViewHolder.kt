package rs.raf.vezbe11.presentation.view.recycler.viewholder

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.compat.SharedViewModelCompat.sharedViewModel

import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.data.models.converters.DateConverter
import rs.raf.vezbe11.data.models.entities.PersonalMealEntity
import rs.raf.vezbe11.databinding.LayoutPersonamMealItemBinding
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.vezbe11.presentation.view.recycler.adapter.PersonalMealAdapter
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel
import java.sql.Date

class PersonalMealViewHolder(private val itemBinding: LayoutPersonamMealItemBinding, private val listener: PersonalMealAdapter.OnItemClickListener) : RecyclerView.ViewHolder(itemBinding.root) {


        fun bind(meal: PersonalMealEntity) {
            itemBinding.mealNameItemTV.text = meal.strName

            itemBinding.dateItemTV.text = reformat(meal.date?: Date(0))
            itemBinding.mealTypeTV.text = meal.strTypeOfMeal
            Glide.with(itemView.context).load(meal.strPersonalUrl).circleCrop().into(itemBinding.personalItemMealPV)

            itemBinding.deletePV.setOnClickListener {
                val position = adapterPosition
                listener.onDeleteClick(position)
            }

            itemBinding.editPV.setOnClickListener {
                val position = adapterPosition
                listener.onEditClick(position)
            }
    }

    private fun reformat(date: Date): String {
        val str = date.toString()
        val reformat = str.split("-")
        val year = reformat[0]
        val month = reformat[1]
        val day = reformat[2]
        return "$day-$month-$year"
    }
}