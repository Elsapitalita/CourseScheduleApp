package com.dicoding.courseschedule.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.DailyReminder

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        val theme = findPreference<ListPreference>(getString(R.string.pref_key_dark))
        theme?.setOnPreferenceChangeListener{
            _, newValue ->
            when(newValue)
            {
                getString(R.string.pref_dark_auto) -> updateTheme(-1)
                getString(R.string.pref_dark_off) -> updateTheme(1)
                getString(R.string.pref_dark_on) -> updateTheme(2)
            }
            true
        }
        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val prefNotif = findPreference<SwitchPreference>(getString(R.string.pref_key_notify))
        prefNotif?.setOnPreferenceChangeListener{
            _, newValue ->
            val dailyReminder = DailyReminder()

            if (newValue == true){
                dailyReminder.setDailyReminder(requireContext())
            } else{
                dailyReminder.cancelAlarm(requireContext())
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}