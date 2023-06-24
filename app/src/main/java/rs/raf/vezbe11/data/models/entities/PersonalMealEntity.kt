package rs.raf.vezbe11.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName="personal_meals")
data class PersonalMealEntity (
    @PrimaryKey(autoGenerate = true)
    var idPersonalMeal: Long,
    val strTypeOfMeal: String?,
    val date: String?,
    val strPersonalUrl: String?,
    val idMealForeign: String?,
    val idUserForeign: String?
)