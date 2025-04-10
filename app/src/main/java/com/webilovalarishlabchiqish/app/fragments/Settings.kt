package com.webilovalarishlabchiqish.app.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.webilovalarishlabchiqish.application.R
import com.webilovalarishlabchiqish.application.databinding.FragmentSettingsBinding
import java.util.Locale

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val PREFS_NAME = "app_settings"
        const val KEY_LANGUAGE = "language"
        const val KEY_NOTIFICATIONS = "notifications"
        const val KEY_DARK_MODE = "dark_mode"
    }

    val languageCodes = listOf("uz", "en", "ru")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPrefs = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val languages = listOf("O'zbek", "English", "Русский")

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            languages
        )
        adapter.setDropDownViewResource(R.layout.spinner_item)
        binding.languageSpinner.adapter = adapter

        val savedLanguage = sharedPrefs.getString(KEY_LANGUAGE, languageCodes[0])
        val selectedIndex = languageCodes.indexOf(savedLanguage)
        if (selectedIndex >= 0) binding.languageSpinner.setSelection(selectedIndex)

        binding.notificationsSwitch.isChecked =
            sharedPrefs.getBoolean(KEY_NOTIFICATIONS, true)
        binding.darkModeSwitch.isChecked =
            sharedPrefs.getBoolean(KEY_DARK_MODE, false)

        binding.languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedLanguage = languageCodes[position]
                val oldLanguage = sharedPrefs.getString(KEY_LANGUAGE, "")

                if (oldLanguage != selectedLanguage) {
                    sharedPrefs.edit().putString(KEY_LANGUAGE, selectedLanguage).apply()
                    setLocale(selectedLanguage)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean(KEY_NOTIFICATIONS, isChecked).apply()
        }

        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPrefs.edit().putBoolean(KEY_DARK_MODE, isChecked).apply()

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)

        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics)

        val intent = requireActivity().intent
        requireActivity().finish()
        startActivity(intent)
    }
}
