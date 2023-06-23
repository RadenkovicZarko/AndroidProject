package rs.raf.vezbe11.data.repositories


import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.entities.MealEntity
import rs.raf.vezbe11.data.models.Resource
import rs.raf.vezbe11.data.models.entities.CategoryEntity
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
}