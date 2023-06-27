package rs.raf.vezbe11.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.vezbe11.data.models.PlannerItem
import rs.raf.vezbe11.data.models.Resource
import rs.raf.vezbe11.data.models.entities.MealEntity
import rs.raf.vezbe11.data.models.entities.PersonalMealEntity
import rs.raf.vezbe11.data.models.entities.UserEntity
import rs.raf.vezbe11.data.repositories.MealRepository
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.states.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MealViewModel (private val mealRepository: MealRepository,
) : ViewModel(), MainContract.ViewModel{

    override val subscriptions = CompositeDisposable()
    override val mealState : MutableLiveData<MealState> = MutableLiveData()
    override val userState : MutableLiveData<UserState> = MutableLiveData()
    override val categoryState : MutableLiveData<CategoryState> = MutableLiveData()
    override val numOfMealsState : MutableLiveData<NumOfMealsState> = MutableLiveData()
//    try using .postValue() when changing what is selected for saving
    override val currentPersonalMealSave : MutableLiveData<MealEntity> = MutableLiveData()
    override val currentUser : MutableLiveData<UserEntity> = MutableLiveData()
    override val insertPersonalMeal : MutableLiveData<AddPersonalMealState> = MutableLiveData()
    override val deletePersonalMeal: MutableLiveData<DeletePersonalMealState> = MutableLiveData()
    override val personalMealsState: MutableLiveData<PersonalMealState> = MutableLiveData()
    override val personalOneMealState: MutableLiveData<PersonalMealEntity> = MutableLiveData()
    override val plannerList: MutableLiveData<List<PlannerItem>> = MutableLiveData()
    override val personalMealsDates: MutableLiveData<List<PersonalMealEntity>> = MutableLiveData()
    override val mealDetailsState: MutableLiveData<MealDetailsState>  = MutableLiveData()
    override val ingredientsForMealState: MutableLiveData<IngredientsForMealState> = MutableLiveData()
    override val personalMealUpdate: MutableLiveData<AddPersonalMealState> = MutableLiveData()
    override val filterMealState: MutableLiveData<FilterMealState> = MutableLiveData()
    override val countOfFilterMealState: MutableLiveData<CountOfFilterMealState> = MutableLiveData()
    override fun fetchAllMeals() {
        val subscription = mealRepository
            .fetchAllM()
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealState.value =  MealState.Loading
                        is Resource.Success ->  mealState.value = MealState.DataFetched
                        is Resource.Error ->  mealState.value = MealState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealState.value = MealState.Error("Error happened while fetching data from the server")
                }
            )
        subscriptions.add(subscription)

    }

    override fun fetchAllCategories() {
        val subscription = mealRepository
            .fetchAllC()
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealState.value =  MealState.Loading
                        is Resource.Success ->  mealState.value = MealState.DataFetched
                        is Resource.Error ->  mealState.value = MealState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealState.value = MealState.Error("Error happened while fetching data from the server")
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllAreas() {
        val subscription = mealRepository
            .fetchAllA()
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealState.value =  MealState.Loading
                        is Resource.Success ->  mealState.value = MealState.DataFetched
                        is Resource.Error ->  mealState.value = MealState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealState.value = MealState.Error("Error happened while fetching data from the server")
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllIngredients() {
        val subscription = mealRepository
            .fetchAllI()
            .startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealState.value =  MealState.Loading
                        is Resource.Success ->  mealState.value = MealState.DataFetched
                        is Resource.Error ->  mealState.value = MealState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealState.value = MealState.Error("Error happened while fetching data from the server")
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllCalories() {
        val subscription = mealRepository.fetchAllCalories().startWith(Resource.Loading()) //Kada se pokrene fetch hocemo da postavimo stanje na Loading
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealState.value =  MealState.Loading
                        is Resource.Success ->  mealState.value = MealState.DataFetched
                        is Resource.Error ->  mealState.value = MealState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealState.value = MealState.Error("Error happened while fetching data from the server")
                }
            )
        subscriptions.add(subscription)
    }

    @SuppressLint("CheckResult")
    override fun getCAndMRelations() {
        mealRepository.getCategoryMealRelations() .flatMapIterable { categoryList -> categoryList } // FlatMap the list into individual items
            .subscribe { categoryEntity ->
                categoryEntity.mealsWithCategory.forEach { item -> Timber.e(item.strMeal) }
            }

    }

    override fun findUserWithUsernameAndPassword(username:String, password:String){
            val subscription = mealRepository
                .findUserWithUsernameAndPassword(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        userState.value = UserState.Success(listOf(it))
                    },
                    {
                        userState.value = UserState.Error("Error happened while fetching data from db")
                    }
                )
            subscriptions.add(subscription)
    }



    override fun getMealsByName(name: String) {
        TODO()
    }

    override fun getAllCategories() {
        val subscription = mealRepository
            .getAllCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    categoryState.value = CategoryState.Success(it)
                },
                {
                    categoryState.value = CategoryState.Error("Error happened while fetching data from db")
                }
            )
        subscriptions.add(subscription)
    }

    override fun setPersonalMealForSaving(meal: MealEntity) {
        currentPersonalMealSave.value = meal
    }

    override fun setCurrentUser(user: UserEntity) {
        currentUser.value = user
    }

    override fun getAllPersonalMealsByUser(userId: String){
        val subscription = mealRepository
            .getAllPersonalMealsByUser(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    personalMealsState.value = PersonalMealState.Success(it)
                },
                {
                    personalMealsState.value = PersonalMealState.Error(it.toString())
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getOnePersonalMealsByUser(idUser: String, idMeal: String) {
        val subscription = mealRepository
            .getOnePersonalMealsByUser(idUser, idMeal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if(it.isNotEmpty()){
                        personalOneMealState.value = it[0]
                    }
                },
                {
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun insertPersonalMeal(meal: PersonalMealEntity) {
        val subscription = mealRepository
            .insertPersonalMeal(meal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    insertPersonalMeal.value = AddPersonalMealState.Success
                },
                {
                    insertPersonalMeal.value = AddPersonalMealState.Error("Error happened while fetching data from db")
                }
            )
        subscriptions.add(subscription)
    }

    override fun updatePersonalMeal(meal: PersonalMealEntity) {
        val subscription = mealRepository
            .updatePersonalMeal(meal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    personalMealUpdate.value = AddPersonalMealState.Success
                },
                {
                    personalMealUpdate.value = AddPersonalMealState.Error("Error happened while fetching data from db")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getPersonalMealsBetweenDates(
        startDate: Long,
        endDate: Long,
        idUser: String
    ): List<PersonalMealEntity> {
        return mealRepository
            .getPersonalMealsBetweenDates(startDate, endDate, idUser)
            .blockingFirst() ?: return emptyList()
    }

    var temp = 0.0

    override fun getMealsCount(): Int {
        val count = mealRepository
            .getAllMealsCount()
            .blockingFirst()
        return count?:0
    }

    override fun loadPlannerList() {
        var lst = arrayListOf<PlannerItem>()

        for(i in 0 .. 27)
            lst.add(PlannerItem(calcDay(i), calcTypeMeal(i), null))

        plannerList.value = lst.toList()
        Timber.e(lst.toList().toString())
    }

    private fun calcDay(i: Int): String{
        return when(i/4){
            0 -> "Monday"
            1 -> "Tuesday"
            2 -> "Wednesday"
            3 -> "Thursday"
            4 -> "Friday"
            5 -> "Saturday"
            6 -> "Sunday"
            else -> "Monday"
        }
    }

    private fun calcTypeMeal(i: Int): String{
        return when(i%4){
            0 -> "Breakfast"
            1 -> "Lunch"
            2 -> "Dinner"
            3 -> "Snack"
            else -> "Breakfast"
        }
    }

    override fun getAllMealsForCategory(category: String) {
        val subscription = mealRepository
            .getAllMealsForCategory(category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealState.value = MealState.Success(it)
                },
                {
                    mealState.value = MealState.Error("Error happened while fetching data from db")
                }
            )
        subscriptions.add(subscription)
    }
    override fun deletePersonalMeal(meal: PersonalMealEntity) {
        val subscripiton =mealRepository
            .deletePersonalMeal(meal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    deletePersonalMeal.value = DeletePersonalMealState.Success
                },
                {
                    deletePersonalMeal.value = DeletePersonalMealState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
    }

    override fun getAllMeals() {
        val subscription = mealRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealState.value = MealState.Success(it)
                },
                {
                    mealState.value = MealState.Error("Error happened while fetching data from db")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getMealsInRange(a: Int) {
        val subscription = mealRepository
            .getMealsInRange(a)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealState.value = MealState.Success(it)
                },
                {
                    mealState.value = MealState.Error("Error happened while fetching data from db")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getNumOfMeals(category: String) {
        val subscription = mealRepository
            .getNumOfMeals(category)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    numOfMealsState.value = NumOfMealsState.Success(it)
                },
                {
                    numOfMealsState.value = NumOfMealsState.Error("Error happened while fetching data from db")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getCountFilteredAndSortedMealsBetween(
        meal: String?,
        ingredient: String?,
        minCalories: Double?,
        maxCalories: Double?,
        sort: Int?,
        category: String
    ) {
        publishSubject5.onNext(QueryFilter(meal,ingredient,minCalories,maxCalories,sort,0, category))
    }

    override fun getCountFilteredAndSortedMealsNormal(
        meal: String?,
        ingredient: String?,
        minCalories: Double?,
        maxCalories: Double?,
        sort: Int?,
        category: String
    ) {
        publishSubject4.onNext(QueryFilter(meal,ingredient,minCalories,maxCalories,sort,0, category))
    }

    override fun getFilteredAndSortedMealsBetween(
        meal: String?,
        ingredient: String?,
        minCalories: Double?,
        maxCalories: Double?,
        sort: Int?,a:Int
    ,category: String
    ) {
        publishSubject3.onNext(QueryFilter(meal,ingredient,minCalories,maxCalories,sort,a, category))
    }

    override fun getFilteredAndSortedMealsNormal(
        meal: String?,
        ingredient: String?,
        minCalories: Double?,
        maxCalories: Double?,
        sort: Int?,a:Int
    ,category: String
    ) {
        publishSubject2.onNext(QueryFilter(meal,ingredient,minCalories,maxCalories,sort,a, category))
    }

    override fun getMealById(idMeal: String) {
        val subscription = mealRepository
            .getMealById(idMeal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealDetailsState.value = MealDetailsState.Success(it)
                },
                {
                    mealDetailsState.value = MealDetailsState.Error("Greska")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getIngredientsForMeal(idMeal: String) {
        val subscription = mealRepository
            .getIngredientsForMeal(idMeal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    ingredientsForMealState.value = IngredientsForMealState.Success(it)
                },
                {
                    ingredientsForMealState.value = IngredientsForMealState.Error("Error happened while fetching data from db")
                }
            )
        subscriptions.add(subscription)
    }

    override fun getCountOfFilteredMeals(
        category: String?,
        ingredient: String?,
        area: String?,
        tag: String?,
        meal: String?,
        sort: Int?
    ) {
        publishSubject7.onNext(QueryFilterMeals(category, ingredient, area, tag, meal, sort,0))
    }

    override fun getFilteredMeals(
        category: String?,
        ingredient: String?,
        area: String?,
        tag: String?,
        meal: String?,
        sort: Int?,
        a: Int
    ) {
        publishSubject6.onNext(QueryFilterMeals(category, ingredient, area, tag, meal, sort,a))

    }

    class QueryFilterMeals(
        category: String?,
        ingredient: String?,
        area: String?,
        tag: String?,
        meal: String?,
        sort: Int?,
        a : Int
    ){
        val category = category
        val ingredient = ingredient
        val area = area
        val tag = tag
        val meal = meal
        val sort = sort
        val a = a
    }

    override fun getCaloriesByNameOfIngredientOrMeal(letters: String) {
        publishSubject.onNext(letters)
    }

    private val publishSubject3: PublishSubject<QueryFilter> = PublishSubject.create()
    private val publishSubject5: PublishSubject<QueryFilter> = PublishSubject.create()
    private val publishSubject6: PublishSubject<QueryFilterMeals> = PublishSubject.create()
    private val publishSubject7: PublishSubject<QueryFilterMeals> = PublishSubject.create()
    private val publishSubject2: PublishSubject<QueryFilter> = PublishSubject.create()
    private val publishSubject4: PublishSubject<QueryFilter> = PublishSubject.create()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()
    init {
        val subscriptionMeal3 = publishSubject3
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { it ->
                mealRepository
                    .getFilteredAndSortedMealsBetween(it.meal,it.ingredient,it.minCalories,it.maxCalories,it.sort,it.a,it.category)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                    }
            }
            .subscribe(
                {
                    mealState.value = MealState.Success(it)
                },
                {
                    mealState.value = MealState.Error("Error happened while fetching data from db")
                }
            )
        subscriptions.add(subscriptionMeal3)

        val subscriptionMeal5 = publishSubject5
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { it ->
                mealRepository
                    .getCountFilteredAndSortedMealsNormal(it.meal,it.ingredient,it.minCalories,it.maxCalories,it.sort,it.category)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                    }
            }
            .subscribe(
                {
                    numOfMealsState.value = NumOfMealsState.Success(it)
                },
                {
                    numOfMealsState.value = NumOfMealsState.Error("Error happened while fetching data from db")
                }
            )
        subscriptions.add(subscriptionMeal5)


        val subscriptionMeal6 = publishSubject6
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { it ->
                mealRepository
                    .getFilteredMeals(it.category,it.ingredient,it.area,it.tag,it.meal,it.sort,it.a)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                    }
            }
            .subscribe(
                {
                    filterMealState.value = FilterMealState.Success(it)
                },
                {
                    filterMealState.value = FilterMealState.Error("Error happened while fetching data from db")
                }
            )
        subscriptions.add(subscriptionMeal6)

        val subscriptionMeal7 = publishSubject7
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { it ->
                mealRepository
                    .getCountOfFilteredMeals(it.category,it.ingredient,it.area,it.tag,it.meal,it.sort)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                    }
            }
            .subscribe(
                {
                    countOfFilterMealState.value = CountOfFilterMealState.Success(it)
                },
                {
                    countOfFilterMealState.value = CountOfFilterMealState.Error("Error happened while fetching data from db")
                }
            )
        subscriptions.add(subscriptionMeal7)




        val subscriptionMeal2 = publishSubject2
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { it ->
                mealRepository
                    .getFilteredAndSortedMealsNormal(it.meal,it.ingredient,it.minCalories,it.maxCalories,it.sort,it.a,it.category)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                    }
            }
            .subscribe(
                {
                    mealState.value = MealState.Success(it)
                },
                {
                    mealState.value = MealState.Error("Error happened while fetching data from db")
                }
            )
        subscriptions.add(subscriptionMeal2)

        val subscriptionMeal4 = publishSubject4
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { it ->
                mealRepository
                    .getCountFilteredAndSortedMealsNormal(it.meal,it.ingredient,it.minCalories,it.maxCalories,it.sort,it.category)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                    }
            }
            .subscribe(
                {
                    numOfMealsState.value = NumOfMealsState.Success(it)
                },
                {
                    numOfMealsState.value = NumOfMealsState.Error("Error happened while fetching data from db")
                }
            )
        subscriptions.add(subscriptionMeal4)

        val subscriptionMeal = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { it ->
                mealRepository
                    .getCaloriesByNameOfIngredientOrMeal(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in publish subject")
                    }
            }
            .subscribe(
                {
                    categoryState.value = CategoryState.Success(it)
                },
                {
                    categoryState.value = CategoryState.Error("Error happened while fetching data from db")
                }
            )
        subscriptions.add(subscriptionMeal)
    }



    override fun fetchAllD(): Observable<Resource<Unit>> {
        val fetchMeals = mealRepository.fetchAllM()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
        val fetchCategories = mealRepository.fetchAllC()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
        val fetchIngredients = mealRepository.fetchAllI()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())

        val loadingResource = Resource.Loading<Unit>()

        return Observable.concatArray(
            Observable.just(loadingResource),
            Observable.zip(
                fetchMeals,
                fetchCategories,
                fetchIngredients,
                io.reactivex.functions.Function3 { mealsResult: Resource<Unit>, categoriesResult: Resource<Unit>, ingredientsResult: Resource<Unit> ->
                    // Check if all fetch operations were successful
                    if (mealsResult is Resource.Success && categoriesResult is Resource.Success && ingredientsResult is Resource.Success) {
                        Resource.Success(Unit)
                    } else {
                        Resource.Error()
                    }
                }
            )
        )
    }







    class QueryFilter(meal: String?,
                      ingredient: String?,
                      minCalories: Double?,
                      maxCalories: Double?,
                      sort: Int?,a:Int,category: String){
        val meal: String? = meal
        val ingredient: String? = ingredient
        val minCalories: Double? = minCalories
        val maxCalories: Double? = maxCalories
        val sort: Int? = sort
        val a : Int = a
        val category = category

    }
}