package rs.raf.vezbe11.presentation.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.Resource
import rs.raf.vezbe11.databinding.FragmentCategoryBinding
import rs.raf.vezbe11.databinding.FragmentFilterBinding
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.activities.DetailsOfMealActivity
import rs.raf.vezbe11.presentation.view.activities.ListOfMealsActivity
import rs.raf.vezbe11.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.vezbe11.presentation.view.recycler.adapter.FilterMealAdapter
import rs.raf.vezbe11.presentation.view.recycler.adapter.MealAdapter
import rs.raf.vezbe11.presentation.view.states.CountOfFilterMealState
import rs.raf.vezbe11.presentation.view.states.FilterMealState
import rs.raf.vezbe11.presentation.view.states.MealState
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel
import timber.log.Timber

class FilterFragment: Fragment(R.layout.fragment_filter), FilterMealAdapter.OnItemClickListener {

    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MealViewModel>()


    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FilterMealAdapter
    private var isLoading = false
    private var currentPage = 0
    private var totalPages = 0
    var category : String? = null
    var area : String? = null
    var ingredients : String? = null
    var selected : Int = 0

    var sort: Int? = null
    var nameOfMeal: String? = null
    var nameOfTag: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
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

    private fun initObservers() {
        mainViewModel.filterMealState.observe(viewLifecycleOwner, Observer{ it->
            renderState(it)
        })

        mainViewModel.countOfFilterMealState.observe(viewLifecycleOwner, Observer{ it->
            renderCountOfFilterMealState(it)
        })


        mainViewModel.getFilteredMeals(category,ingredients,area,nameOfMeal, nameOfTag,sort,0)
        mainViewModel.getCountOfFilteredMeals(category,ingredients,area,nameOfTag,nameOfMeal,sort)
    }

    private fun renderState(state: FilterMealState) {
        when (state) {
            is FilterMealState.Success -> {
                Timber.e("DESILO SEE!@#")
                adapter.submitList(state.meals)
                Timber.e(state.meals.size.toString())
            }
            is FilterMealState.Error -> {
                Timber.e("Error")
            }
            is FilterMealState.DataFetched -> {
                Timber.e("DataFetched")
            }
            is FilterMealState.Loading -> {
                Timber.e("Loading")
            }
        }
    }

    private fun renderCountOfFilterMealState(state: CountOfFilterMealState) {
        when (state) {
            is CountOfFilterMealState.Success -> {
                totalPages = state.numOfMeals/10
                updateButtonStates()
            }
            is CountOfFilterMealState.Error -> {
                Timber.e("Error")
            }
            is CountOfFilterMealState.DataFetched -> {
                Timber.e("DataFetched")
            }
            is CountOfFilterMealState.Loading -> {
                Timber.e("Loading")
            }
        }
    }


    private fun initRecycler() {
        binding.mealsRv.layoutManager = LinearLayoutManager(context)
        adapter = FilterMealAdapter(this)
        binding.mealsRv.adapter = adapter
    }

