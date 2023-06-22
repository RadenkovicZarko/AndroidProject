package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel

class CategoryFragment: Fragment(R.layout.fragment_category) {
    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MealViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel.fetchAllCategories()
        mainViewModel.fetchAllAreas()
        mainViewModel.fetchAllIngredients()
        mainViewModel.fetchAllMeals()

        val view = inflater.inflate(R.layout.fragment_category, container, false)
        return view
    }
}