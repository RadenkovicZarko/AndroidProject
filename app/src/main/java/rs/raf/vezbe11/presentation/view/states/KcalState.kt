package rs.raf.vezbe11.presentation.view.states

import rs.raf.vezbe11.data.models.entities.MealEntity

sealed class KcalState{
    object Loading: KcalState()
    object DataFetched:KcalState()
    data class Success(val kcal: Double): KcalState()
    data class Error(val message: String): KcalState()
}