package rs.raf.vezbe11.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.vezbe11.data.models.entities.CategoryEntity
import rs.raf.vezbe11.databinding.LayoutItemCategoryBinding
import rs.raf.vezbe11.presentation.view.recycler.adapter.CategoryAdapter

class CategoryViewHolder(private val itemBinding: LayoutItemCategoryBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(category: CategoryEntity, listener: CategoryAdapter.OnItemClickListener) {
        itemBinding.categoryNameTvRv.text = category.strCategory
        Picasso.get().load(category.strCategoryThumb).into(itemBinding.categoryIconIvRc)
        itemBinding.categoryIconIvRc.setOnClickListener {
            val position = adapterPosition
            val text = category.strCategoryDescription
            listener.onImageClick(position, text)
        }

    }

    init {

    }
}