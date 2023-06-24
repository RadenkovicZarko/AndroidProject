package rs.raf.vezbe11.presentation.view.states

import rs.raf.vezbe11.data.models.entities.MealEntity
import rs.raf.vezbe11.data.models.entities.PersonalMealEntity

sealed class PersonalMealState{
    object Loading: PersonalMealState()
    object DataFetched: PersonalMealState()
    data class Success(val meals: List<PersonalMealEntity>): PersonalMealState()
    data class Error(val message: String): PersonalMealState()
}
