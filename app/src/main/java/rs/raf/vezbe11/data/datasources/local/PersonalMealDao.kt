package rs.raf.vezbe11.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.entities.PersonalMealEntity
import rs.raf.vezbe11.data.models.relations.PersonalMealAndMeal
import rs.raf.vezbe11.data.models.relations.PersonalMealAndUser

@Dao
abstract class PersonalMealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity: PersonalMealEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(entities: List<PersonalMealEntity>): Completable

    @Query("Select * from personal_meals")
    abstract fun getAll(): Observable<List<PersonalMealEntity>>

    @Query("DELETE FROM personal_meals")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<PersonalMealEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
    @Query("SELECT * FROM personal_meals WHERE idUserForeign=:idUser")
    abstract fun getAllPersonalMealsByUser(idUser: String): Observable<List<PersonalMealEntity>>

}