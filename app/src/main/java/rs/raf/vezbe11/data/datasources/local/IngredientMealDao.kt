package rs.raf.vezbe11.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.IngredientMealEntity

@Dao
abstract class IngredientMealDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: IngredientMealEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<IngredientMealEntity>): Completable

    @Query("SELECT * FROM crossTable")
    abstract fun getAll(): Observable<List<IngredientMealEntity>>

    @Query("DELETE FROM crossTable")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<IngredientMealEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }


}