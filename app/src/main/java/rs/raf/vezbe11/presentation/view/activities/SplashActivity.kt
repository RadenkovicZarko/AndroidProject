package rs.raf.vezbe11.presentation.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import androidx.appcompat.app.AppCompatActivity

import rs.raf.vezbe11.R
import javax.security.auth.login.LoginException

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        // Create a new SplashScreen with a time delay
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainNavigationActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}