package rs.raf.vezbe11.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.vezbe11.data.models.converters.DateConverter
import rs.raf.vezbe11.data.models.entities.*


@Database(
    entities = [MealEntity::class, AreaEntity::class, IngredientEntity::class , IngredientMealEntity::class, CategoryEntity::class, UserEntity::class, PersonalMealEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class MealDataBase : RoomDatabase(){

    abstract fun getMealDao(): MealDao
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getAreaDao(): AreaDao
    abstract fun getIngredientDao(): IngredientDao
    abstract fun getIngredientMealDao(): IngredientMealDao

    abstract fun getUserDao(): UserDao

    abstract fun getPersonalMealDao(): PersonalMealDao
}