package rs.raf.vezbe11.data.models.responses

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class MealIdResponse (
val strMeal: String,
val strMealThumb: String,
val idMeal: String
)