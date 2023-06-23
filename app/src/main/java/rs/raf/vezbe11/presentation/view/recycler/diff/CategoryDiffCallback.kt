package rs.raf.vezbe11.presentation.view.recycler.diff

import androidx.recyclerview.widget.DiffUtil
import rs.raf.vezbe11.data.models.entities.CategoryEntity

class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryEntity>() {

    override fun areItemsTheSame(oldItem: CategoryEntity , newItem: CategoryEntity): Boolean {
        return oldItem.idCategory == newItem.idCategory
    }

    override fun areContentsTheSame(oldItem: CategoryEntity, newItem: CategoryEntity): Boolean {
        return oldItem.strCategory == newItem.strCategory
    }
}