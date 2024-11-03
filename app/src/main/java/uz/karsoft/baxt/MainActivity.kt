package uz.karsoft.baxt

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController

class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        setContentView(R.layout.activity_main)

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        loadNightModePreference()
        loadNotificationPreference()
    }
    private fun loadNightModePreference() {
        val sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE)
        val isNightMode = sharedPreferences.getBoolean("night_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (isNightMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    fun loadNotificationPreference(): Boolean {
        return getSharedPreferences("notification", Context.MODE_PRIVATE)
            .getBoolean("notification_mode", false)
    }
}
