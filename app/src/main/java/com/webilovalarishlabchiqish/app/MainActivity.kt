package com.webilovalarishlabchiqish.app

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.webilovalarishlabchiqish.app.fragments.About
import com.webilovalarishlabchiqish.app.fragments.LessonsFragment
import com.webilovalarishlabchiqish.app.fragments.PracticeFragment
import com.webilovalarishlabchiqish.app.fragments.SettingsFragment
import com.webilovalarishlabchiqish.app.fragments.SettingsFragment.Companion.KEY_LANGUAGE
import com.webilovalarishlabchiqish.app.fragments.SettingsFragment.Companion.PREFS_NAME
import com.webilovalarishlabchiqish.app.fragments.TestsFragment
import com.webilovalarishlabchiqish.application.R
import com.webilovalarishlabchiqish.application.databinding.ActivityMainBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout

    companion object{
        var isNightMode = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        val sharedPrefs = getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        val darkModeEnabled = sharedPrefs.getBoolean("dark_mode", false)

        if (darkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        updateLocale(applicationContext)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initView()

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_lessons -> {

                    replaceFragment(LessonsFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)

                }

                R.id.nav_practice -> {
                    replaceFragment(PracticeFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.nav_tests -> {
                    replaceFragment(TestsFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.nav_profile -> {
                    replaceFragment(SettingsFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                }

                R.id.nav_about -> {
                    replaceFragment(About())
                    drawerLayout.closeDrawer(GravityCompat.START)
                }

            }
            true
        }

        binding.menu.setOnClickListener {

            val navigationView = binding.navigationView
            val backgroundView = binding.drawerBackgroundView

            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(navigationView)
            } else {
                drawerLayout.openDrawer(navigationView)
            }

            backgroundView.setOnClickListener {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
        }

        binding.aboutapp.setOnClickListener {
            replaceFragment(About())
        }

    }

    fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


    private fun initView(){

        drawerLayout = binding.drawerLayout

        replaceFragment(LessonsFragment())

        val getEditor = getSharedPreferences("app_settings", MODE_PRIVATE)

    }

    private fun updateLocale(context: Context): Context {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val langCode = sharedPrefs.getString(KEY_LANGUAGE, "uz") ?: "uz"
        val locale = Locale(langCode)
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }
}