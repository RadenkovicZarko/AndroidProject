package rs.raf.vezbe11.presentation.view.states

import rs.raf.vezbe11.data.models.entities.MealEntity

sealed class NumOfMealsState {
    object Loading: NumOfMealsState()
    object DataFetched: NumOfMealsState()
    data class Success(val numOfMeals: Int): NumOfMealsState()
    data class Error(val message: String): NumOfMealsState()
}