package rs.raf.vezbe11.data.repositories

import android.annotation.SuppressLint
import io.reactivex.Completable
import rs.raf.vezbe11.data.datasources.remote.MealService
import rs.raf.vezbe11.data.models.Resource
import timber.log.Timber
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.processNextEventInCurrentThread
import okhttp3.internal.wait
import rs.raf.vezbe11.data.datasources.local.*
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
    private val localUserSource: UserDao,
    private val remoteDataSource: MealService,
    private val caloriesRemoteDataSource : CalorieService
)  : MealRepository{

    @SuppressLint("CheckResult")
    override fun fetchAllM(): Observable<Resource<Unit>> {
        return remoteDataSource.getAllMealsId().flatMap { mealResponse ->
            val meals = mealResponse.meals

            if (meals != null && meals.isNotEmpty()) {
                localMealSource.deleteAll()
                localIngredientMealSource.deleteAll()
                meals.forEach {
                        remoteDataSource.getMealById(it.idMeal).forEach { mealResponse2 ->
                            Timber.e("DESILO SE")
                            val meals2 = mealResponse2.meals
                            if ( meals2.isNotEmpty()) {
                                val entitie = meals2.map {
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

                                val entitieIngredientMeal = meals2.flatMap {
                                    listOf(
                                        justDoIt(it.idMeal, it.strIngredient1, it.strMeasure1),
                                        justDoIt(it.idMeal, it.strIngredient2, it.strMeasure2),
                                        justDoIt(it.idMeal, it.strIngredient3, it.strMeasure3),
                                        justDoIt(it.idMeal, it.strIngredient4, it.strMeasure4),
                                        justDoIt(it.idMeal, it.strIngredient5, it.strMeasure5),
                                        justDoIt(it.idMeal, it.strIngredient6, it.strMeasure6),
                                        justDoIt(it.idMeal, it.strIngredient7, it.strMeasure7),
                                        justDoIt(it.idMeal, it.strIngredient8, it.strMeasure8),
                                        justDoIt(it.idMeal, it.strIngredient9, it.strMeasure9),
                                        justDoIt(it.idMeal, it.strIngredient10, it.strMeasure10),
                                        justDoIt(it.idMeal, it.strIngredient11, it.strMeasure11),
                                        justDoIt(it.idMeal, it.strIngredient12, it.strMeasure12),
                                        justDoIt(it.idMeal, it.strIngredient13, it.strMeasure13),
                                        justDoIt(it.idMeal, it.strIngredient14, it.strMeasure14),
                                        justDoIt(it.idMeal, it.strIngredient15, it.strMeasure15),
                                        justDoIt(it.idMeal, it.strIngredient16, it.strMeasure16),
                                        justDoIt(it.idMeal, it.strIngredient17, it.strMeasure17),
                                        justDoIt(it.idMeal, it.strIngredient18, it.strMeasure18),
                                        justDoIt(it.idMeal, it.strIngredient19, it.strMeasure19),
                                        justDoIt(it.idMeal, it.strIngredient20, it.strMeasure20)
                                    ).filter { i -> i != null && i.idIngredient != "" }
                                        .map { it as IngredientMealEntity }
                                }
                                Timber.e(entitieIngredientMeal.size.toString())
                                Timber.e(entitie.size.toString())
                                localIngredientMealSource.insertAll(entitieIngredientMeal).blockingAwait()
                                localMealSource.insertAll(entitie).blockingAwait()
                            }
                            Observable.just(Resource.Success(Unit))
                        }
                    }

                Observable.just(Resource.Success(Unit))
            }
            else {
                Observable.just(Resource.Error())
            }

        }


//        return remoteDataSource.getAllMeals().flatMap { mealResponse ->
//            val meals = mealResponse.meals
//            if(meals != null && meals.isNotEmpty())
//            {
//                val entities = meals.map {
//                    MealEntity(
//                        it.idMeal,
//                        it.strMeal,
//                        it.strDrinkAlternate,
//                        it.strCategory,
//                        it.strArea,
//                        it.strInstructions,
//                        it.strMealThumb,
//                        it.strTags,
//                        it.strYoutube,
//                        it.strSource,
//                        it.strImageSource,
//                        it.strCreativeCommonsConfirmed,
//                        it.dateModified
//                    )
//                }
//
//                val entitiesIngredientMeal = meals.flatMap {
//                    listOf(justDoIt(it.idMeal,it.strIngredient1,it.strMeasure1),
//                    justDoIt(it.idMeal,it.strIngredient2,it.strMeasure2),
//                    justDoIt(it.idMeal,it.strIngredient3,it.strMeasure3),
//                    justDoIt(it.idMeal,it.strIngredient4,it.strMeasure4),
//                    justDoIt(it.idMeal,it.strIngredient5,it.strMeasure5),
//                    justDoIt(it.idMeal,it.strIngredient6,it.strMeasure6),
//                    justDoIt(it.idMeal,it.strIngredient7,it.strMeasure7),
//                    justDoIt(it.idMeal,it.strIngredient8,it.strMeasure8),
//                    justDoIt(it.idMeal,it.strIngredient9,it.strMeasure9),
//                    justDoIt(it.idMeal,it.strIngredient10,it.strMeasure10),
//                    justDoIt(it.idMeal,it.strIngredient11,it.strMeasure11),
//                    justDoIt(it.idMeal,it.strIngredient12,it.strMeasure12),
//                    justDoIt(it.idMeal,it.strIngredient13,it.strMeasure13),
//                    justDoIt(it.idMeal,it.strIngredient14,it.strMeasure14),
//                    justDoIt(it.idMeal,it.strIngredient15,it.strMeasure15),
//                    justDoIt(it.idMeal,it.strIngredient16,it.strMeasure16),
//                    justDoIt(it.idMeal,it.strIngredient17,it.strMeasure17),
//                    justDoIt(it.idMeal,it.strIngredient18,it.strMeasure18),
//                    justDoIt(it.idMeal,it.strIngredient19,it.strMeasure19),
//                    justDoIt(it.idMeal,it.strIngredient20,it.strMeasure20)).filter { i -> i!=null && i.idIngredient!="" }.map{ it as IngredientMealEntity}
//                }
//                Timber.e(entitiesIngredientMeal.size.toString())
//                localIngredientMealSource.deleteAndInsertAll(entitiesIngredientMeal)
//                localMealSource.deleteAndInsertAll(entities)
//                Observable.just(Resource.Success(Unit))
//            }else {
//                Observable.just(Resource.Error())
//            }
//
//        }
    }
    @SuppressLint("CheckResult")
    private fun justDoIt(idMeal: String, strIngredient:String?, strMeasure:String? ):IngredientMealEntity? {
//        if(strIngredient!=null && strIngredient!="")
//        {
//
//            var it : IngredientEntity? = localIngredientSource.loadSingle(strIngredient)
//
//
//                if (it!=null && it.strIngredient!="") {
//                    var calories = 0.0
//                    val prom =
//                        caloriesRemoteDataSource.getCalories(it.strIngredient).blockingFirst()
//                            .forEach { it1 ->
//                                calories = it1.calories
//                            }
//                    it.calories = calories
//                    localIngredientSource.updateCalories(it)
//                }
//
//        }

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
                var i =0
                val entities = ingredients.map {it->
                    var calories = 0.0
                    val prom = caloriesRemoteDataSource.getCalories(it.strIngredient).blockingFirst().forEach{it1->
                        calories += it1.calories
                    }
                    Timber.e(i.toString())
                    i++
                    Timber.e(calories.toString())
                    Timber.e("Desilo se")
                    IngredientEntity(
                        it.strIngredient,
                        it.strIngredient,
                        it.strDescription,
                        it.strType,
                        calories
                    )
                }

                localIngredientSource.deleteAndInsertAll(entities)
                Observable.just(Resource.Success(Unit))
            }else {
               Observable.just(Resource.Error())
            }
        }
    }

