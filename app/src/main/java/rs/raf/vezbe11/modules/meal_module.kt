package rs.raf.vezbe11.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.vezbe11.data.datasources.local.MealDataBase
import rs.raf.vezbe11.data.datasources.local.UserDao
import rs.raf.vezbe11.data.datasources.remote.CalorieService
import rs.raf.vezbe11.data.datasources.remote.MealService
import rs.raf.vezbe11.data.repositories.MealRepository
import rs.raf.vezbe11.data.repositories.MealRepositoryImpl
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel


val mealModule = module {
    viewModel { MealViewModel(mealRepository = get()) }

    single<MealRepository> { MealRepositoryImpl(localMealSource = get(),localCategorySource = get(), localIngredientSource = get(),
        localAreaSource = get(), localIngredientMealSource = get(), localUserSource = get(),  remoteDataSource = get() , caloriesRemoteDataSource = get(), localPersonalMealSource = get()) }

    single { get<MealDataBase>().getMealDao() }

    single { get<MealDataBase>().getCategoryDao() }

    single { get<MealDataBase>().getIngredientDao() }

    single { get<MealDataBase>().getAreaDao() }

    single { get<MealDataBase>().getIngredientMealDao() }

    single { get<MealDataBase>().getUserDao() }


    single<MealService> {
        rs.raf.vezbe11.modules.create(
            retrofit = createRetrofit(moshi = get() , httpClient = createOkHttpClient())
        )
    }

    single<CalorieService> {
        rs.raf.vezbe11.modules.create(
            retrofit = createRetrofit2(moshi = get(), httpClient = createOkHttpClient2())
        )
    }
    single { get<MealDataBase>().getPersonalMealDao() }
}