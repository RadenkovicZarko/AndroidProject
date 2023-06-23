package rs.raf.vezbe11.data.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="users")
data class UserEntity (
    @PrimaryKey
    var userName: String,
    var password: String,
    var years: Int,
    var height: Double,
    var weight: Double,
    var sex: Int,
    var activityLevel: Int
    ) : java.io.Serializable