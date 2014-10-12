package com.pixelus.dashclock.ext.mydate;

import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.crashlytics.android.Crashlytics;
import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

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

  public static final String PREF_USE_CUSTOM_DATE_FORMATS = "pref_use_custom_date_formats";

  public static final String PREF_SHORT_DATE_FORMAT = "pref_short_date_format";
  public static final String PREF_LONG_DATE_FORMAT = "pref_long_date_format";

  public static final String DEFAULT_LONG_DATE_FORMAT =
      "EEE " + ((SimpleDateFormat) SimpleDateFormat.getDateInstance(LONG)).toLocalizedPattern();
  public static final String DEFAULT_SHORT_DATE_FORMAT =
      ((SimpleDateFormat) SimpleDateFormat.getDateInstance(SHORT)).toLocalizedPattern();

  private SharedPreferences sp;

  @Override
  public void onCreate() {
    super.onCreate();

    sp = PreferenceManager.getDefaultSharedPreferences(this);

    Crashlytics.start(this);
  }

  @Override
  protected void onUpdateData(int i) {

    final Date currentDate = new Date();
    final SimpleDateFormat longDateFormat = getDateFormat(PREF_LONG_DATE_FORMAT, DEFAULT_LONG_DATE_FORMAT);
    final SimpleDateFormat shortDateFormat = getDateFormat(PREF_SHORT_DATE_FORMAT, DEFAULT_SHORT_DATE_FORMAT);

    final String status = shortDateFormat.format(currentDate);
    final String expandedTitle = longDateFormat.format(currentDate);

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

  private SimpleDateFormat getDateFormat(final String prefFormatKey, final String defaultFormat) {

    final String customDateFormat = sp.getString(prefFormatKey, "");
    final boolean useCustomDateFormats = sp.getBoolean(PREF_USE_CUSTOM_DATE_FORMATS, false);

    if (useCustomDateFormats && !customDateFormat.isEmpty()) {
        // Here we assume that the custom date format has already been validated and is safe!
        return new SimpleDateFormat(customDateFormat);
    }

    return new SimpleDateFormat(defaultFormat);
  }

  public Intent createClickIntent() {

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