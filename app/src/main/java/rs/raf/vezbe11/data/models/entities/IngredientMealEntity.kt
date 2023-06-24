package rs.raf.vezbe11.data.models.entities

import androidx.room.Entity

@Entity(primaryKeys = ["idIngredient", "idMeal"],
    tableName="crossTable")
data class IngredientMealEntity (
    val idMeal: String,
    val idIngredient : String,
    val strMeasure: Double?
)