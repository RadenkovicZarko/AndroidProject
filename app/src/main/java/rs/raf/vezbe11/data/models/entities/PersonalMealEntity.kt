package rs.raf.vezbe11.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import rs.raf.vezbe11.data.models.converters.DateConverter
import java.sql.Date

@Entity(tableName="personal_meals")
data class PersonalMealEntity(
    @PrimaryKey(autoGenerate = true)
    var idPersonalMeal: Long,
    var strName: String?,
    val strTypeOfMeal: String?,

    @TypeConverters(DateConverter::class)
    val date: Date?,
    val strPersonalUrl: String?,
    val idMealForeign: String?,
    val idUserForeign: String?
)