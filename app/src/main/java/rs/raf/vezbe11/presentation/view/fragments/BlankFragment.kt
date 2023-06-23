package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel
import timber.log.Timber

class BlankFragment : Fragment() {
    private lateinit var textView: EditText
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

//        mainViewModel.fetchAllCalories()
//        mainViewModel.getCAndMRelations()
        val view = inflater.inflate(R.layout.fragment_blank, container, false)
        textView = view.findViewById(R.id.editTextTextPersonName)
        return view
    }


}