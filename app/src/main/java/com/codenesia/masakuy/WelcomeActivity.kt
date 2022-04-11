package com.codenesia.masakuy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codenesia.masakuy.database.Preferences
import com.codenesia.masakuy.databinding.ActivityWelcomeBinding
import kotlinx.coroutines.launch

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var preferences = Preferences(this)
        Utils.checkTheme(preferences.isThemeDark())

        binding.btnGetStarted.setOnClickListener({
            goToMainActivity()
            preferences.setFirstLaunch(false)
        })

        if (!preferences.isFirstLaunch())
            goToMainActivity()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}