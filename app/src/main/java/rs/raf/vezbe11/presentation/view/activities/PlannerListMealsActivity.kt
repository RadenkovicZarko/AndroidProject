package rs.raf.vezbe11.presentation.view.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.entities.MealEntity
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.recycler.adapter.PlannerMealAdapter
import rs.raf.vezbe11.presentation.view.states.MealState
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel

class PlannerListMealsActivity: AppCompatActivity(), PlannerMealAdapter.OnItemClickListener {
    private val mainViewModel: MainContract.ViewModel by viewModel<MealViewModel>()
    ////////////////////////////
    private lateinit var adapter: PlannerMealAdapter
    private var listRV: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var mealInfo: TextView? = null
    ////////////////////////////
    private val MEAL_PLANNER_KEY = "mealPlannerKey"
    private val MEAL_METADATA_KEY = "mealMetadataKey"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planner_list_meals)
        init()
    }

    private fun init() {
        progressBar = findViewById(R.id.loading_plannerlistPb)
        initUi()
        initObservers()
    }

    private fun initUi() {
        initTextView()
        initRecycler()
    }

    private fun initTextView() {
        val message = intent.getStringExtra(MEAL_METADATA_KEY)

        mealInfo = findViewById(R.id.plannerMealInfoTV)
        mealInfo?.text = message
    }

    private fun initRecycler() {
        listRV = findViewById(R.id.plannerMealsRV)
        listRV?.layoutManager = LinearLayoutManager(this)
        adapter = PlannerMealAdapter(this)
        listRV?.adapter = adapter
    }

    private fun initObservers(){
        mainViewModel.mealState.observe(this, Observer{ it->
            renderState(it)
        })

        mainViewModel.getAllMeals()
    }

    private fun renderState(state: MealState) {
        when (state) {
            is MealState.Success -> {
                showLoadingState(false)
                adapter.submitList(state.meals)
            }
            is MealState.Error -> {
                showLoadingState(false)
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealState.DataFetched -> {
                showLoadingState(false)
            }
            is MealState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        listRV?.isVisible = !loading
        progressBar?.isVisible = loading
    }

    override fun returnChoice(meal: MealEntity) {
        var gson = Gson()
        val meal_json: String = gson.toJson(meal)

        val resultIntent = Intent()
        resultIntent.putExtra(MEAL_PLANNER_KEY, meal_json)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}