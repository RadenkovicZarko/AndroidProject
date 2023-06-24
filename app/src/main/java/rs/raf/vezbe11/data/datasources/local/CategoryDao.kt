package rs.raf.vezbe11.data.datasources.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.entities.CategoryEntity
import rs.raf.vezbe11.data.models.relations.CategoryMealRelation

@Dao
abstract class CategoryDao {
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: CategoryEntity): Completable
    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<CategoryEntity>): Completable
    @Query("SELECT * FROM categories")
    abstract fun getAll(): Observable<List<CategoryEntity>>
    @Query("DELETE FROM categories")
    abstract fun deleteAll()
    @Transaction
    open fun deleteAndInsertAll(entities: List<CategoryEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
    @Transaction
    @Query("SELECT * FROM categories")
    abstract fun getCategoryMealRelations(): Observable<List<CategoryMealRelation>>

    @Query("SELECT * FROM categories WHERE strCategory IN ( SELECT DISTINCT strCategory FROM ( SELECT strCategory FROM meals  WHERE strMeal LIKE :letters || '%'  UNION  \n" +
            "SELECT strCategory FROM  ingredients i JOIN crossTable c ON (i.idIngredient = c.idIngredient) JOIN meals m  ON (c.idMeal = m.idMeal) WHERE strIngredient LIKE :letters || '%'  ) AS categoryData )")
    abstract fun getCaloriesByNameOfIngredientOrMeal(letters: String): Observable<List<CategoryEntity>>

}