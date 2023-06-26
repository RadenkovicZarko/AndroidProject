package rs.raf.vezbe11.presentation.view.states

import rs.raf.vezbe11.data.models.entities.MealEntity

sealed class MealDetailsState  {
    object Loading: MealDetailsState()
    object DataFetched:MealDetailsState()
    data class Success(val meal: MealEntity): MealDetailsState()
    data class Error(val message: String): MealDetailsState()
}