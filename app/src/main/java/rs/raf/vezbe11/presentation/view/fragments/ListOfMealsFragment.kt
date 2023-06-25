package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.databinding.FragmentListOfMealsBinding
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.recycler.adapter.MealAdapter
import rs.raf.vezbe11.presentation.view.states.MealState
import rs.raf.vezbe11.presentation.view.states.NumOfMealsState
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel
import timber.log.Timber

class ListOfMealsFragment : Fragment(R.layout.fragment_list_of_meals), MealAdapter.OnItemClickListener{
    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MealViewModel>()


    private var _binding: FragmentListOfMealsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MealAdapter
    var text = ""
    private var isLoading = false
    private var currentPage = 0
    private var totalPages = 0

    var nameOfMeal :String?= null
    var nameOfIngredient :String?= null
    var lCalories : Double?= null
    var hCalories : Double?= null
    var sort : Int? = null

    companion object {
        fun newInstance(text: String): ListOfMealsFragment {
            val fragment = ListOfMealsFragment()
            val args = Bundle()
            args.putString("text", text)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        mainViewModel.fetchAllMeals()
        _binding = FragmentListOfMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        var prom = ""
        if(arguments?.getString("text") != null) {
            prom = arguments?.getString("text")!!
        }
        text = prom
        init()
    }



    private fun init() {
        initUi()
        initObservers()
    }

    private fun initUi() {
        initRecycler()
        initListeners()

    }


    private fun initRecycler() {
        binding.listRvLoM.layoutManager = LinearLayoutManager(context)
        adapter = MealAdapter(this)
        binding.listRvLoM.adapter = adapter
    }

    private fun initListeners() {
        binding.inputEtLoMMeals.doAfterTextChanged {
            nameOfMeal = it.toString()
            if(nameOfMeal == "")
                nameOfMeal=null
            if(binding.cbBetween.isSelected) {
                mainViewModel.getFilteredAndSortedMealsNormal(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort, 0)
                mainViewModel.getCountFilteredAndSortedMealsNormal(  nameOfMeal, nameOfIngredient, lCalories, hCalories, sort)
            }
            else {
                mainViewModel.getFilteredAndSortedMealsBetween(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort, 0 )
                mainViewModel.getCountFilteredAndSortedMealsBetween(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort )
            }
            currentPage = 0

        }
        binding.inputEtLoMIngredients.doAfterTextChanged {
            val filter = it.toString()
            nameOfIngredient = it.toString()
            if(nameOfIngredient == "")
                nameOfIngredient=null
            if(binding.cbBetween.isSelected) {
                mainViewModel.getFilteredAndSortedMealsNormal(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort, 0)
                mainViewModel.getCountFilteredAndSortedMealsNormal(  nameOfMeal, nameOfIngredient, lCalories, hCalories, sort)
            }
            else {
                mainViewModel.getFilteredAndSortedMealsBetween(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort, 0 )
                mainViewModel.getCountFilteredAndSortedMealsBetween(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort )
            }
            currentPage = 0
        }
        binding.nextButton.setOnClickListener{
            loadNextPage()

        }
        binding.previousButton.setOnClickListener{
            loadPreviousPage()
        }
        binding.filterBtn.setOnClickListener{
            if(binding.etLess.text.toString()!="")
            {
                lCalories = binding.etLess.text.toString().toDoubleOrNull()
            }
            else
            {
                lCalories = null
            }
            if(binding.etHigher.text.toString()!="")
            {
                hCalories = binding.etHigher.text.toString().toDoubleOrNull()
            }
            else
            {
                hCalories = null
            }
            if(binding.cbBetween.isSelected) {
                mainViewModel.getFilteredAndSortedMealsNormal(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort, 0)
                mainViewModel.getCountFilteredAndSortedMealsNormal(  nameOfMeal, nameOfIngredient, lCalories, hCalories, sort)
            }
            else {
                mainViewModel.getFilteredAndSortedMealsBetween(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort, 0 )
                mainViewModel.getCountFilteredAndSortedMealsBetween(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort )
            }

        }
        binding.ascBtn.setOnClickListener {
            if(binding.cbBetween.isSelected) {
                mainViewModel.getFilteredAndSortedMealsNormal(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort, 0)
                mainViewModel.getCountFilteredAndSortedMealsNormal(  nameOfMeal, nameOfIngredient, lCalories, hCalories, sort)
            }
            else {
                mainViewModel.getFilteredAndSortedMealsBetween(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort, 0 )
                mainViewModel.getCountFilteredAndSortedMealsBetween(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort )
            }
        }
        binding.dscBtn.setOnClickListener{
            if(binding.cbBetween.isSelected) {
                mainViewModel.getFilteredAndSortedMealsNormal(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort, 0)
                mainViewModel.getCountFilteredAndSortedMealsNormal(  nameOfMeal, nameOfIngredient, lCalories, hCalories, sort)
            }
            else {
                mainViewModel.getFilteredAndSortedMealsBetween(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort, 0 )
                mainViewModel.getCountFilteredAndSortedMealsBetween(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort )
            }
        }
    }

    private fun loadPreviousPage() {
        if (currentPage > 0) {
            isLoading = true

            val previousPage = currentPage - 1
            val startIndex = previousPage * 10

            // Implement your logic to load the previous page of data (e.g., from an API)
            if(binding.cbBetween.isSelected) {
                mainViewModel.getFilteredAndSortedMealsNormal(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort, startIndex)
                mainViewModel.getCountFilteredAndSortedMealsNormal(  nameOfMeal, nameOfIngredient, lCalories, hCalories, sort)
            }
            else {
                mainViewModel.getFilteredAndSortedMealsBetween(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort, startIndex )
                mainViewModel.getCountFilteredAndSortedMealsBetween(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort )
            }

            isLoading = false
            currentPage = previousPage

            updateButtonStates()
        }
    }

    private fun loadNextPage() {
        isLoading = true

        val nextPage = currentPage + 1
        val startIndex = nextPage * 10

        // Implement your logic to load the next page of data (e.g., from an API)

        if(binding.cbBetween.isSelected) {
            mainViewModel.getFilteredAndSortedMealsNormal(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort, startIndex)
            mainViewModel.getCountFilteredAndSortedMealsNormal(  nameOfMeal, nameOfIngredient, lCalories, hCalories, sort)
        }
        else {
            mainViewModel.getFilteredAndSortedMealsBetween(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort, startIndex )
            mainViewModel.getCountFilteredAndSortedMealsBetween(nameOfMeal, nameOfIngredient, lCalories, hCalories, sort )
        }
        isLoading = false
        currentPage = nextPage

        updateButtonStates()
    }

    private fun updateButtonStates() {
        binding.previousButton.isEnabled = currentPage > 0
        binding.nextButton.isEnabled = currentPage < totalPages
    }



    private fun initObservers() {
        mainViewModel.mealState.observe(viewLifecycleOwner, Observer{ it->
            renderState(it)
        })

        mainViewModel.getMealsInRange(0)

        mainViewModel.numOfMealsState.observe(viewLifecycleOwner, Observer{ it->
            renderNumOfMealsState(it)
        })
        mainViewModel.getNumOfMeals(text)
    }


    private fun renderNumOfMealsState(state: NumOfMealsState) {
        when (state) {
            is NumOfMealsState.Success -> {
                totalPages = state.numOfMeals/10
                Timber.e(totalPages.toString())

            }
            is NumOfMealsState.Error -> {
                Timber.e("Error")
            }
            is NumOfMealsState.DataFetched -> {
                Timber.e("DataFetched")
            }
            is NumOfMealsState.Loading -> {
                Timber.e("Loading")
            }
        }
    }




    private fun renderState(state: MealState) {
        when (state) {
            is MealState.Success -> {
                Timber.e("DESILO SEE!@#")
                adapter.submitList(state.meals)
                Timber.e(state.meals.size.toString())
            }
            is MealState.Error -> {
                Timber.e("Error")
            }
            is MealState.DataFetched -> {
                Timber.e("DataFetched")
            }
            is MealState.Loading -> {
                Timber.e("Loading")
            }
        }
    }


    override fun onItemClick(text: String) {
        TODO("Not yet implemented")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}