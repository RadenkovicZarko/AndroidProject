package rs.raf.vezbe11.data.repositories

import io.reactivex.Completable
import rs.raf.vezbe11.data.datasources.local.AreaDao
import rs.raf.vezbe11.data.datasources.local.CategoryDao
import rs.raf.vezbe11.data.datasources.local.IngredientDao
import rs.raf.vezbe11.data.datasources.local.IngredientMealDao
import rs.raf.vezbe11.data.datasources.local.MealDao
import rs.raf.vezbe11.data.datasources.remote.MealService
import rs.raf.vezbe11.data.models.MealEntity
import rs.raf.vezbe11.data.models.Resource
import timber.log.Timber
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.MealResponse

import org.json.JSONArray

class MealRepositoryImpl (
    private val localMealSource: MealDao,
    private val localCategorySource: CategoryDao,
    private val localIngredientSource: IngredientDao,
    private val localAreaSource: AreaDao,
    private val localIngredientMealSource: IngredientMealDao,
    private val remoteDataSource: MealService
)  : MealRepository{

    override fun fetchAll(): Observable<Resource<Unit>> {
        return remoteDataSource.getAll().flatMap { mealResponse ->
            val meals = mealResponse.meals
            if(meals != null && meals.isNotEmpty())
            {
                val entities = meals.map {
                    Timber.e(it.strMeal)
                    MealEntity(
                        it.idMeal,
                        it.strMeal,
                        it.strDrinkAlternate,
                        it.strTags,
                        it.strYoutube,
                        it.strSource,
                        it.strImageSource,
                        it.strCreativeCommonsConfirmed,
                        it.dateModified
                    )
                }
                localMealSource.deleteAndInsertAll(entities)
                Observable.just(Resource.Success(Unit))
            }else {
                Observable.just(Resource.Error())
            }

        }
    }

    override fun getAll(): Observable<List<MealEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllByName(name: String): Observable<List<MealEntity>> {
        TODO("Not yet implemented")
    }

    override fun insert(meal: MealEntity): Completable {
        TODO("Not yet implemented")
    }


}