package edu.umkc.burrise.countaloud;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/*
  If you are targeting older than 3.0, extend PreferenceActivity. If
   3.0 or newer, extend Activty and use a fragment.
 */
public class AppPreferencesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        // I assume the default layout for an Activity
        //  has a field called android.R.id.content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new AppPreferenceFragment())
                .commit();
    }

    public static class AppPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            // ************* BEGIN OPTIONAL *****************
            // You only need this if you want to include
            // the preference value in the summary that shows
            EditTextPreference maxLoginAttemptsPreference = (EditTextPreference) findPreference("loginAttemptsPreferenceKey");

            // Set initial summary from default value.
            maxLoginAttemptsPreference.setSummary(
                    composeSummaryText( maxLoginAttemptsPreference.getText() ));

            // Set up a listener to change summary display value when
            //  field value changes.
            maxLoginAttemptsPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    preference.setSummary(composeSummaryText(newValue.toString()));
                    return true;
                }
            });
            // ************* END OPTIONAL *****************
        }

        private String composeSummaryText(String s) {
            return "Lock after " + s + " failed logins.";
        }
    }
}