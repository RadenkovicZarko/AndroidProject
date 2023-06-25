package rs.raf.vezbe11.presentation.contract

import androidx.lifecycle.LiveData
import io.reactivex.Observable
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
        val personalMealsState: LiveData<PersonalMealState>
        val numOfMealsState: LiveData<NumOfMealsState>
        val  deletePersonalMeal: LiveData<DeletePersonalMealState>
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

        fun getMealsInRange(a:Int)

        fun getNumOfMeals(category: String)
        fun getCountFilteredAndSortedMealsBetween(meal:String?,ingredient:String?,minCalories:Double?,maxCalories:Double?,sort:Int?)

        fun getCountFilteredAndSortedMealsNormal(meal:String?,ingredient:String?,minCalories:Double?,maxCalories:Double?,sort:Int?)

        fun getFilteredAndSortedMealsBetween(meal:String?,ingredient:String?,minCalories:Double?,maxCalories:Double?,sort:Int?,a:Int)

        fun getFilteredAndSortedMealsNormal(meal:String?,ingredient:String?,minCalories:Double?,maxCalories:Double?,sort:Int?,a:Int)

    }
}