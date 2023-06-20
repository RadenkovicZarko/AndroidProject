package rs.raf.vezbe11.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.vezbe11.presentation.view.states.MealState


interface MainContract {

    interface ViewModel {
        val mealState: LiveData<MealState>

        fun fetchAllMeals()
        fun getAllMeals()
        fun getMealsByName(name: String)
    }
}