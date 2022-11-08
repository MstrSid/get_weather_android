package by.kos.getweather.utils;

import android.app.Application;
import by.kos.getweather.R;
import java.util.HashMap;

public class UtilsStatic {

  public static HashMap<Integer, String> getWeatherState(Application application) {
    int[] keys = application.getResources().getIntArray(R.array.weatherCodeKeys);
    String[] values = application.getResources().getStringArray(R.array.weatherCodeValues);
    HashMap<Integer, String> weatherState = new HashMap<>();
    for (int i = 0; i < keys.length; i++) {
      weatherState.put(keys[i], values[i]);
    }
    return weatherState;
  }

}
