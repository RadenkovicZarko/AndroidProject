package rs.raf.vezbe11.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import rs.raf.vezbe11.data.models.*
import rs.raf.vezbe11.data.models.responses.*

interface MealService {
    @GET("search.php?s=")
    fun getAllMeals(): Observable<MealsResponse>

    @GET("filter.php?i=")
    fun getAllMealsId(): Observable<MealsIdResponse>

    @GET("lookup.php")
    fun getMealById(@Query("i") i: String): Observable<MealsResponse>

    @GET("categories.php")
    fun getAllCategories(): Observable<CategoriesResponse>

    @GET("list.php?a=list")
    fun getAllAreas(): Observable<AreasResponse>


    @GET("list.php?i=list")
    fun getAllIngredients(): Observable<IngredientsResponse>
}