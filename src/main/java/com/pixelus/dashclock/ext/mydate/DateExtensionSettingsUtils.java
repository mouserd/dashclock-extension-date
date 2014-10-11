package com.pixelus.dashclock.ext.mydate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateExtensionSettingsUtils {

  public static String buildSummaryText(final String dateFormat) {
    return dateFormat + " (e.g. " + new SimpleDateFormat(dateFormat).format(new Date()) + ")";
  }
}
