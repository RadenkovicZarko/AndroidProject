package rs.raf.vezbe11.presentation.view.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.compat.SharedViewModelCompat.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.data.models.PlannerItem
import rs.raf.vezbe11.data.models.Resource
import rs.raf.vezbe11.data.models.entities.UserEntity
import rs.raf.vezbe11.databinding.FragmentCategoryBinding
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.adapters.PagerAdapter
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel
import timber.log.Timber

class MainNavigationActivity : AppCompatActivity() {
    var viewPager: ViewPager? = null
    var bottomNavigationView: BottomNavigationView? = null
    val LOGIN_KEY = "loginKey"
    private val mainViewModel: MainContract.ViewModel by viewModel<MealViewModel>()
    var loadingPbCat: ProgressBar? = null
    var shouldShowLoad = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navigation)
        init()
        if (intent?.action == Intent.ACTION_VIEW) {
            val deepLinkUri: Uri? = intent.data
            if (deepLinkUri != null) {
                // Handle the deep link URL and extract any parameters
                val listParam: String? = deepLinkUri.getQueryParameter("list")

                Timber.e(listParam)
                // Parse the listParam to retrieve the PlannerItems
//                val plannerItems: List<PlannerItem> = parseListParam(listParam)

                // Load the PlannerItems into your app's list or perform any other desired action
//                loadPlannerItems(plannerItems)
            }
        }
    }


    private fun init() {
        loadingPbCat = findViewById(R.id.loadingPbCat)
        initViewPager()
        initNavigation()
        loadCurrentUser()
        //fetchAllData()
    }

    private fun loadCurrentUser() {
        val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
        val message = sharedPreferences.getString(LOGIN_KEY, null)

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
            when (it.itemId) {
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


    fun fetchAllData() {
        val num = mainViewModel.getMealsCount()

        if (num > 40) {
            shouldShowLoad = false
            Timber.e("Already have some data: $num")
        }

        val subs = mainViewModel.fetchAllD()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        showLoadingState(shouldShowLoad) // Show loading state
                    }
                    is Resource.Success -> {
                        // All fetch operations were successful
                        showLoadingState(false) // Hide loading state

                        // Handle the success case
                        // You can access the result using resource.data
                    }
                    is Resource.Error -> {
                        showLoadingState(false) // Hide loading state

                        // Handle the error case
                    }
                }
            }
        mainViewModel.subscriptions.add(subs)
    }

    private fun showLoadingState(loading: Boolean) {
        viewPager?.isVisible = !loading
        bottomNavigationView?.isVisible = !loading
        loadingPbCat?.isVisible = loading
    }


}