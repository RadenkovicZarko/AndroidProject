package rs.raf.vezbe11.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.databinding.FragmentListBinding
import rs.raf.vezbe11.databinding.FragmentPersonalMealsBinding
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.recycler.adapter.PersonalMealAdapter
import rs.raf.vezbe11.presentation.view.states.CategoryState
import rs.raf.vezbe11.presentation.view.states.PersonalMealState
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel
import timber.log.Timber

class PersonalMealsFragment:Fragment(R.layout.fragment_personal_meals) {
    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MealViewModel>()
    ////////////////////////////
    private lateinit var adapter: PersonalMealAdapter
    private var listRV: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    ////////////////////////////
    private var _binding: FragmentPersonalMealsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        mainViewModel.fetchAllMeals()
        _binding = FragmentPersonalMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        initUi(view)
        initObservers(view)
    }

    private fun initUi(view: View) {
        progressBar = view.findViewById(R.id.loading_personalPb)
        initRecycler(view)
        initListeners(view)
    }

    private fun initRecycler(view: View) {
        listRV = view.findViewById(R.id.personalListRV)
        listRV?.layoutManager = LinearLayoutManager(context)
        adapter = PersonalMealAdapter()
        listRV?.adapter = adapter
    }

    private fun initListeners(view: View) {
        // TODO
    }

    private fun initObservers(view: View){
        mainViewModel.personalMealsState.observe(viewLifecycleOwner, Observer{
            renderState(it)
        })
        var userId = mainViewModel.currentUser.value?.userName
        if(userId == null)
            userId = "None"
        mainViewModel.getAllPersonalMealsByUser(userId)
    }

    private fun renderState(state: PersonalMealState) {
        when (state) {
            is PersonalMealState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.meals)
            }
            is PersonalMealState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is PersonalMealState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is PersonalMealState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        listRV?.isVisible = !loading
        progressBar?.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}