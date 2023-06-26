package rs.raf.vezbe11.presentation.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import rs.raf.vezbe11.R
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.data.models.entities.MealEntity
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.activities.PlannerListMealsActivity
import rs.raf.vezbe11.presentation.view.recycler.adapter.PlannerAdapter
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel

// INSERT INTO users VALUES ("Vanjce","123",21,210,150,0,10)
class PersonalPlanFragment: Fragment(R.layout.fragment_personal_plan), PlannerAdapter.OnItemClickListener{
    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MealViewModel>()
    ////////////////////////////
    private lateinit var adapter: PlannerAdapter
    private var listRV: RecyclerView? = null
    private var sendBtn: Button? = null
    private var emailET: EditText? = null
    ////////////////////////////
    private val SECOND_ACTIVITY_REQUEST_CODE = 0
    private val MEAL_PLANNER_KEY = "mealPlannerKey"
    private val MEAL_METADATA_KEY = "mealMetadataKey"
    private var current_day_type = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        initUi(view)
        initRecycler(view)
        initObservers(view)
    }

    private fun initUi(view: View) {
        listRV = view.findViewById(R.id.planerRecyclerView)
        sendBtn = view.findViewById(R.id.sendButton)
        emailET = view.findViewById(R.id.editemailText)
    }

    private fun initRecycler(view: View) {
        listRV?.layoutManager = GridLayoutManager(view.context, 4)
        adapter = PlannerAdapter(this)
        listRV?.adapter = adapter
    }

    private fun initObservers(view: View) {
        mainViewModel.plannerList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.submitList(it)
        })

        mainViewModel.loadPlannerList()
    }

    override fun chooseMeal(position: Int) {
        val meal = mainViewModel.plannerList.value?.get(position)
        current_day_type = position
        val intent = Intent(context, PlannerListMealsActivity::class.java)
        val str = "${meal?.day} - ${meal?.typeOfMeal}"
        intent.putExtra(MEAL_METADATA_KEY,str)
        startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Handle the result
                val returnedData = data?.getStringExtra(MEAL_PLANNER_KEY)
                var gson = Gson()
                var meal = gson.fromJson(returnedData, MealEntity::class.java)
                mainViewModel.plannerList.value?.get(current_day_type)?.meal = meal
                adapter.notifyItemChanged(current_day_type)
                // Do something with the returned data
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // Handle the cancellation or other result codes
            }
        }
    }
}