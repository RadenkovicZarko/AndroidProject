package rs.raf.vezbe11.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="ingredients")
data class IngredientEntity (
    @PrimaryKey
    val idIngredient: String,
    val strIngredient: String,
    val strDescription: String?,
    val strType: String?
)