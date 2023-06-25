package rs.raf.vezbe11.presentation.view.states

sealed class DeletePersonalMealState{
    object Success: DeletePersonalMealState()
    data class Error(val message: String): DeletePersonalMealState()
}