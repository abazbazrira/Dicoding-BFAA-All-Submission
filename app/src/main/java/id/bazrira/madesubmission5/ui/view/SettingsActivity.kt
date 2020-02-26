package id.bazrira.madesubmission5.ui.view

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import id.bazrira.madesubmission5.R
import id.bazrira.madesubmission5.service.ReminderAlarmReceiver
import id.bazrira.madesubmission5.service.ReminderAlarmReceiver.Companion.TYPE_DAILY
import id.bazrira.madesubmission5.service.ReminderAlarmReceiver.Companion.TYPE_RELEASE


class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_settings -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

        private val reminderAlarmReceiver: ReminderAlarmReceiver = ReminderAlarmReceiver()

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            val switchDailyReminder: SwitchPreferenceCompat? =
                findPreference(getString(R.string.daily_reminder))
            val switchReleaseReminder: SwitchPreferenceCompat? =
                findPreference(getString(R.string.release_reminder))

            switchDailyReminder?.onPreferenceChangeListener = this
            switchReleaseReminder?.onPreferenceChangeListener = this
        }

        override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
            val key = preference!!.key
            val isSet = newValue as Boolean

            if (key == getString(R.string.daily_reminder)) {
                if (isSet) {
                    reminderAlarmReceiver.setDailyReminder(
                        requireContext(),
                        TYPE_DAILY,
                        getString(R.string.msg_daily_reminder)
                    )
                } else {
                    reminderAlarmReceiver.cancelAlarm(requireContext(), TYPE_DAILY)
                }
            } else {
                if (isSet) {
                    reminderAlarmReceiver.setReleaseReminder(
                        requireContext(),
                        TYPE_RELEASE,
                        getString(R.string.has_been_release)
                    )
                } else {
                    reminderAlarmReceiver.cancelAlarm(requireContext(), TYPE_RELEASE)
                }
            }
            return true
        }
    }
}
