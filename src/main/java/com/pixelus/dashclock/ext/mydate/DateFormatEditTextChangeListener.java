package com.pixelus.dashclock.ext.mydate;


import android.preference.EditTextPreference;
import android.preference.Preference;
import android.util.Log;
import com.pixelus.android.activity.SharedPreferenceActivity;

import java.text.SimpleDateFormat;

public class DateFormatEditTextChangeListener
  implements Preference.OnPreferenceChangeListener {

  private static final String TAG = DateFormatEditTextChangeListener.class.getSimpleName();
  private SharedPreferenceActivity parent;
  private String defaultDateFormat;

  public DateFormatEditTextChangeListener(SharedPreferenceActivity parent, final String defaultDateFormat) {
    this.parent = parent;
    this.defaultDateFormat = defaultDateFormat;
  }

  @Override
  public boolean onPreferenceChange(final Preference preference, final Object value) {

    final EditTextPreference editTextPreference = (EditTextPreference) preference;
    final String stringValue = value.toString();

    // Validate the user date format pattern by constructing a new SimpleDateFormat.  If it can't be parsed
    // an exception will be thrown and the preference isn't persisted.
    try {
      new SimpleDateFormat(stringValue);
    } catch (final IllegalArgumentException e) {
      Log.e(TAG, "Your date format (" + stringValue + ") is not valid, falling back to default format");
//      editTextPreference.setText(defaultDateFormat);
      editTextPreference.setSummary(R.string.invalid_date_pattern_message); // Provide the user with feedback that the pattern is invalid.
      return false;
    }

    editTextPreference.setText(stringValue); // Note: we have to setText here to work around a bug that means that
                                             // the following summary update wouldn't otherwise be reflected on the UI!
    editTextPreference.setSummary(DateExtensionSettingsUtils.buildSummaryText(stringValue));

    return true;
  }
}
