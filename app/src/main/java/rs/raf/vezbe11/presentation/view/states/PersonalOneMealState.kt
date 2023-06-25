package rs.raf.vezbe11.presentation.view.states

import rs.raf.vezbe11.data.models.entities.PersonalMealEntity

sealed class PersonalOneMealState{
    data class Success(val meal: PersonalMealEntity): PersonalOneMealState()
    data class Error(val message: String): PersonalOneMealState()
}
