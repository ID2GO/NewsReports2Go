package eu.id2go.news2go;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public static class ReportPreferenceFragment extends PreferenceFragment implements
            Preference.OnPreferenceChangeListener {

        // @Override the onCreate method of EarthquakePreferenceFragment
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);

            Preference minMagnitude = findPreference(getString(R.string.settings_tags_key));
            bindPreferenceSummaryToValue(minMagnitude);

        }

        //  This method is called when the user has changed a Preference, so inside of it we
        //  should add whatever action we want to happen after this change (update the displayed
        //  summary).
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            //  Update the displayed preference
            String stringValue = value.toString();
//            preference.setSummary(stringValue);
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }


            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            //  The setOnPreferenceChangeListener is set the current EarthquakePreferenceFragment
            //  instance to listen for changes to the preference we pass in
            preference.setOnPreferenceChangeListener(this);
            //  read the current value of the preference stored in the SharedPreferences on the device,
            //  and display that in the preference summary (so that the user can see the current
            //  value of the preference)
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences
                    (preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }
}
