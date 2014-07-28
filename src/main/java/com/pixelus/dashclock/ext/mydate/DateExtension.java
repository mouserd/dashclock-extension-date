package com.pixelus.dashclock.ext.mydate;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.crashlytics.android.Crashlytics;
import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.Intent.ACTION_MAIN;
import static android.content.Intent.CATEGORY_LAUNCHER;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static java.text.DateFormat.LONG;
import static java.text.DateFormat.SHORT;

public class DateExtension extends DashClockExtension {

  public static final String TAG = DateExtension.class.getSimpleName();
  public static final String PREF_CLICK_INTENT_APPLICATION = "pref_click_intent_shortcut";

  @Override public void onCreate() {
    super.onCreate();

    Crashlytics.start(this);
  }

  @Override
  protected void onUpdateData(int i) {

    final Date currentDate = new Date();
    final String status = DateFormat.getDateInstance(SHORT).format(currentDate);
    final String expandedTitle = getDayOfWeek(currentDate) + " " + DateFormat.getDateInstance(LONG).format(currentDate);

    Log.d(TAG, "short date: " + status);
    Log.d(TAG, "long date: " + expandedTitle);

    publishUpdate(new ExtensionData()
            .visible(true)
            .icon(R.drawable.ic_launcher)
            .status(status)
            .expandedTitle(expandedTitle)
            .clickIntent(createClickIntent())
    );
  }

  private String getDayOfWeek(Date currentDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("EEEEEEE"); // Set your date format
    return sdf.format(currentDate);
  }

  public Intent createClickIntent() {

    final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
    final String intentPackageName = sp.getString(PREF_CLICK_INTENT_APPLICATION, "");
    if (intentPackageName.isEmpty()) {
      return null;
    }

    final Intent intent = new Intent(ACTION_MAIN);
    intent.addCategory(CATEGORY_LAUNCHER);
    intent.setComponent(ComponentName.unflattenFromString(intentPackageName));
    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);

    return intent;
  }
}