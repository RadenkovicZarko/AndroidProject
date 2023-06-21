package rs.raf.vezbe11.data.models.responses

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class CategoryResponse (
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String
)