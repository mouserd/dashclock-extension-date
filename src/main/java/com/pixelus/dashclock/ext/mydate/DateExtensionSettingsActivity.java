package com.pixelus.dashclock.ext.mydate;

import android.os.Bundle;
import com.crashlytics.android.Crashlytics;
import com.pixelus.android.activity.SharedPreferenceActivity;

public class DateExtensionSettingsActivity
    extends SharedPreferenceActivity {

  @Override
  public int getIconResourceId() {
    return R.drawable.ic_launcher;
  }

  @Override
  public int getPreferencesXmlResourceId() {
    return R.xml.preferences;
  }

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Crashlytics.start(this);
  }
}