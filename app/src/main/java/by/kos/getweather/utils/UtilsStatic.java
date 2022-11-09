package by.kos.getweather.utils;

import android.app.Application;
import android.content.Context;
import by.kos.getweather.R;
import java.util.HashMap;

public class UtilsStatic {

  private static String time;
  private static String date;

  public static HashMap<Integer, String> getWeatherState(Context context) {
    int[] keys = context.getResources().getIntArray(R.array.weatherCodeKeys);
    String[] values = context.getResources().getStringArray(R.array.weatherCodeValues);
    HashMap<Integer, String> weatherState = new HashMap<>();
    for (int i = 0; i < keys.length; i++) {
      weatherState.put(keys[i], values[i]);
    }
    return weatherState;
  }

  public static HashMap<String, String> formatTimeDate(String dateTime) {
    HashMap<String, String> result = new HashMap<>();
    if (dateTime.contains("T")) {
      String[] arr = dateTime.split("T");
      result.put("time", arr[1]);
      String[] arrDates = arr[0].split("-");
      result.put("date", arrDates[2] + "." + arrDates[1] + "." + arrDates[0]);
    } else {
      String[] arr = dateTime.split("-");
      result.put("date", arr[2] + "." + arr[1] + "." + arr[0]);
    }
    return result;
  }

}
