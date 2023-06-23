package rs.raf.vezbe11.presentation.view.recycler.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import rs.raf.vezbe11.data.models.entities.CategoryEntity
import rs.raf.vezbe11.databinding.LayoutItemCategoryBinding
import rs.raf.vezbe11.presentation.view.recycler.adapter.CategoryAdapter
import timber.log.Timber

class CategoryViewHolder(private val itemBinding: LayoutItemCategoryBinding, private val listener: CategoryAdapter.OnItemClickListener) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(category: CategoryEntity) {
        itemBinding.categoryNameTvRv.text = category.strCategory
        Picasso.get().load(category.strCategoryThumb).into(itemBinding.categoryIconIvRc)
        itemBinding.threeDotsIvRc.setOnClickListener {
            val position = adapterPosition
            val text = category.strCategoryDescription
            listener.onImageClick(position, text)
        }

    }

}