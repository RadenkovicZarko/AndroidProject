package rs.raf.vezbe11.presentation.view.states

import rs.raf.vezbe11.data.models.entities.MealEntity

sealed class IngredientsForMealState {
    object Loading: IngredientsForMealState()
    object DataFetched: IngredientsForMealState()
    data class Success(val ingredientsForMeal: List<String>): IngredientsForMealState()
    data class Error(val message: String): IngredientsForMealState()
}