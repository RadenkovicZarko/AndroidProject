package rs.raf.vezbe11.presentation.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.fragments.ListOfMealsFragment
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel

class ListOfMealsActivity : AppCompatActivity()  {


    val LOGIN_KEY = "loginKey"
    private val mainViewModel: MainContract.ViewModel by viewModel<MealViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meals)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        var str = intent.getStringExtra("category")!!
        val fragment = ListOfMealsFragment.newInstance(str) // Instantiate your fragment class

        fragmentTransaction.add(R.id.fragmentContainerMeals, fragment)
        fragmentTransaction.commit()
    }
}