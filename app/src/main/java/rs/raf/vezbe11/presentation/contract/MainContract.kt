package rs.raf.vezbe11.presentation.contract

import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import rs.raf.vezbe11.data.models.PlannerItem
import rs.raf.vezbe11.data.models.Resource
import rs.raf.vezbe11.data.models.entities.MealEntity
import rs.raf.vezbe11.data.models.entities.PersonalMealEntity
import rs.raf.vezbe11.data.models.entities.UserEntity
import rs.raf.vezbe11.presentation.view.states.*


interface MainContract {

    interface ViewModel {
        val subscriptions: CompositeDisposable
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
        val plannerList: LiveData<List<PlannerItem>>
        val mealDetailsState : LiveData<MealDetailsState>
        val ingredientsForMealState : LiveData<IngredientsForMealState>
        val personalMealsDates: LiveData<List<PersonalMealEntity>>
        val personalMealUpdate: LiveData<AddPersonalMealState>
        val filterMealState: LiveData<FilterMealState>
        val countOfFilterMealState : LiveData<CountOfFilterMealState>

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

        fun updatePersonalMeal(meal: PersonalMealEntity)


        fun getAllMealsForCategory(category : String)

        fun deletePersonalMeal(meal: PersonalMealEntity)

        fun getOnePersonalMealsByUser(idUser: String, idMeal: String)

        fun getPersonalMealsBetweenDates(startDate: Long, endDate: Long, idUser: String): List<PersonalMealEntity>


        fun getMealsCount(): Int

        fun loadPlannerList()

        fun getMealsInRange(a:Int)

        fun getAllMeals()

        fun getNumOfMeals(category: String)
        fun getCountFilteredAndSortedMealsBetween(meal:String?,ingredient:String?,minCalories:Double?,maxCalories:Double?,sort:Int?,category: String)

        fun getCountFilteredAndSortedMealsNormal(meal:String?,ingredient:String?,minCalories:Double?,maxCalories:Double?,sort:Int?,category: String)

        fun getFilteredAndSortedMealsBetween(meal:String?,ingredient:String?,minCalories:Double?,maxCalories:Double?,sort:Int?,a:Int,category: String)

        fun getFilteredAndSortedMealsNormal(meal:String?,ingredient:String?,minCalories:Double?,maxCalories:Double?,sort:Int?,a:Int,category: String)

        fun getMealById(idMeal: String)

        fun getIngredientsForMeal(idMeal : String)
        fun getFilteredMeals(category: String?, ingredient: String?, area: String?, tag: String?, meal: String?, sort: Int?,a:Int)
        fun getCountOfFilteredMeals(category: String?, ingredient: String?, area: String? , tag:String?, meal: String?, sort: Int? )

        fun fetchAllD(): Observable<Resource<Unit>>
    }
}