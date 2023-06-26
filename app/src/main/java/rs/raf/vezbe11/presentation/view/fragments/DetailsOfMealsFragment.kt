package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import okhttp3.internal.notifyAll
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.entities.MealEntity
import rs.raf.vezbe11.databinding.FragmentDetailsOfMealsBinding
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.vezbe11.presentation.view.states.IngredientsForMealState
import rs.raf.vezbe11.presentation.view.states.MealDetailsState
import rs.raf.vezbe11.presentation.view.states.NumOfMealsState
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel
import timber.log.Timber

class DetailsOfMealsFragment(idMeal:String) : Fragment(R.layout.fragment_details_of_meals){

    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MealViewModel>()


    private var _binding: FragmentDetailsOfMealsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CategoryAdapter
    lateinit var editText : EditText
    lateinit var meal: MealEntity
    var mealId = idMeal
    var listOfIngredients = listOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsOfMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.mealDetailsState.observe(viewLifecycleOwner, Observer{ it->
            renderMealDetailsState(it)
        })
        mainViewModel.ingredientsForMealState.observe(viewLifecycleOwner, Observer{ it->
            renderIngredientsForMealState(it)
        })
        mainViewModel.getMealById(mealId)
        mainViewModel.getIngredientsForMeal(mealId)

    }

    private fun init() {
        Picasso.get().load(meal.strMealThumb).into(binding.mealImgIv)
        binding.nameTv.text = meal.strMeal
        binding.categoryTv.text = meal.strCategory
        binding.areaTv.text = meal.strArea
        binding.instructionsTv.text = meal.strInstructions
        if(listOfIngredients.isEmpty())
            binding.ingredientsTv.text = "Not available"
        else
            binding.ingredientsTv.text = listOfIngredients.toString()
        binding.linkTv.text = meal.strYoutube
        if(meal.strTags == null || meal.strTags == "" || meal.strTags == "[]")
            binding.tagsTv.text = "Not available"
        else
            binding.tagsTv.text = meal.strTags
        initListeners()
    }

    private fun initListeners() {
        binding.saveBtn.setOnClickListener {
            TODO()
            //OVE VANJCE TREBA DA SE ODRADI POKRETANJE TVOG NEKAKO...
        }
    }

    private fun renderMealDetailsState(state: MealDetailsState) {
        when (state) {
            is MealDetailsState.Success -> {
                meal = state.meal
                init()
            }
            is MealDetailsState.Error -> {
                Timber.e("Error")
            }
            is MealDetailsState.DataFetched -> {
                Timber.e("DataFetched")
            }
            is MealDetailsState.Loading -> {
                Timber.e("Loading")
            }
        }
    }

    private fun renderIngredientsForMealState(state: IngredientsForMealState) {
        when (state) {
            is IngredientsForMealState.Success -> {
                listOfIngredients = state.ingredientsForMeal
            }
            is IngredientsForMealState.Error -> {
                Timber.e("Error")
            }
            is IngredientsForMealState.DataFetched -> {
                Timber.e("DataFetched")
            }
            is IngredientsForMealState.Loading -> {
                Timber.e("Loading")
            }
        }
    }



}