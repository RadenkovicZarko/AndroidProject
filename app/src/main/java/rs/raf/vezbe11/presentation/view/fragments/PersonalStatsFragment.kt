package rs.raf.vezbe11.presentation.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.entities.PersonalMealEntity
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import rs.raf.vezbe11.presentation.view.activities.LogInActivity
import rs.raf.vezbe11.presentation.view.activities.MainNavigationActivity
import timber.log.Timber
import java.time.LocalDate
import kotlin.collections.ArrayList

class PersonalStatsFragment: Fragment(R.layout.fragment_personal_stats) {
    private val mainViewModel: MainContract.ViewModel by sharedViewModel<MealViewModel>()
    /////////////////////////////
    var barChart: BarChart? = null
    var avgCaloriesTV: TextView? = null
    var countBtn: Button? = null
    var kcalBtn: Button? = null
    var logoutBtn: Button? = null
    /////////////////////////////
    private var lstOfDays = mutableListOf<DayCaloriesMeals>()
    private var isCount = true
    private var recKcal = 0.0
    val LOGIN_KEY = "loginKey"


    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view : View) {
        calcRecommended()
        loadData()
        initUi(view)
        initChart()
        initListeners()
    }

    private fun initUi(view: View) {
        barChart = view.findViewById(R.id.barChart)
        avgCaloriesTV = view.findViewById(R.id.statsTV)
        avgCaloriesTV?.text = "Recommended kcal intake: ${recKcal}"
        countBtn = view.findViewById(R.id.show_countBtn)
        kcalBtn = view.findViewById(R.id.show_kcalBtn)
        logoutBtn = view.findViewById(R.id.logoutBtn)
    }

    private fun initChart(){
        var entries = ArrayList<BarEntry>()
        var labels = ArrayList<String>()
        barChart?.description?.isEnabled = false
        barChart?.setBackgroundColor(resources.getColor(R.color.eva_black))
        barChart?.setGridBackgroundColor(resources.getColor(R.color.eva_green))
        barChart?.setNoDataTextColor(resources.getColor(R.color.eva_green))
        barChart?.setBorderColor(resources.getColor(R.color.eva_green))
        barChart?.setDrawBorders(true)

        val colors = mutableListOf<Int>()
        for (i in 0..6) {
            var y = get_y_axis(i)
            var barEntry = BarEntry(i.toFloat(), y)
            entries.add(barEntry)
            labels.add("Day ${i+1}")
            var color = if (lstOfDays.get(i).calories > recKcal) resources.getColor(R.color.eva_purple) else resources.getColor(R.color.eva_green)
            colors.add(color)
        }
        val barDataSet = com.github.mikephil.charting.data.BarDataSet(entries, null)
        barDataSet.valueTextColor = resources.getColor(R.color.eva_green)
        barDataSet.colors = colors
        barDataSet.stackLabels = labels.toTypedArray()
        val data = com.github.mikephil.charting.data.BarData(barDataSet)
        data.setValueTextSize(22f)
        barChart?.data = data
        barChart?.animateY(2000)
        barChart?.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {
            }

            override fun onValueSelected(e: Entry?, h: Highlight?) {
                var day = lstOfDays[e?.x?.toInt()?:0]
                Toast.makeText(context, "Day: ${day.day}", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun initListeners() {
        countBtn?.setOnClickListener {
            if(!isCount){
                isCount = true
                initChart()
            }
        }
        kcalBtn?.setOnClickListener {
            if(isCount){
                isCount = false
                initChart()
            }
        }

        logoutBtn?.setOnClickListener {
            val sharedPreferences = activity?.getSharedPreferences(activity?.packageName, AppCompatActivity.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.putString(LOGIN_KEY,null)
            editor?.apply()
            val intent = Intent(activity, LogInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun get_y_axis(i: Int): Float {
        return if(isCount)
            lstOfDays[i].meals.size.toFloat()
        else
            lstOfDays[i].calories.toFloat()
    }

    private fun loadData() {
        val userId = mainViewModel.currentUser.value?.userName?: ""
        lstOfDays = mutableListOf()

        for(i in 0..6){
            val meals = calcDayAndMeal(i, userId)
            if(meals.isEmpty()){
                lstOfDays.add(DayCaloriesMeals(calcDay(i), 0.0, emptyList()))
                Timber.e("NEMA NISTA ZA DAN $i")
            }
            else{
                var kcal = 0.0
                for(m in meals)
                    kcal += m.calories?:0.0
                Timber.e("IMA NESTO ZA DAN $i - $kcal")
                lstOfDays.add(DayCaloriesMeals(meals[0].date?:return, kcal, meals))
            }
        }

    }

    private fun calcDay(day: Int): java.sql.Date {
        val num = 6 - day
        val date = LocalDate.now().minusDays(num.toLong())
        return toSqlDate(date)
    }

    private fun calcDayAndMeal(day: Int, userId: String): ArrayList<PersonalMealEntity> {
        return mainViewModel.getPersonalMealsBetweenDates(calcDay(day).time, calcDay(day).time, userId) as ArrayList<PersonalMealEntity>
    }

    fun toSqlDate(date: LocalDate): java.sql.Date {
        return java.sql.Date(date.year - 1900, date.monthValue - 1, date.dayOfMonth)
    }

    private fun calcRecommended(){
        var user = mainViewModel.currentUser.value
        var act: Double = ((user?.activityLevel)?.times(1.0))?:1.65
        var weig = user?.weight ?:70
        recKcal = weig.toDouble() * calc_years() * act
    }

    private fun calc_years(): Double{
        var user = mainViewModel.currentUser.value
        if(user?.sex == 1){
            return when(user?.years){
                in 0..20 -> 0.95
                in 21..28 -> 0.90
                else -> 0.85
            }
        }else{
            return when(user?.years){
                in 0..18 -> 1.0
                in 19..28 -> 0.95
                in 29..38 -> 0.90
                else -> 0.85
            }
        }
    }

    data class DayCaloriesMeals(
            val day: java.sql.Date,
            val calories: Double,
            val meals: List<PersonalMealEntity>
            )


}