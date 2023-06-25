package rs.raf.vezbe11.presentation.view.fragments

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.databinding.FragmentPersonalMealsBinding
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.activities.SavePersonalMealActivity
import rs.raf.vezbe11.presentation.view.recycler.adapter.PersonalMealAdapter
import rs.raf.vezbe11.presentation.view.states.PersonalMealState
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel

class PersonalMealsFragment:Fragment(R.layout.fragment_personal_meals), PersonalMealAdapter.OnItemClickListener {
    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MealViewModel>()
    ////////////////////////////
    private lateinit var adapter: PersonalMealAdapter
    private var listRV: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    ////////////////////////////
    private var _binding: FragmentPersonalMealsBinding? = null
    private val binding get() = _binding!!
    val CURRENT_MEAL_KEY = "currentMealKey"
    val EDIT = "edit"


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
    }

    private fun initRecycler(view: View) {
        listRV = view.findViewById(R.id.personalListRV)
        listRV?.layoutManager = LinearLayoutManager(context)
        adapter = PersonalMealAdapter(this)
        listRV?.adapter = adapter
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
            }
            is PersonalMealState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    override fun onDeleteClick(position: Int) {
        var mealDelete = adapter.currentList[position]
        val alertDialogBuilder = AlertDialog.Builder(context)

        val snackbar = view?.let {
            Snackbar.make(
                it,
                "Are you sure you want to delete ${mealDelete.strName}?",
                Snackbar.LENGTH_LONG
            )
        }

        snackbar?.setAction("Delete") {
            mainViewModel.deletePersonalMeal(mealDelete)
        }
        snackbar?.setActionTextColor(Color.parseColor("#d3290f"))
        snackbar?.show()
    }

    override fun onEditClick(position: Int) {
        val sharedPreferences = activity?.getSharedPreferences(activity?.packageName, AppCompatActivity.MODE_PRIVATE)
        val gson = Gson()
        val json: String = gson.toJson(mainViewModel.currentPersonalMealSave.value)

        val editor = sharedPreferences?.edit()
        editor?.putString(CURRENT_MEAL_KEY,json)
        editor?.apply()

        val intent = Intent(requireContext(), SavePersonalMealActivity::class.java)
        val anotherJson: String = gson.toJson(adapter.currentList[position])
        intent.putExtra(EDIT, anotherJson)
        startActivity(intent)
    }

    private fun showLoadingState(loading: Boolean) {
        listRV?.isVisible = !loading
        progressBar?.isVisible = loading
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        init(view!!)
    }

}