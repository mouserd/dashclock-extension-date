package com.pixelus.dashclock.ext.mydate;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.text.TextUtils;
import com.crashlytics.android.Crashlytics;
import com.pixelus.android.activity.SharedPreferenceActivity;

import static com.pixelus.dashclock.ext.mydate.DateExtension.DEFAULT_LONG_DATE_FORMAT;
import static com.pixelus.dashclock.ext.mydate.DateExtension.DEFAULT_SHORT_DATE_FORMAT;
import static com.pixelus.dashclock.ext.mydate.DateExtensionSettingsUtils.buildSummaryText;

public class DateExtensionSettingsActivity
    extends SharedPreferenceActivity {

  private static final String TAG = DateExtensionSettingsActivity.class.getSimpleName();

  public static final String PREF_USE_CUSTOM_DATE_FORMATS = "pref_use_custom_date_formats";
  public static final String PREF_SHORT_DATE_FORMAT = "pref_short_date_format";
  public static final String PREF_LONG_DATE_FORMAT = "pref_long_date_format";

  @Override
  public int getIconResourceId() {
    return R.drawable.ic_launcher;
  }

  @Override
  public int getPreferencesXmlResourceId() {
    return R.xml.preferences;
  }

  public void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Crashlytics.start(this);

    final EditTextPreference shortDateEditText = (EditTextPreference) findPreference(PREF_SHORT_DATE_FORMAT);
    final EditTextPreference longDateEditText = (EditTextPreference) findPreference(PREF_LONG_DATE_FORMAT);

    this.findPreference(PREF_USE_CUSTOM_DATE_FORMATS).setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

      @Override
      public boolean onPreferenceChange(final Preference preference, final Object value) {

        if (!Boolean.valueOf(value.toString())) {
          shortDateEditText.setText(DEFAULT_SHORT_DATE_FORMAT);
          shortDateEditText.setSummary(buildSummaryText(DEFAULT_SHORT_DATE_FORMAT));

          longDateEditText.setText(DEFAULT_LONG_DATE_FORMAT);
          longDateEditText.setSummary(buildSummaryText(DEFAULT_LONG_DATE_FORMAT));
        }

        return true;
      }
    });
    shortDateEditText.setOnPreferenceChangeListener(new DateFormatEditTextChangeListener(this, DEFAULT_SHORT_DATE_FORMAT));
    longDateEditText.setOnPreferenceChangeListener(new DateFormatEditTextChangeListener(this, DEFAULT_LONG_DATE_FORMAT));
  }

  @Override
  protected void onPostCreate(final Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);

    final EditTextPreference shortDateEditText = (EditTextPreference) this.findPreference(PREF_SHORT_DATE_FORMAT);
    shortDateEditText.setDefaultValue(DEFAULT_SHORT_DATE_FORMAT);
    if (TextUtils.isEmpty(shortDateEditText.getText())) {
      shortDateEditText.setText(DEFAULT_SHORT_DATE_FORMAT);
    }
    shortDateEditText.setSummary(buildSummaryText(shortDateEditText.getText()));

    final EditTextPreference longDateEditText = (EditTextPreference) this.findPreference(PREF_LONG_DATE_FORMAT);
    longDateEditText.setDefaultValue(DEFAULT_LONG_DATE_FORMAT);
    if (TextUtils.isEmpty(longDateEditText.getText())) {
      longDateEditText.setText(DEFAULT_LONG_DATE_FORMAT);
    }
    longDateEditText.setSummary(buildSummaryText(longDateEditText.getText()));
  }
}