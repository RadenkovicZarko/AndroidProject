package rs.raf.vezbe11.data.repositories

import android.annotation.SuppressLint
import io.reactivex.Completable
import rs.raf.vezbe11.data.datasources.local.AreaDao
import rs.raf.vezbe11.data.datasources.local.CategoryDao
import rs.raf.vezbe11.data.datasources.local.IngredientDao
import rs.raf.vezbe11.data.datasources.local.IngredientMealDao
import rs.raf.vezbe11.data.datasources.local.MealDao
import rs.raf.vezbe11.data.datasources.remote.MealService
import rs.raf.vezbe11.data.models.Resource
import timber.log.Timber
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import rs.raf.vezbe11.data.datasources.remote.CalorieService
import rs.raf.vezbe11.data.models.entities.*

import rs.raf.vezbe11.data.models.relations.CategoryMealRelation
import rs.raf.vezbe11.data.models.responses.CalorieReponse
import java.util.PrimitiveIterator

class MealRepositoryImpl (
    private val localMealSource: MealDao,
    private val localCategorySource: CategoryDao,
    private val localIngredientSource: IngredientDao,
    private val localAreaSource: AreaDao,
    private val localIngredientMealSource: IngredientMealDao,
    private val remoteDataSource: MealService,
    private val caloriesRemoteDataSource : CalorieService
)  : MealRepository{

    override fun fetchAllM(): Observable<Resource<Unit>> {
        return remoteDataSource.getAllMeals().flatMap { mealResponse ->
            val meals = mealResponse.meals
            if(meals != null && meals.isNotEmpty())
            {
                val entities = meals.map {
                    MealEntity(
                        it.idMeal,
                        it.strMeal,
                        it.strDrinkAlternate,
                        it.strCategory,
                        it.strArea,
                        it.strInstructions,
                        it.strMealThumb,
                        it.strTags,
                        it.strYoutube,
                        it.strSource,
                        it.strImageSource,
                        it.strCreativeCommonsConfirmed,
                        it.dateModified
                    )
                }

                val entitiesIngredientMeal = meals.flatMap {
                    listOf(justDoIt(it.idMeal,it.strIngredient1,it.strMeasure1),
                    justDoIt(it.idMeal,it.strIngredient2,it.strMeasure2),
                    justDoIt(it.idMeal,it.strIngredient3,it.strMeasure3),
                    justDoIt(it.idMeal,it.strIngredient4,it.strMeasure4),
                    justDoIt(it.idMeal,it.strIngredient5,it.strMeasure5),
                    justDoIt(it.idMeal,it.strIngredient6,it.strMeasure6),
                    justDoIt(it.idMeal,it.strIngredient7,it.strMeasure7),
                    justDoIt(it.idMeal,it.strIngredient8,it.strMeasure8),
                    justDoIt(it.idMeal,it.strIngredient9,it.strMeasure9),
                    justDoIt(it.idMeal,it.strIngredient10,it.strMeasure10),
                    justDoIt(it.idMeal,it.strIngredient11,it.strMeasure11),
                    justDoIt(it.idMeal,it.strIngredient12,it.strMeasure12),
                    justDoIt(it.idMeal,it.strIngredient13,it.strMeasure13),
                    justDoIt(it.idMeal,it.strIngredient14,it.strMeasure14),
                    justDoIt(it.idMeal,it.strIngredient15,it.strMeasure15),
                    justDoIt(it.idMeal,it.strIngredient16,it.strMeasure16),
                    justDoIt(it.idMeal,it.strIngredient17,it.strMeasure17),
                    justDoIt(it.idMeal,it.strIngredient18,it.strMeasure18),
                    justDoIt(it.idMeal,it.strIngredient19,it.strMeasure19),
                    justDoIt(it.idMeal,it.strIngredient20,it.strMeasure20)).filter { i -> i!=null && i.idIngredient!="" }.map{ it as IngredientMealEntity}
                }
                Timber.e(entitiesIngredientMeal.size.toString())
                localIngredientMealSource.deleteAndInsertAll(entitiesIngredientMeal)
                localMealSource.deleteAndInsertAll(entities)
                Observable.just(Resource.Success(Unit))
            }else {
                Observable.just(Resource.Error())
            }

        }
    }
    private fun justDoIt(idMeal: String, strIngredient:String?,strMeasure:String? ):IngredientMealEntity? {
        if(strIngredient!=null && strIngredient!="")
        {
            var it = localIngredientSource.loadSingle(strIngredient)
            var calories = 0.0
            val prom = caloriesRemoteDataSource.getCalories(it.strIngredient).blockingFirst().forEach{it1->
                        calories = it1.calories
                    }
            it.calories = calories
            localIngredientSource.updateCalories(it)
        }

        val ingredientMealEntity = strIngredient?.let {
            IngredientMealEntity(
                idMeal,
                it,
                strMeasure
            )
        }
        return ingredientMealEntity
    }
    override fun fetchAllC(): Observable<Resource<Unit>> {
        return remoteDataSource.getAllCategories().flatMap { categoryResponse ->
            val categories = categoryResponse.categories
            if(categories != null && categories.isNotEmpty())
            {
                val entities = categories.map {
                    CategoryEntity(
                        it.idCategory,
                        it.strCategory,
                        it.strCategoryThumb,
                        it.strCategoryDescription
                    )

                }
                localCategorySource.deleteAndInsertAll(entities)
                Observable.just(Resource.Success(Unit))
            }else {
                Observable.just(Resource.Error())
            }

        }
    }

    override fun fetchAllA(): Observable<Resource<Unit>> {
        return remoteDataSource.getAllAreas().flatMap { areaResponse ->
            val areas = areaResponse.meals
            if(areas != null && areas.isNotEmpty())
            {
                val entities = areas.map {
                    AreaEntity(
                        it.strArea
                    )
                }
                localAreaSource.deleteAndInsertAll(entities)
                Observable.just(Resource.Success(Unit))
            }else {
                Observable.just(Resource.Error())
            }

        }
    }

    override fun fetchAllI(): Observable<Resource<Unit>> {
        return remoteDataSource.getAllIngredients().flatMap { ingredientResponse ->
            val ingredients = ingredientResponse.meals
            if(ingredients != null && ingredients.isNotEmpty())
            {
                val entities = ingredients.map {it->
//                    var calories = 0.0
//                    val prom = caloriesRemoteDataSource.getCalories(it.strIngredient).blockingFirst().forEach{it1->
//                        calories = it1.calories
//
//                    }
                    IngredientEntity(
                        it.strIngredient,
                        it.strIngredient,
                        it.strDescription,
                        it.strType,
                        null
                    )
                }
                localIngredientSource.deleteAndInsertAll(entities)
                Observable.just(Resource.Success(Unit))
            }else {
                Observable.just(Resource.Error())
            }
        }
    }


    override fun fetchAllCalories(): Observable<Resource<Unit>> {
        val localIngredients = localIngredientSource.getAll()
       return localIngredients
            .doOnNext {it1->
                it1.forEach{it2->
                    caloriesRemoteDataSource.getCalories(it2.strIngredient) .doOnNext{it3->
                        it3.forEach {it4->
                            Timber.e("Novi sastojak")
                            Timber.e(it2.strIngredient)
                            Timber.e(it4.calories.toString())
                        }
                    }

                }
            }
           .map {
               Resource.Success(Unit)
           }
    }


    override fun getAll(): Observable<List<MealEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllMeals(): Observable<List<MealEntity>> {
        TODO("Not yet implemented")
    }

    override fun getAllByName(name: String): Observable<List<MealEntity>> {
        TODO("Not yet implemented")
    }

    override fun getCategoryMealRelations(): Observable<List<CategoryMealRelation>> {
        return localCategorySource. getCategoryMealRelations()
    }

    override fun insert(meal: MealEntity): Completable {
        TODO("Not yet implemented")
    }




}