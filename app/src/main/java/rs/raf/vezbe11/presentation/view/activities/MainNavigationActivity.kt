package rs.raf.vezbe11.presentation.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import rs.raf.vezbe11.R
import rs.raf.vezbe11.presentation.view.adapters.PagerAdapter

class MainNavigationActivity: AppCompatActivity()  {
    var viewPager: ViewPager? = null
    var bottomNavigationView: BottomNavigationView? = null
    val LOGIN_KEY = "loginKey"


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
//        val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
//        val message = sharedPreferences.getString(LOGIN_KEY,null)
//
//        val gson = Gson()
//        val user = gson.fromJson(message, UserEntity::class.java)
//
//        Toast.makeText(this, "Welcome ${user.userName}", Toast.LENGTH_SHORT).show()
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