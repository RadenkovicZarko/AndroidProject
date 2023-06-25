package rs.raf.vezbe11.presentation.view.recycler.viewholder

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.compat.SharedViewModelCompat.sharedViewModel

import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.data.models.entities.PersonalMealEntity
import rs.raf.vezbe11.databinding.LayoutPersonamMealItemBinding
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.vezbe11.presentation.view.recycler.adapter.PersonalMealAdapter
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel

class PersonalMealViewHolder(private val itemBinding: LayoutPersonamMealItemBinding, private val listener: PersonalMealAdapter.OnItemClickListener) : RecyclerView.ViewHolder(itemBinding.root) {


// TODO add listeners for the delete and edit
        fun bind(meal: PersonalMealEntity) {
            itemBinding.mealNameItemTV.text = meal.strName
            itemBinding.dateItemTV.text = meal.date
            itemBinding.mealTypeTV.text = meal.strTypeOfMeal
            Glide.with(itemView.context).load(meal.strPersonalUrl).circleCrop().into(itemBinding.personalItemMealPV?:return)

            itemBinding.deletePV.setOnClickListener {
                val position = adapterPosition
                listener.onDeleteClick(position)
            }

            itemBinding.editPV.setOnClickListener {
                val position = adapterPosition
                listener.onEditClick(position)
            }
}
}