package rs.raf.vezbe11.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import rs.raf.vezbe11.data.models.entities.PersonalMealEntity
import rs.raf.vezbe11.databinding.LayoutItemCategoryBinding
import rs.raf.vezbe11.databinding.LayoutPersonamMealItemBinding
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.recycler.diff.CategoryDiffCallback
import rs.raf.vezbe11.presentation.view.recycler.diff.PersonalMealDiffCallback
import rs.raf.vezbe11.presentation.view.recycler.viewholder.CategoryViewHolder
import rs.raf.vezbe11.presentation.view.recycler.viewholder.PersonalMealViewHolder

//(private val listener: OnItemClickListener)
class PersonalMealAdapter(private val listener: PersonalMealAdapter.OnItemClickListener)  : ListAdapter<PersonalMealEntity, PersonalMealViewHolder>(
    PersonalMealDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonalMealViewHolder {
        val itemBinding = LayoutPersonamMealItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonalMealViewHolder(itemBinding, listener)
    }

    override fun onBindViewHolder(holder: PersonalMealViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnItemClickListener {
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }
}