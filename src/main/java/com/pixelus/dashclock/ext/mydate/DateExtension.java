package com.pixelus.dashclock.ext.mydate;

import android.util.Log;
import com.crashlytics.android.Crashlytics;
import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.text.DateFormat.LONG;
import static java.text.DateFormat.SHORT;

public class DateExtension extends DashClockExtension {

  public static final String TAG = DateExtension.class.getName();

  private boolean crashlyticsStarted = false;

  @Override
  protected void onUpdateData(int i) {

    if (!crashlyticsStarted) {
      Crashlytics.start(this);
      crashlyticsStarted = true;
    }

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
    );
  }

  private String getDayOfWeek(Date currentDate) {
    SimpleDateFormat sdf = new SimpleDateFormat("EEEEEEE"); // Set your date format
    return sdf.format(currentDate);
  }
}