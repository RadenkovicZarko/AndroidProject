package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.databinding.FragmentListBinding
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.vezbe11.presentation.view.recycler.adapter.MealAdapter
import rs.raf.vezbe11.presentation.view.states.CategoryState
import rs.raf.vezbe11.presentation.view.states.MealState
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel
import timber.log.Timber

class ListOfMealsFragment : Fragment(R.layout.fragment_list_of_meals), MealAdapter.OnItemClickListener{
    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MealViewModel>()


    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: MealAdapter

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
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve the text from arguments
        val text = arguments?.getString("text")
        Timber.e(text)
        // Use the text as needed
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
        binding.listRv.layoutManager = LinearLayoutManager(context)
        adapter = MealAdapter(this)
        binding.listRv.adapter = adapter
    }

    private fun initListeners() {
        binding.inputEt.doAfterTextChanged {
            val filter = it.toString()
            //mainViewModel.getMoviesByName(filter)
//            mainViewModel.getAllMealsForCategoryWithFilter(filter)
        }
    }

    private fun initObservers() {
        mainViewModel.mealState.observe(viewLifecycleOwner, Observer{ it->
            renderState(it)
        })
//        // Pravimo subscription kad observablu koji je vezan za getAll iz baze
//        // Na svaku promenu tabele, obserrvable ce emitovati na onNext sve elemente
//        // koji zadovoljavaju query
//        mainViewModel.getAllMealsForCategory()
//        // Pokrecemo operaciju dovlacenja podataka sa servera, kada podaci stignu,
//        // bice sacuvani u bazi, tada ce se triggerovati observable na koji smo se pretplatili
//        // preko metode getAllMovies()
//        mainViewModel.fetchAllMeals()
    }



    private fun renderState(state: MealState) {
        when (state) {
            is MealState.Success -> {
                adapter.submitList(state.meals)
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