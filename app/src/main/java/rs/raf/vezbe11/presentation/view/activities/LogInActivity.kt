package rs.raf.vezbe11.presentation.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.vezbe11.R
import rs.raf.vezbe11.databinding.ActivityLoginBinding
import rs.raf.vezbe11.presentation.contract.MainContract
import rs.raf.vezbe11.presentation.view.states.UserState
import rs.raf.vezbe11.presentation.viewmodel.MealViewModel
import timber.log.Timber

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val mainViewModel: MainContract.ViewModel by viewModel<MealViewModel>()
    val LOGIN_KEY = "loginKey"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
        val message = sharedPreferences.getString(LOGIN_KEY,null)
        if (message != null) {
            val intent = Intent(this, MainNavigationActivity::class.java)
            startActivity(intent)
            finish()
        }

        setContentView(binding.root)
        init()
    }

    private fun init() {
        initUi()
    }

    private fun initUi() {

        binding.loginButton.setOnClickListener { e ->
            Timber.e("USAOO")
            mainViewModel.findUserWithUsernameAndPassword("ZareJarePare1", "123")
            mainViewModel.userState.observe(this, Observer { it ->
                when(it){
                    is UserState.Success -> {
//                        val sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
//                        val gson = Gson()
//                        val json: String = gson.toJson(User(username, email, password))
//
//                        val editor = sharedPreferences.edit()
//                        editor.putString(
//                            com.example.rafdnevnjakproject.view.activities.LoginActivity.LOGIN_KEY,
//                            json
//                        )
//                        editor.apply()
//                        val intent = Intent(this, MainActivity::class.java)
//                        startActivity(intent)
//                        finish()
                    }
                }
            })
        }
    }


    private fun renderState(state: UserState) {
        when (state) {
            is UserState.Success -> {
                Timber.e(state.users.get(0).userName)
            }
            is UserState.Error -> {
                Timber.e("Error")
            }
            is UserState.DataFetched -> {
                Timber.e("DataFetched")
            }
            is UserState.Loading -> {
                Timber.e("Loading")
            }
        }
    }


}