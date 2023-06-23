package rs.raf.vezbe11.presentation.view.states

import rs.raf.vezbe11.data.models.entities.CategoryEntity
import rs.raf.vezbe11.data.models.relations.CategoryMealRelation

sealed class CategoryState {
    object Loading:  CategoryState()
    object DataFetched: CategoryState()
    data class Success(val categories: List<CategoryEntity>): CategoryState()
    data class Error(val message: String): CategoryState()
}