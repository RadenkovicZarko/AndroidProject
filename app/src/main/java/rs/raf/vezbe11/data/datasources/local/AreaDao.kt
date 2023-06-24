package rs.raf.vezbe11.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.entities.AreaEntity

@Dao
abstract class AreaDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: AreaEntity): Completable
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<AreaEntity>): Completable
    @Query("SELECT * FROM areas")
    abstract fun getAll(): Observable<List<AreaEntity>>
    @Query("DELETE FROM areas")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<AreaEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
}