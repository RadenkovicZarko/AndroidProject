package rs.raf.vezbe11.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.vezbe11.presentation.view.states.CategoryState
import rs.raf.vezbe11.presentation.view.states.MealState


interface MainContract {

    interface ViewModel {
        val mealState: LiveData<MealState>
        val categoryState: LiveData<CategoryState>
        fun fetchAllMeals()
        fun fetchAllCategories()
        fun fetchAllAreas()
        fun fetchAllIngredients()
        fun fetchAllCalories()
        fun getCAndMRelations()

        fun getMealsByName(name: String)
    }
}