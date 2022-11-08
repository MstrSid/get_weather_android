package by.kos.getweather;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import by.kos.getweather.utils.UtilsStatic;
import by.kos.getweather.viewmodels.MainViewModel;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

  private MainViewModel viewModel;
  private static final String TAG = "mainACTV";
  private TextView tvGPSDataLat;
  private TextView tvGPSDataLong;
  private TextView tvDate;
  private TextView tvTime;
  private TextView tvTNowData;
  private TextView tvWindSpeedData;
  private TextView tvWeatherCodeData;
  private ProgressBar progressBar;
  private HashMap<Integer, String> weatherStates;
  private Double latitude;
  private Double longitude;
  private ArrayList<String> timeList;
  private ArrayList<Double> tMinList;
  private ArrayList<Double> tMaxList;
  private Double tNow;
  private Double windSpeedNow;
  private Integer weatherCodeNow;
  private String dateTimeNow;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    weatherStates = UtilsStatic.getWeatherState(getApplication());
    viewModel.loadWeather();
    initViews();
    setObservers();
  }

  private void initViews() {
    tvGPSDataLat = findViewById(R.id.tvGPSDataLat);
    tvGPSDataLong = findViewById(R.id.tvGPSDataLong);
    progressBar = findViewById(R.id.progressBar);
    tvDate = findViewById(R.id.tvDate);
    tvTime = findViewById(R.id.tvTime);
    tvTNowData = findViewById(R.id.tvTNowData);
    tvWindSpeedData = findViewById(R.id.tvWindSpeedData);
    tvWeatherCodeData = findViewById(R.id.tvWeatherCodeData);
  }

  private void setObservers() {

    viewModel.getWeather().observe(this, weather -> {
      latitude = weather.getLatitude();
      longitude = weather.getLongitude();
      timeList = weather.getDaily().getTime();
      tMinList = weather.getDaily().getTemperature_2m_min();
      tMaxList = weather.getDaily().getTemperature_2m_max();
      tNow = weather.getCurrentWeather().getTemperature();
      windSpeedNow = weather.getCurrentWeather().getWindspeed();
      weatherCodeNow = weather.getCurrentWeather().getWeathercode();
      dateTimeNow = weather.getCurrentWeather().getTime();
      tvGPSDataLat.setText(latitude.toString());
      tvGPSDataLong.setText(longitude.toString());
      tvTNowData.setText(tNow.toString());
      tvWindSpeedData.setText(windSpeedNow.toString());
      String[] arr = dateTimeNow.split("T");
      tvTime.setText(arr[1]);
      tvDate.setText(arr[0]);
      tvWeatherCodeData.setText(getWeatherStateText());
    });

    viewModel.getIsError().observe(this, isError -> {
      if (isError) {
        Snackbar.make(getWindow().getDecorView().getRootView(), R.string.txt_error_loading,
            Snackbar.LENGTH_SHORT).show();
      }

      viewModel.getIsLoad().observe(this, isLoading -> {
        if (isLoading) {
          Log.d(TAG, "Loading");
          progressBar.setVisibility(View.VISIBLE);
        } else {
          Log.d(TAG, "Loaded");
          progressBar.setVisibility(View.INVISIBLE);
        }
      });
    });
  }

  private String getWeatherStateText() {
    String result = "";
    for (Integer key : weatherStates.keySet()) {
      if (weatherCodeNow == key) {
        result = weatherStates.get(key);
      }
    }
    return result;
  }

  private void logging() {
    Log.d(TAG, "latitude: " + latitude);
    Log.d(TAG, "longitude: " + longitude);
    for (Object o : timeList) {
      Log.d(TAG, o.toString());
    }
    for (Object o : tMinList) {
      Log.d(TAG, "t. min: " + o.toString());
    }
    for (Object o : tMaxList) {
      Log.d(TAG, "t. max: " + o.toString());
    }
    Log.d(TAG, "On time now: " + dateTimeNow);
    Log.d(TAG, "t. now: " + tNow);
    Log.d(TAG, "wind speed now:  " + windSpeedNow);
  }
}