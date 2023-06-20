package rs.raf.vezbe11.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import rs.raf.vezbe11.data.models.MealResponse
import rs.raf.vezbe11.data.models.MealsResponse

interface MealService {
    @GET("search.php?s=")
    fun getAll(): Observable<MealsResponse>
}