package rs.raf.vezbe11.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.vezbe11.data.models.*


@Database(
    entities = [MealEntity::class, AreaEntity::class, IngredientEntity::class , IngredientMealEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters()
abstract class MealDataBase : RoomDatabase(){

    abstract fun getMealDao(): MealDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getAreaDao(): AreaDao
    abstract fun getIngredientDao(): IngredientDao
    abstract fun getIngredientMealDao(): IngredientMealDao
}