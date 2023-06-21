package rs.raf.vezbe11.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import rs.raf.vezbe11.data.models.*
import rs.raf.vezbe11.data.models.responses.AreasResponse
import rs.raf.vezbe11.data.models.responses.CategoriesResponse
import rs.raf.vezbe11.data.models.responses.IngredientsResponse
import rs.raf.vezbe11.data.models.responses.MealsResponse

interface MealService {
    @GET("search.php?s=")
    fun getAllMeals(): Observable<MealsResponse>

    @GET("categories.php")
    fun getAllCategories(): Observable<CategoriesResponse>

    @GET("list.php?a=list")
    fun getAllAreas(): Observable<AreasResponse>


    @GET("list.php?i=list")
    fun getAllIngredients(): Observable<IngredientsResponse>
}