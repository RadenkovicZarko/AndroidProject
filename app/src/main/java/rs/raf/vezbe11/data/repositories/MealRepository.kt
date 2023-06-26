package rs.raf.vezbe11.data.repositories


import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.entities.MealEntity
import rs.raf.vezbe11.data.models.Resource
import rs.raf.vezbe11.data.models.entities.CategoryEntity
import rs.raf.vezbe11.data.models.entities.PersonalMealEntity
import rs.raf.vezbe11.data.models.entities.UserEntity
import rs.raf.vezbe11.data.models.relations.CategoryMealRelation


interface MealRepository {
    fun fetchAllM(): Observable<Resource<Unit>>
    fun fetchAllC(): Observable<Resource<Unit>>
    fun fetchAllA(): Observable<Resource<Unit>>
    fun fetchAllI(): Observable<Resource<Unit>>

    fun fetchAllCalories(): Observable<Resource<Unit>>
    fun getAll(): Observable<List<MealEntity>>

    fun getAllMeals(): Observable<List<MealEntity>>
    fun getAllByName(name: String): Observable<List<MealEntity>>
    fun  getCategoryMealRelations(): Observable<List<CategoryMealRelation>>

    fun findUserWithUsernameAndPassword(username:String, password:String): Observable<UserEntity>

    fun insert(meal: MealEntity): Completable

    fun getCaloriesByNameOfIngredientOrMeal(letters: String):Observable<List<CategoryEntity>>
    fun insertPersonalMeal(meal: PersonalMealEntity): Completable

    fun deletePersonalMeal(meal: PersonalMealEntity): Completable

    fun getOnePersonalMealsByUser(idUser: String, idMeal: String): Observable<List<PersonalMealEntity>>

    fun getPersonalMealsBetweenDates(startDate: Long, endDate: Long, idUser: String): Observable<List<PersonalMealEntity>>

    fun getAllPersonalMealsByUser(idUser: String): Observable<List<PersonalMealEntity>>
    fun getAllCategories():Observable<List<CategoryEntity>>

    fun getAllMealsForCategory(category : String) : Observable<List<MealEntity>>

    fun getMealsInRange(a:Int) : Observable<List<MealEntity>>

    fun getNumOfMeals(category: String):Observable<Int>

    fun getCountFilteredAndSortedMealsBetween(meal:String?,ingredient:String?,minCalories:Double?,maxCalories:Double?,sort:Int?,category: String) : Observable<Int>
    fun getCountFilteredAndSortedMealsNormal(meal:String?,ingredient:String?,minCalories:Double?,maxCalories:Double?,sort:Int?,category: String) : Observable<Int>

    fun getFilteredAndSortedMealsBetween(meal:String?,ingredient:String?,minCalories:Double?,maxCalories:Double?,sort:Int?,a:Int,category: String) : Observable<List<MealEntity>>

    fun getFilteredAndSortedMealsNormal(meal:String?,ingredient:String?,minCalories:Double?,maxCalories:Double?,sort:Int?,a:Int,category: String) : Observable<List<MealEntity>>

    fun getMealById(idMeal: String):Observable<MealEntity>

    abstract fun getIngredientsForMeal(idMeal : String) : Observable<List<String>>
}