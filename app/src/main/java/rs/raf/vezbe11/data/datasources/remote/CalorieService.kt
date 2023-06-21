package rs.raf.vezbe11.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.*
import rs.raf.vezbe11.data.models.responses.CalorieReponse
import rs.raf.vezbe11.data.models.responses.CaloriesResponse
import rs.raf.vezbe11.data.models.responses.MealsResponse

interface CalorieService {
    @GET("nutrition")
    fun getCalories(@Query("query") query: String): Observable<List<CalorieReponse>>
}