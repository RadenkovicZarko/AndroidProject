package rs.raf.vezbe11.presentation.view.states

sealed class AddPersonalMealState{
    object Success: AddPersonalMealState()
    data class Error(val message: String): AddPersonalMealState()
}
