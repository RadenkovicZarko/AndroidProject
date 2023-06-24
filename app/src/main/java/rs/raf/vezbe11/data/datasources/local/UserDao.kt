package rs.raf.vezbe11.data.datasources.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.entities.UserEntity

@Dao
abstract class UserDao {
    @Query("SELECT * FROM users WHERE userName = :username and password = :password")
    abstract fun findUserWithUsernameAndPassword(username: String, password: String): Observable<UserEntity>
}