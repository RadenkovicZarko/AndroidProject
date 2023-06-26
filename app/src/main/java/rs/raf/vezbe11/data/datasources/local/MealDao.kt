package rs.raf.vezbe11.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.entities.MealEntity

@Dao
abstract class MealDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insert(entity: MealEntity): Completable

    @Insert( onConflict = OnConflictStrategy.REPLACE )
    abstract fun insertAll(entities: List<MealEntity>): Completable

    @Query("SELECT * FROM meals")
    abstract fun getAll(): Observable<List<MealEntity>>

    @Query("DELETE FROM meals")
    abstract fun deleteAll()

    @Query("SELECT * FROM meals WHERE strCategory= :category")
    abstract fun getAllMealsForCategory(category : String) : Observable<List<MealEntity>>

    @Transaction
    open fun deleteAndInsertAll(entities: List<MealEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }

    @Query("SELECT * FROM meals LIMIT 10 OFFSET :a")
    abstract fun getMealsInRange(a:Int):Observable<List<MealEntity>>

    @Query("SELECT COUNT(*) FROM meals WHERE strCategory = :category")
    abstract fun getNumOfMeals(category: String):Observable<Int>

//    @Query(" SELECT * FROM meals WHERE " +
//            "(:mealName IS NULL OR strMeal LIKE '%' || :mealName || '%') AND " +
//            "(:ingredients IS NULL OR strIngredients LIKE '%' || :ingredients || '%') AND " +
//            "(:minCalories IS NULL OR sumOfCalories >= :minCalories) AND " +
//            "(:maxCalories IS NULL OR sumOfCalories <= :maxCalories) " +
//            "ORDER BY CASE WHEN :sortBy IS NULL THEN 0 ELSE 1 END")
//    fun getFilteredAndSortedMeals(mealName:String?, ingredients:String?, minCalories:Double?,maxCalories:Double?,sortBy:Int?): List<MealEntity>


    @Query("SELECT DISTINCT m.idMeal, strmeal, strDrinkAlternate, strCategory,strArea,strInstructions,strMealThumb,strTags,strYoutube,strSource,strImageSource,strCreativeCommonsConfirmed,dateModified,sumOfCalories FROM meals m JOIN crossTable c ON (m.idMeal = c.idMeal) WHERE \n" +
            "(:meal IS NULL OR strMeal LIKE '%' || :meal || '%') AND (:ingredient IS NULL OR idIngredient LIKE '%' || :ingredient || '%') AND (((:minCalories IS NOT NULL AND sumOfCalories >= :minCalories ) AND  (:maxCalories IS NOT NULL AND sumOfCalories <= :maxCalories )) OR (:minCalories IS NULL AND :maxCalories IS NULL)) AND strCategory = :category \n" +
            "ORDER BY CASE WHEN :sort IS NULL THEN 0 ELSE (CASE WHEN :sort IS 1 THEN sumOfCalories ELSE -sumOfCalories END)  END LIMIT 10 OFFSET :a")
    abstract fun getFilteredAndSortedMealsBetween(meal:String?,ingredient:String?,minCalories:Double?,maxCalories:Double?,sort:Int?,a:Int,category: String) : Observable<List<MealEntity>>

    @Query("SELECT DISTINCT m.idMeal, strmeal, strDrinkAlternate, strCategory,strArea,strInstructions,strMealThumb,strTags,strYoutube,strSource,strImageSource,strCreativeCommonsConfirmed,dateModified,sumOfCalories FROM meals m JOIN crossTable c ON (m.idMeal = c.idMeal) WHERE \n" +
            "(:meal IS NULL OR strMeal LIKE '%' || :meal || '%') AND (:ingredient IS NULL OR idIngredient LIKE '%' || :ingredient || '%') AND (((:minCalories IS NOT NULL AND sumOfCalories <= :minCalories ) OR  (:maxCalories IS NOT NULL AND sumOfCalories >= :maxCalories ))OR (:minCalories IS NULL AND :maxCalories IS NULL))  AND strCategory = :category \n" +
            "ORDER BY CASE WHEN :sort IS NULL THEN 0 ELSE (CASE WHEN :sort IS 1 THEN sumOfCalories ELSE -sumOfCalories END)  END LIMIT 10 OFFSET :a")
    abstract fun getFilteredAndSortedMealsNormal(meal:String?,ingredient:String?,minCalories:Double?,maxCalories:Double?,sort:Int?, a:Int, category: String) : Observable<List<MealEntity>>


    @Query("SELECT COUNT (DISTINCT m.idMeal) FROM meals m JOIN crossTable c ON (m.idMeal = c.idMeal) WHERE \n" +
            "(:meal IS NULL OR strMeal LIKE '%' || :meal || '%') AND (:ingredient IS NULL OR idIngredient LIKE '%' || :ingredient || '%') AND (((:minCalories IS NOT NULL AND sumOfCalories >= :minCalories ) AND  (:maxCalories IS NOT NULL AND sumOfCalories <= :maxCalories ))OR (:minCalories IS NULL AND :maxCalories IS NULL) ) AND strCategory = :category \n" +
            "ORDER BY CASE WHEN :sort IS NULL THEN 0 ELSE (CASE WHEN :sort IS 1 THEN sumOfCalories ELSE -sumOfCalories END)  END")
    abstract fun getCountFilteredAndSortedMealsBetween(meal:String?,ingredient:String?,minCalories:Double?,maxCalories:Double?,sort:Int?, category: String) : Observable<Int>

    @Query("SELECT COUNT (DISTINCT m.idMeal) FROM meals m JOIN crossTable c ON (m.idMeal = c.idMeal) WHERE \n" +
            "(:meal IS NULL OR strMeal LIKE '%' || :meal || '%') AND (:ingredient IS NULL OR idIngredient LIKE '%' || :ingredient || '%') AND (((:minCalories IS NOT NULL AND sumOfCalories <= :minCalories ) OR  (:maxCalories IS NOT NULL AND sumOfCalories >= :maxCalories ))OR (:minCalories IS NULL AND :maxCalories IS NULL) )AND strCategory = :category \n" +
            "ORDER BY CASE WHEN :sort IS NULL THEN 0 ELSE (CASE WHEN :sort IS 1 THEN sumOfCalories ELSE -sumOfCalories END)  END")
    abstract fun getCountFilteredAndSortedMealsNormal(meal:String?,ingredient:String?,minCalories:Double?,maxCalories:Double?,sort:Int?, category: String) : Observable<Int>


    @Query("SELECT * FROM meals WHERE idMeal = :idMeal")
    abstract fun getMealById(idMeal: String):Observable<MealEntity>

    @Query("SELECT idIngredient FROM meals m JOIN crossTable c ON (m.idMeal = c.idMeal ) WHERE m.idMeal = :idMeal")
    abstract fun getIngredientsForMeal(idMeal : String) : Observable<List<String>>
}