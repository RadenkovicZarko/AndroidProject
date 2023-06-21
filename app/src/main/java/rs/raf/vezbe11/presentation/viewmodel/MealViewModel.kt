package rs.raf.vezbe11.presentation.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.vezbe11.data.models.Resource
import rs.raf.vezbe11.data.repositories.MealRepository
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.states.CategoryState
import rs.raf.vezbe11.presentation.view.states.MealState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MealViewModel (private val mealRepository: MealRepository,
) : ViewModel(), MainContract.ViewModel{


    private val subscriptions = CompositeDisposable()
    override val mealState : MutableLiveData<MealState> = MutableLiveData()
    override val categoryState : MutableLiveData<CategoryState> = MutableLiveData()

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

    override fun getMealsByName(name: String) {
        TODO("Not yet implemented")
    }


    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        val subscriptionMeal = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap { it ->
                mealRepository
                    .getAllByName(it)
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
        subscriptions.add(subscriptionMeal)


    }



}