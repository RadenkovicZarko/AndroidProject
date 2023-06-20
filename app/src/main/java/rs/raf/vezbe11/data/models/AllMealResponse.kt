package rs.raf.vezbe11.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllMealResponse (
    val allMeals: List<MealResponse>
)