//    private fun makeString () : String
//    {
//        val localIngredients = localIngredientSource.getAll()
//        var allIngredients =""
//        Timber.e("Desilo se1")
//        localIngredients.forEach{it.forEach {it2->
//            if(allIngredients=="")
//                allIngredients+=it2.strIngredient
//            else
//                allIngredients+=" and " + it2.strIngredient
//            }
//        }
//        return allIngredients
//    }

    @SuppressLint("CheckResult")
    override fun fetchAllCalories(): Observable<Resource<Unit>> {

        var allIngredients = ""
        var listAllIngredients: List<String> = listOf()
        Timber.e("Desilo se1")
        localIngredientSource.getAll().blockingFirst().forEach { it2 ->
            var str = ""
            if (allIngredients == "") {
                str = allIngredients + it2.strIngredient
                if (str.length >= 500) {
                    listAllIngredients += allIngredients
                    str = it2.strIngredient
                }
            } else {
                str = allIngredients + " and " + it2.strIngredient
                if (str.length >= 500) {
                    listAllIngredients += allIngredients
                    str = it2.strIngredient
                }
            }
            if(str.equals("Gherkin Relish"))
                Timber.e("TOOOOO")

            allIngredients = str
        }
        listAllIngredients+=allIngredients


        Timber.e("DEsilo se 2")
        listAllIngredients.forEach { Timber.e(it) }
        var lista : List<IngredientEntity> = listOf()
        return caloriesRemoteDataSource.getCalories(listAllIngredients.get(0)).flatMap{ it ->
               var name = ""
               var calories = 0.0
               Timber.e("USAO123")
               it.forEach { it1 ->
                   var p = false
                   val capitalizedStr = it1.name.split(" ").joinToString(" ") { it.capitalize() }
                   name += capitalizedStr
                   calories += it1.calories
                   Timber.e("--------------------------------------------")
                   Timber.e(name)
                   listAllIngredients.forEach { it2 ->
                       if(name == "Soda") {
                           p =true
                           name = "Bicarbonate Of Soda"
                       }

                       val words = it2.split(" and ")
                       if (words.contains(name)) p = true
                       Timber.e(it2)
                   }

                   if (p == true) {
                       Timber.e("USAO1")
                       var ing = localIngredientSource.loadSingle(name)
                       ing.calories = calories
                       Timber.e("USAO2")
//                       localIngredientSource.updateCalories(ing)
                       lista+=ing
                       name = ""
                       Timber.e("USAO3")
                       calories = 0.0
                   } else {
                       name += " "
                   }
//                    if (it1.name != "") {
//                        Timber.e(it1.name.capitalize())
//                        var ing = localIngredientSource.loadSingle(it1.name.capitalize())
//
//                        Timber.e("Prosao")
//                        ing.calories = it1.calories
//                        Timber.e("Prosao2")
//                        localIngredientSource.updateCalories(ing)
//                    }
               }

               Observable.just(Resource.Success(Unit))
           }


//       listAllIngredients.forEach { it0 ->
//           Timber.e(it0)
//
//           caloriesRemoteDataSource.getCalories(it0).forEach{ it ->
//               var name = ""
//               var calories = 0.0
//               Timber.e("USAO123")
//               it.forEach { it1 ->
//                   var p = false
//                   val capitalizedStr = it1.name.split(" ").joinToString(" ") { it.capitalize() }
//                   name += capitalizedStr
//                   calories += it1.calories
//                   Timber.e("--------------------------------------------")
//                   Timber.e(name)
//                   listAllIngredients.forEach { it2 ->
//                       val words = it2.split(" and ")
//                       if (words.contains(name)) p = true
//                   }
//
//                   if (p == true) {
//                       Timber.e("USAO1")
//                       var ing = localIngredientSource.loadSingle(name)
//                       ing.calories = calories
//                       Timber.e("USAO2")
////                       localIngredientSource.updateCalories(ing)
//                       lista+=ing
//                       name = ""
//                       Timber.e("USAO3")
//                       calories = 0.0
//                   } else {
//                       name += " "
//                   }
////                    if (it1.name != "") {
////                        Timber.e(it1.name.capitalize())
////                        var ing = localIngredientSource.loadSingle(it1.name.capitalize())
////
////                        Timber.e("Prosao")
////                        ing.calories = it1.calories
////                        Timber.e("Prosao2")
////                        localIngredientSource.updateCalories(ing)
////                    }
//               }
//
//               Observable.just(Resource.Success(Unit))
//           }
//       }
//
//        localIngredientSource.deleteAndInsertAll(lista)
//


//        return Observable.just(Resource.Success(Unit))

//        listAllIngredients.forEach { it ->
//            Timber.e(it)
//            caloriesRemoteDataSource.getCalories(it).forEach { it ->
//                Timber.e(it.size.toString())
//                it.forEach { it1 ->
//                    Timber.e("Desilo se")
//                    if (it1.name != "") {
//                        var ing = localIngredientSource.loadSingle(it1.name)
//                        ing.calories = it1.calories
//                        localIngredientSource.updateCalories(ing)
//                    }
//                }
//                Observable.just(Resource.Success(Unit))
//            }
//
//        }
//
//        return Observable.just(Resource.Success(Unit))


//        var i = 0
//        var listIngredientEntity : List<IngredientEntity> = listOf()
//        localIngredientSource.getAll().forEach { it1 ->
//            it1.map { it2 ->
//                Timber.e(i.toString())
//                i++
//                var calories = 0.0
//                caloriesRemoteDataSource.getCalories(it2.strIngredient).forEach { it3 ->
//                    it3.forEach { it4 ->
//                        calories = it4.calories
//                    }
//                }
//                it2.calories = calories
//                listIngredientEntity+=it2
//            }
//
//        }
//       listIngredientEntity.forEach{
//            it->
//            localIngredientSource.updateCalories(it)
//        }
//
//        return Observable.just(Resource.Success(Unit))



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

    override fun findUserWithUsernameAndPassword(username:String, password:String): Observable<UserEntity> {
        return localUserSource.findUserWithUsernameAndPassword(username, password)

    }

    override fun insert(meal: MealEntity): Completable {
        TODO("Not yet implemented")
    }

    override fun getCaloriesByNameOfIngredientOrMeal(letters: String): Observable<List<CategoryEntity>> {
        return localCategorySource.getCaloriesByNameOfIngredientOrMeal(letters)
    }

    override fun getAllCategories(): Observable<List<CategoryEntity>> {
        return localCategorySource.getAll()
    }


}