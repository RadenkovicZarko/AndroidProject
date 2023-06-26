package rs.raf.vezbe11.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.converters.DateConverter
import rs.raf.vezbe11.data.models.entities.PersonalMealEntity

@Dao
@TypeConverters(DateConverter::class)
abstract class PersonalMealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity: PersonalMealEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(entities: List<PersonalMealEntity>): Completable

    @Query("Select * from personal_meals")
    abstract fun getAll(): Observable<List<PersonalMealEntity>>

    @Query("DELETE FROM personal_meals")
    abstract fun deleteAll()

    @Delete
    abstract fun delete(entity: PersonalMealEntity): Completable

    @Transaction
    open fun deleteAndInsertAll(entities: List<PersonalMealEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
    @Query("SELECT * FROM personal_meals WHERE idUserForeign=:idUser")
    abstract fun getAllPersonalMealsByUser(idUser: String): Observable<List<PersonalMealEntity>>

    @Query("SELECT * FROM personal_meals WHERE idUserForeign=:idUser and idMealForeign=:idMeal")
    abstract fun getOnePersonalMealsByUser(idUser: String, idMeal: String): Observable<List<PersonalMealEntity>>

    @Query("SELECT * FROM personal_meals WHERE idUserForeign=:idUser AND date BETWEEN :startDate AND :endDate")
    abstract fun getPersonalMealsBetweenDatesFromUser(startDate: Long, endDate: Long, idUser: String): Observable<List<PersonalMealEntity>>
}