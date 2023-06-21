package rs.raf.vezbe11.data.models.responses

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class IngredientResponse (
    var idIngredient: String,
    var strIngredient: String,
    var strDescription: String?,
    var strType: String?
)