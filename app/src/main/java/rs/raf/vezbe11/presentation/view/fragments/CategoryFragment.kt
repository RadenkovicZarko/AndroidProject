package rs.raf.vezbe11.presentation.view.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.databinding.FragmentCategoryBinding
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.activities.ListOfMealsActivity
import rs.raf.vezbe11.presentation.view.adapters.PagerAdapter
import rs.raf.vezbe11.presentation.view.recycler.adapter.CategoryAdapter
import rs.raf.vezbe11.presentation.view.states.CategoryState
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel
import timber.log.Timber


class CategoryFragment(mainAdapter: PagerAdapter): Fragment(), CategoryAdapter.OnItemClickListener {
    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MealViewModel>()


    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CategoryAdapter
    lateinit var editText : EditText
    var mainAdapter = mainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        mainViewModel.fetchAllMeals()
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
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

    private fun initRecycler() {
        binding.listRvCat.layoutManager = LinearLayoutManager(context)
        adapter = CategoryAdapter(this)
        binding.listRvCat.adapter = adapter
    }

    private fun initListeners() {
        binding.inputEtCat.doAfterTextChanged {
            val filter = it.toString()
            //mainViewModel.getMoviesByName(filter)
            mainViewModel.getCaloriesByNameOfIngredientOrMeal(filter)
        }
    }


    private fun initObservers() {
        mainViewModel.categoryState.observe(viewLifecycleOwner, Observer{ it->
            renderState(it)
        })
//        // Pravimo subscription kad observablu koji je vezan za getAll iz baze
//        // Na svaku promenu tabele, obserrvable ce emitovati na onNext sve elemente
//        // koji zadovoljavaju query
        mainViewModel.getAllCategories()
//        // Pokrecemo operaciju dovlacenja podataka sa servera, kada podaci stignu,
//        // bice sacuvani u bazi, tada ce se triggerovati observable na koji smo se pretplatili
//        // preko metode getAllMovies()
//        mainViewModel.fetchAllCategories()
//        mainViewModel.fetchAllIngredients()
//        mainViewModel.fetchAllMeals()

    }

    private fun renderState(state: CategoryState) {
        when (state) {
            is CategoryState.Success -> {
                adapter.submitList(state.categories)
            }
            is CategoryState.Error -> {
                Timber.e("Error")
            }
            is CategoryState.DataFetched -> {
                Timber.e("DataFetched")
            }
            is CategoryState.Loading -> {
                Timber.e("Loading")
            }
        }
    }


    override fun onImageClick(position: Int, text: String) {
        val dialog = MyDialogFragment.newInstance(text)
        dialog.show(childFragmentManager, "YourDialogFragment")
    }

    override fun onItemClick(text: String) {

        val intent = Intent(requireContext() , ListOfMealsActivity::class.java)
        intent.putExtra("category", text)
        startActivity(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}