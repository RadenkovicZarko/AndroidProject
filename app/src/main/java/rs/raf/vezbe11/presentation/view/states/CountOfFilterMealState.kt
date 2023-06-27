package rs.raf.vezbe11.presentation.view.states

sealed class CountOfFilterMealState {
    object Loading: CountOfFilterMealState()
    object DataFetched: CountOfFilterMealState()
    data class Success(val numOfMeals: Int): CountOfFilterMealState()
    data class Error(val message: String): CountOfFilterMealState()
}