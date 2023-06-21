package rs.raf.vezbe11.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="ingredients")
data class IngredientEntity (
    @PrimaryKey
    var idIngredient: String,
    var strIngredient: String,
    var strDescription: String?,
    var strType: String?,
    var calories : Double?
)