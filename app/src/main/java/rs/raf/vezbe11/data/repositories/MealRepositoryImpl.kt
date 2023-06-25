package rs.raf.vezbe11.data.repositories

import android.annotation.SuppressLint
import io.reactivex.Completable
import rs.raf.vezbe11.data.datasources.remote.MealService
import rs.raf.vezbe11.data.models.Resource
import timber.log.Timber
import io.reactivex.Observable
import rs.raf.vezbe11.data.datasources.local.*
import rs.raf.vezbe11.data.datasources.remote.CalorieService
import rs.raf.vezbe11.data.models.entities.*

import rs.raf.vezbe11.data.models.relations.CategoryMealRelation
import java.sql.Time
import java.text.DecimalFormat

class MealRepositoryImpl (
    private val localMealSource: MealDao,
    private val localCategorySource: CategoryDao,
    private val localIngredientSource: IngredientDao,
    private val localAreaSource: AreaDao,
    private val localIngredientMealSource: IngredientMealDao,
    private val localUserSource: UserDao,
    private val remoteDataSource: MealService,
    private val caloriesRemoteDataSource : CalorieService,
    private val localPersonalMealSource: PersonalMealDao
)  : MealRepository{

    @SuppressLint("CheckResult")
    override fun fetchAllM(): Observable<Resource<Unit>> {
        return remoteDataSource.getAllMealsId().flatMap { mealResponse ->
            val meals = mealResponse.meals

            if (meals.isNotEmpty()) {
                localMealSource.deleteAll()
                localIngredientMealSource.deleteAll()
                meals.forEach {
                        remoteDataSource.getMealById(it.idMeal).forEach { mealResponse2 ->
                            val meals2 = mealResponse2.meals
                            if ( meals2.isNotEmpty()) {


                                fun parseValueToGrams(value: String?): Double {
                                    if(value==null)
                                        return 0.0
                                    val numericRegex = Regex("[0-9]+(\\.[0-9]+)?") // Regex to extract numeric values
                                    val quantity = numericRegex.find(value)?.value?.toDoubleOrNull() ?: 0.0

                                    val unitConversionTable = mapOf(
                                        "g" to 1.0,          // gram
                                        "oz" to 28.3495,     // ounce
                                        "ml" to 1.0,         // milliliter
                                        "fl" to 29.5735,     // fluid ounce
                                        "tbs" to 15.0,       // tablespoon
                                        "cup" to 236.588    // cup
                                        // Add more conversions as needed
                                    )

                                    val unitRegex = Regex("[a-zA-Z]+") // Regex to extract unit of measurement
                                    val unit = unitRegex.find(value)?.value ?: ""

                                    return (quantity * (unitConversionTable[unit.toLowerCase()] ?: 1.0)).takeIf { quantity != 0.0 } ?: 0.0
                                }

                                @SuppressLint("CheckResult")
                                fun justDoIt(idMeal: String, strIngredient:String?, strMeasure:Double? ):IngredientMealEntity? {
                                    val ingredientMealEntity = strIngredient?.let {
                                        IngredientMealEntity(
                                            idMeal,
                                            it,
                                            strMeasure
                                        )
                                    }
                                    return ingredientMealEntity
                                }

                                fun sumOfCaloriesInMeal(strIngredient1 : String?, strMeasure1 :  String? ,
                                                        strIngredient2 : String?, strMeasure2  : String? ,
                                                        strIngredient3 : String?, strMeasure3  : String? ,
                                                        strIngredient4 : String?, strMeasure4  : String? ,
                                                        strIngredient5 : String?, strMeasure5  : String? ,
                                                        strIngredient6 : String?, strMeasure6  : String? ,
                                                        strIngredient7 : String?, strMeasure7  : String? ,
                                                        strIngredient8 : String?, strMeasure8  : String? ,
                                                        strIngredient9 : String?, strMeasure9  : String? ,
                                                        strIngredient10: String?, strMeasure10 : String? ,
                                                        strIngredient11: String?, strMeasure11 : String? ,
                                                        strIngredient12: String?, strMeasure12 : String? ,
                                                        strIngredient13: String?, strMeasure13 : String? ,
                                                        strIngredient14: String?, strMeasure14 : String? ,
                                                        strIngredient15: String?, strMeasure15 : String? ,
                                                        strIngredient16: String?, strMeasure16 : String? ,
                                                        strIngredient17: String?, strMeasure17 : String? ,
                                                        strIngredient18: String?, strMeasure18 : String? ,
                                                        strIngredient19: String?, strMeasure19 : String? ,
                                                        strIngredient20: String?, strMeasure20 : String?  ) : Double
                                {
                                    val listaObroka = mutableListOf<String?>()
                                    val listaMeasures = mutableListOf<String?>()
                                    if(strIngredient1!=null) { listaObroka+=strIngredient1; listaMeasures+=strMeasure1}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient2; listaMeasures+=strMeasure2}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient3; listaMeasures+=strMeasure3}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient4; listaMeasures+=strMeasure4}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient5; listaMeasures+=strMeasure5}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient6; listaMeasures+=strMeasure6}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient7; listaMeasures+=strMeasure7}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient8; listaMeasures+=strMeasure8}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient9; listaMeasures+=strMeasure9}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient10; listaMeasures+=strMeasure10}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient11; listaMeasures+=strMeasure11}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient12; listaMeasures+=strMeasure12}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient13; listaMeasures+=strMeasure13}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient14; listaMeasures+=strMeasure14}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient15; listaMeasures+=strMeasure15}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient16; listaMeasures+=strMeasure16}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient17; listaMeasures+=strMeasure17}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient18; listaMeasures+=strMeasure18}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient19; listaMeasures+=strMeasure19}
                                    if(strIngredient1!=null) { listaObroka+=strIngredient20; listaMeasures+=strMeasure20}


                                    var suma = 0.0
                                    var  i = 0
                                    listaObroka.forEach {
                                        val ingredient= it?.let { it1 -> localIngredientSource.getIngredientByName(it1) }
                                        val measure = parseValueToGrams(listaMeasures.get(i))
                                        i++
                                        suma+= ingredient?.calories?.times(measure) ?: 0.0

                                    }
                                    val decimalFormat = DecimalFormat("#.##")
                                    val roundedValue = decimalFormat.format(suma).toDouble()
                                    return roundedValue

                                }


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
                                        it.dateModified,
                                        sumOfCaloriesInMeal(it.strIngredient1 ,it.strMeasure1,
                                                it.strIngredient2 , it.strMeasure2,
                                                it.strIngredient3 , it.strMeasure3,
                                                it.strIngredient4 , it.strMeasure4,
                                                it.strIngredient5 , it.strMeasure5,
                                                it.strIngredient6 , it.strMeasure6,
                                                it.strIngredient7 , it.strMeasure7,
                                                it.strIngredient8 , it.strMeasure8,
                                                it.strIngredient9 , it.strMeasure9,
                                                it.strIngredient10, it.strMeasure10,
                                                it.strIngredient11, it.strMeasure11,
                                                it.strIngredient12, it.strMeasure12,
                                                it.strIngredient13, it.strMeasure13,
                                                it.strIngredient14, it.strMeasure14,
                                                it.strIngredient15, it.strMeasure15,
                                                it.strIngredient16, it.strMeasure16,
                                                it.strIngredient17, it.strMeasure17,
                                                it.strIngredient18, it.strMeasure18,
                                                it.strIngredient19, it.strMeasure19,
                                                it.strIngredient20, it.strMeasure20)
                                    )
                                }

                                val entitieIngredientMeal = meals2.flatMap {
                                    listOf(
                                        justDoIt(it.idMeal, it.strIngredient1 , parseValueToGrams(it.strMeasure1) ),
                                        justDoIt(it.idMeal, it.strIngredient2 , parseValueToGrams(it.strMeasure2) ),
                                        justDoIt(it.idMeal, it.strIngredient3 , parseValueToGrams(it.strMeasure3) ),
                                        justDoIt(it.idMeal, it.strIngredient4 , parseValueToGrams(it.strMeasure4) ),
                                        justDoIt(it.idMeal, it.strIngredient5 , parseValueToGrams(it.strMeasure5) ),
                                        justDoIt(it.idMeal, it.strIngredient6 , parseValueToGrams(it.strMeasure6) ),
                                        justDoIt(it.idMeal, it.strIngredient7 , parseValueToGrams(it.strMeasure7) ),
                                        justDoIt(it.idMeal, it.strIngredient8 , parseValueToGrams(it.strMeasure8) ),
                                        justDoIt(it.idMeal, it.strIngredient9 , parseValueToGrams(it.strMeasure9) ),
                                        justDoIt(it.idMeal, it.strIngredient10, parseValueToGrams(it.strMeasure10)),
                                        justDoIt(it.idMeal, it.strIngredient11, parseValueToGrams(it.strMeasure11)),
                                        justDoIt(it.idMeal, it.strIngredient12, parseValueToGrams(it.strMeasure12)),
                                        justDoIt(it.idMeal, it.strIngredient13, parseValueToGrams(it.strMeasure13)),
                                        justDoIt(it.idMeal, it.strIngredient14, parseValueToGrams(it.strMeasure14)),
                                        justDoIt(it.idMeal, it.strIngredient15, parseValueToGrams(it.strMeasure15)),
                                        justDoIt(it.idMeal, it.strIngredient16, parseValueToGrams(it.strMeasure16)),
                                        justDoIt(it.idMeal, it.strIngredient17, parseValueToGrams(it.strMeasure17)),
                                        justDoIt(it.idMeal, it.strIngredient18, parseValueToGrams(it.strMeasure18)),
                                        justDoIt(it.idMeal, it.strIngredient19, parseValueToGrams(it.strMeasure19)),
                                        justDoIt(it.idMeal, it.strIngredient20, parseValueToGrams(it.strMeasure20))
                                    ).filter { i -> i != null && i.idIngredient != "" }
                                        .map { it as IngredientMealEntity }
                                }
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
            if(areas.isNotEmpty())
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
            if(ingredients.isNotEmpty())
            {
                var i =0
                val entities = ingredients.map {it->
                    var calories = 0.0
                    val prom = caloriesRemoteDataSource.getCalories(it.strIngredient).blockingFirst().forEach{it1->
                        calories += it1.calories
                    }
                    Timber.e(i.toString())
                    i++
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
    override fun insertPersonalMeal(meal: PersonalMealEntity): Completable {
        return localPersonalMealSource.insert(meal)

    }
    override fun getAllPersonalMealsByUser(idUser: String): Observable<List<PersonalMealEntity>> {
        return localPersonalMealSource.getAllPersonalMealsByUser(idUser)

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
        val listAllIngredients: MutableList<String> = mutableListOf()
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


            allIngredients = str
        }
        listAllIngredients+=allIngredients


        listAllIngredients.forEach { Timber.e(it) }
        var lista : List<IngredientEntity> = listOf()
        return caloriesRemoteDataSource.getCalories(listAllIngredients.get(0)).flatMap{ it ->
               var name = ""
               var calories = 0.0
               it.forEach { it1 ->
                   var p = false
                   val capitalizedStr = it1.name.split(" ").joinToString(" ") { it.capitalize() }
                   name += capitalizedStr
                   calories += it1.calories
                   listAllIngredients.forEach { it2 ->
                       if(name == "Soda") {
                           p =true
                           name = "Bicarbonate Of Soda"
                       }

                       val words = it2.split(" and ")
                       if (words.contains(name)) p = true
                   }

                   if (p == true) {
                       val ing = localIngredientSource.loadSingle(name)
                       ing.calories = calories
//                       localIngredientSource.updateCalories(ing)
                       lista+=ing
                       name = ""
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

    override fun getAllMealsForCategory(category: String): Observable<List<MealEntity>> {
        return localMealSource.getAllMealsForCategory(category)
    }

    override fun getMealsInRange(a: Int): Observable<List<MealEntity>> {
        return localMealSource.getMealsInRange(a)
    }

    override fun getNumOfMeals(category: String):Observable<Int>{
        return localMealSource.getNumOfMeals(category)
    }

    override fun getCountFilteredAndSortedMealsBetween(
        meal: String?,
        ingredient: String?,
        minCalories: Double?,
        maxCalories: Double?,
        sort: Int?,

    ): Observable<Int> {
        return localMealSource.getCountFilteredAndSortedMealsBetween(meal,ingredient,minCalories,maxCalories,sort)
    }

    override fun getCountFilteredAndSortedMealsNormal(
        meal: String?,
        ingredient: String?,
        minCalories: Double?,
        maxCalories: Double?,
        sort: Int?,

    ): Observable<Int> {
        return localMealSource.getCountFilteredAndSortedMealsNormal(meal,ingredient,minCalories,maxCalories,sort)
    }

    override fun getFilteredAndSortedMealsBetween(
        meal: String?,
        ingredient: String?,
        minCalories: Double?,
        maxCalories: Double?,
        sort: Int?,a:Int
    ): Observable<List<MealEntity>> {

        return localMealSource.getFilteredAndSortedMealsBetween(meal,ingredient,minCalories,maxCalories,sort,a)
    }

    override fun getFilteredAndSortedMealsNormal(
        meal: String?,
        ingredient: String?,
        minCalories: Double?,
        maxCalories: Double?,
        sort: Int?,a:Int
    ): Observable<List<MealEntity>> {

        return localMealSource.getFilteredAndSortedMealsNormal(meal,ingredient,minCalories,maxCalories,sort,a)
    }


}