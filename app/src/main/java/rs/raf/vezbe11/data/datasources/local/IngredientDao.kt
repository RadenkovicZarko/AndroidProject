package rs.raf.vezbe11.data.datasources.local

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.entities.IngredientEntity

@Dao
abstract class IngredientDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: IngredientEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<IngredientEntity>): Completable

    @Query("SELECT * FROM ingredients")
    abstract fun getAll(): Observable<List<IngredientEntity>>

    @Query("DELETE FROM ingredients")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<IngredientEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

    @Query("SELECT * FROM ingredients WHERE idIngredient=:id ")
    abstract fun loadSingle(id: String): IngredientEntity


    @Update
    abstract fun updateCalories(entity: IngredientEntity)

}