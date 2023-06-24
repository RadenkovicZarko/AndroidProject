package rs.raf.vezbe11.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
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

    private val subscriptions = CompositeDisposable()
    override val mealState : MutableLiveData<MealState> = MutableLiveData()
    override val userState : MutableLiveData<UserState> = MutableLiveData()
    override val categoryState : MutableLiveData<CategoryState> = MutableLiveData()
//    try using .postValue() when changing what is selected for saving
    override val currentPersonalMealSave : MutableLiveData<MealEntity> = MutableLiveData()
    override val currentUser : MutableLiveData<UserEntity> = MutableLiveData()
    override val insertPersonalMeal : MutableLiveData<AddPersonalMealState> = MutableLiveData()
    override val personalMealsState: MutableLiveData<PersonalMealState> = MutableLiveData()

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
                Timber.e("Kategorija")
                Timber.e(categoryEntity.meal.strCategory)
                Timber.e("Jela")
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
                        Timber.e(it)
                    }
                )
            subscriptions.add(subscription)
    }



    override fun getMealsByName(name: String) {
        TODO("Not yet implemented")
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
                    personalMealsState.value = PersonalMealState.Error("Error happened while fetching data from db")
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
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
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

    override fun getCaloriesByNameOfIngredientOrMeal(letters: String) {
        publishSubject.onNext(letters)
    }


    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
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



}