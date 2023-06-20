package rs.raf.vezbe11.modules

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rs.raf.vezbe11.data.datasources.local.MealDataBase
import rs.raf.vezbe11.data.datasources.remote.MealService
import rs.raf.vezbe11.data.repositories.MealRepository
import rs.raf.vezbe11.data.repositories.MealRepositoryImpl
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel


val mealModule = module {
    viewModel { MealViewModel(mealRepository = get()) }

    single<MealRepository> { MealRepositoryImpl(localMealSource = get(),localCategorySource = get(), localIngredientSource = get(),
        localAreaSource = get(), localIngredientMealSource = get(), remoteDataSource = get()) }

    single { get<MealDataBase>().getMealDao() }

    single { get<MealDataBase>().getCategoryDao() }

    single { get<MealDataBase>().getIngredientDao() }

    single { get<MealDataBase>().getAreaDao() }

    single { get<MealDataBase>().getIngredientMealDao() }

    single<MealService> {
        rs.raf.vezbe11.modules.create(
            retrofit = get()
        )
    }
}