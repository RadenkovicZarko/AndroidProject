package rs.raf.vezbe11.presentation.contract

import androidx.lifecycle.LiveData
import rs.raf.vezbe11.data.models.entities.MealEntity
import rs.raf.vezbe11.data.models.entities.PersonalMealEntity
import rs.raf.vezbe11.data.models.entities.UserEntity
import rs.raf.vezbe11.presentation.view.states.*


interface MainContract {

    interface ViewModel {
        val mealState: LiveData<MealState>
        val userState: LiveData<UserState>
        val categoryState: LiveData<CategoryState>
        val currentPersonalMealSave: LiveData<MealEntity>
        val currentUser: LiveData<UserEntity>
        val insertPersonalMeal: LiveData<AddPersonalMealState>
        val deletePersonalMeal: LiveData<DeletePersonalMealState>
        val personalMealsState: LiveData<PersonalMealState>
        val personalOneMealState: LiveData<PersonalMealEntity>
        fun fetchAllMeals()
        fun fetchAllCategories()
        fun fetchAllAreas()
        fun fetchAllIngredients()
        fun fetchAllCalories()
        fun getCAndMRelations()

        fun findUserWithUsernameAndPassword(username:String, password:String)
        fun getMealsByName(name: String)

        fun getAllCategories()

        fun getAllPersonalMealsByUser(idUser: String)

        fun getCaloriesByNameOfIngredientOrMeal(letters: String)

        fun setPersonalMealForSaving(meal: MealEntity)

        fun setCurrentUser(user: UserEntity)

        fun insertPersonalMeal(meal: PersonalMealEntity)


        fun getAllMealsForCategory(category : String)

        fun deletePersonalMeal(meal: PersonalMealEntity)

        fun getOnePersonalMealsByUser(idUser: String, idMeal: String)
    }
}