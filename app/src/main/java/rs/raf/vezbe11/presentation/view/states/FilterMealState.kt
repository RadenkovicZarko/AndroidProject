package rs.raf.vezbe11.presentation.view.states

import rs.raf.vezbe11.data.models.entities.MealEntity

sealed class FilterMealState {
    object Loading: FilterMealState()
    object DataFetched: FilterMealState()
    data class Success(val meals: List<MealEntity>): FilterMealState()
    data class Error(val message: String): FilterMealState()
}