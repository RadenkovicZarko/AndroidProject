package rs.raf.vezbe11.presentation.view.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import org.koin.androidx.viewmodel.compat.SharedViewModelCompat.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.entities.UserEntity
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.adapters.PagerAdapter
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel

class MainNavigationActivity: AppCompatActivity()  {
    var viewPager: ViewPager? = null
    var bottomNavigationView: BottomNavigationView? = null
    val LOGIN_KEY = "loginKey"
    private val mainViewModel: MainContract.ViewModel by viewModel<MealViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation)
        init()
    }

    private fun init() {
        initViewPager()
        initNavigation()
        loadCurrentUser()
    }

    private fun loadCurrentUser() {
        val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
        val message = sharedPreferences.getString(LOGIN_KEY,null)

        val gson = Gson()
        val user = gson.fromJson(message, UserEntity::class.java)
        mainViewModel.setCurrentUser(user)
        Toast.makeText(this, "Welcome ${user.userName}", Toast.LENGTH_SHORT).show()
    }

    private fun initViewPager() {
        viewPager = findViewById(R.id.viewPagerNavigation)
        viewPager?.adapter = PagerAdapter(supportFragmentManager)

    }

    private fun initNavigation() {
        bottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView?.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_1 -> {
                    viewPager?.currentItem = 0
                }
                R.id.navigation_2 -> {
                    viewPager?.currentItem = 1
                }
                R.id.navigation_3 -> {
                    viewPager?.currentItem = 2
                }
            }
            true
        }
    }



}