package richellin.revien.android.util;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by richellin on 2017/01/18.
 */

public class SharedPrefUtil {
  public SharedPrefUtil() {
  }

  public static boolean setString(Context context, String s, String s1) {
    return PreferenceManager.getDefaultSharedPreferences(context).edit().putString(s, s1).commit();
  }

  public static boolean setInt(Context context, String s, int i) {
    return PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(s, i).commit();
  }

  public static boolean setLong(Context context, String s, Long l) {
    return PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(s, l).commit();
  }

  public static String getString(Context context, String s) {
    return SharedPrefUtil.getString(context, s, "");
  }

  public static String getString(Context context, String s, String d) {
    return PreferenceManager.getDefaultSharedPreferences(context).getString(s, d);
  }

  public static int getInt(Context context, String s) {
    return SharedPrefUtil.getInt(context, s, 0);
  }

  public static int getInt(Context context, String s, int i) {
    return PreferenceManager.getDefaultSharedPreferences(context).getInt(s, i);
  }

  public static long getLong(Context context, String s) {
    return SharedPrefUtil.getLong(context, s, 0L);
  }

  public static long getLong(Context context, String s, Long l) {
    return PreferenceManager.getDefaultSharedPreferences(context).getLong(s, l);
  }

  public static void remove(Context context, String s) {
    PreferenceManager.getDefaultSharedPreferences(context).edit().remove(s).commit();
  }
}