    private fun initListeners()
    {
        binding.categoryBtn.setOnClickListener {
            area = null
            ingredients = null
            selected = 1
            binding.areaBtn.setAlpha(0.3f)
            binding.categoryBtn.setAlpha(1f);
            binding.ingridientBtn.setAlpha(0.3f);
        }

        binding.areaBtn.setOnClickListener {
            category = null
            ingredients = null
            selected = 2
            binding.areaBtn.setAlpha(1f)
            binding.categoryBtn.setAlpha(0.3f);
            binding.ingridientBtn.setAlpha(0.3f);
        }

        binding.ingridientBtn.setOnClickListener {
            category = null
            area = null
            selected = 3
            binding.areaBtn.setAlpha(0.3f)
            binding.categoryBtn.setAlpha(0.3f);
            binding.ingridientBtn.setAlpha(1f);
        }

        binding.parameterEt.doAfterTextChanged{
            if(selected == 0)
            {
                val dialog = MyDialogFragment.newInstance("You must selected one of parameters")
                dialog.show(childFragmentManager, "Error")
            }
            else
            {
                if(selected==1)
                    category = it.toString()
                else if(selected==2)
                    area = it.toString()
                else
                    ingredients = it.toString()

                if(binding.sort.isChecked) {
                    mainViewModel.getFilteredMeals(category, ingredients, area, nameOfTag, nameOfMeal, sort, 0)
                    mainViewModel.getCountOfFilteredMeals(category, ingredients, area, nameOfTag, nameOfMeal, 0)
                }
                else
                {
                    mainViewModel.getFilteredMeals(category, ingredients, area, nameOfTag, nameOfMeal, sort, 0)
                    mainViewModel.getCountOfFilteredMeals(category, ingredients, area, nameOfTag, nameOfMeal, null)
                }
                currentPage = 0
            }
        }
        binding.mealEt.doAfterTextChanged {
            if(selected == 0)
            {
                val dialog = MyDialogFragment.newInstance("You must selected one of parameters")
                dialog.show(childFragmentManager, "Error")
            }
            else
            {
                nameOfMeal = it.toString()
                if(binding.sort.isChecked) {
                    mainViewModel.getFilteredMeals(category, ingredients, area, nameOfTag, nameOfMeal, sort, 0)
                    mainViewModel.getCountOfFilteredMeals(category, ingredients, area, nameOfTag, nameOfMeal, 0)
                }
                else
                {
                    mainViewModel.getFilteredMeals(category, ingredients, area, nameOfTag, nameOfMeal, sort, 0)
                    mainViewModel.getCountOfFilteredMeals(category, ingredients, area, nameOfTag, nameOfMeal, null)
                }
                currentPage = 0
            }
        }

        binding.tagEt.doAfterTextChanged {
            if(selected == 0)
            {
                val dialog = MyDialogFragment.newInstance("You must selected one of parameters")
                dialog.show(childFragmentManager, "Error")
            }
            else
            {
                nameOfTag = it.toString()
                if(binding.sort.isChecked) {
                    mainViewModel.getFilteredMeals(category, ingredients, area, nameOfTag, nameOfMeal, sort, 0)
                    mainViewModel.getCountOfFilteredMeals(category, ingredients, area, nameOfTag, nameOfMeal, 0)
                }
                else
                {
                    mainViewModel.getFilteredMeals(category, ingredients, area, nameOfTag, nameOfMeal, sort, 0)
                    mainViewModel.getCountOfFilteredMeals(category, ingredients, area, nameOfTag, nameOfMeal, null)
                }
                currentPage = 0
            }
        }

        binding.sort.setOnClickListener {
            if(selected == 0)
            {
                val dialog = MyDialogFragment.newInstance("You must selected one of parameters")
                dialog.show(childFragmentManager, "Error")
                binding.parameterEt.setText("")
            }
            else
            {
                if(binding.sort.isChecked) {
                    mainViewModel.getFilteredMeals(category, ingredients, area, nameOfTag, nameOfMeal, sort, 0)
                    mainViewModel.getCountOfFilteredMeals(category, ingredients, area, nameOfTag, nameOfMeal, 0)
                }
                else
                {
                    mainViewModel.getFilteredMeals(category, ingredients, area, nameOfTag, nameOfMeal, sort, 0)
                    mainViewModel.getCountOfFilteredMeals(category, ingredients, area, nameOfTag, nameOfMeal, null)
                }
                currentPage = 0
            }
        }
        binding.nextButtonFm.setOnClickListener{
            loadNextPage()

        }
        binding.previousButtonFm.setOnClickListener{
            loadPreviousPage()
        }
    }

    override fun onItemClick(text: String) {
        val intent = Intent(requireContext() , DetailsOfMealActivity::class.java)
        intent.putExtra("category", text)
        startActivity(intent)
    }


    private fun loadPreviousPage() {
        if (currentPage > 0) {
            isLoading = true

            val previousPage = currentPage - 1
            val startIndex = previousPage * 10


            mainViewModel.getFilteredMeals(category,ingredients,area,nameOfMeal, nameOfTag,sort,startIndex)

            isLoading = false
            currentPage = previousPage

            updateButtonStates()
        }
    }

    private fun loadNextPage() {
        isLoading = true

        val nextPage = currentPage + 1
        val startIndex = nextPage * 10

        mainViewModel.getFilteredMeals(category,ingredients,area,nameOfMeal, nameOfTag,sort,startIndex)

        isLoading = false
        currentPage = nextPage

        updateButtonStates()
    }


    private fun updateButtonStates() {
        binding.previousButtonFm.isEnabled = currentPage > 0
        binding.nextButtonFm.isEnabled = currentPage < totalPages -1
    }



}