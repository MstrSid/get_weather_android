package by.kos.getweather;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import by.kos.getweather.viewmodels.MainViewModel;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  private MainViewModel viewModel;
  private static final String TAG = "mainACTV";
  private TextView tvGPSDataLat;
  private TextView tvGPSDataLong;
  private TextView tvDate;
  private TextView tvTime;
  private TextView tvTNowData;
  private TextView tvWindSpeedData;
  private ProgressBar progressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    viewModel = new ViewModelProvider(this).get(MainViewModel.class);
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
  }

  private void setObservers() {

    viewModel.getWeather().observe(this, weather -> {
      Double latitude = weather.getLatitude();
      Double longitude = weather.getLongitude();
      ArrayList<String> timeList = weather.getDaily().getTime();
      ArrayList<Double> tMinList = weather.getDaily().getTemperature_2m_min();
      ArrayList<Double> tMaxList = weather.getDaily().getTemperature_2m_max();
      Double tNow = weather.getCurrentWeather().getTemperature();
      Double windSpeedNow = weather.getCurrentWeather().getWindspeed();
      Integer weatherCodeNow = weather.getCurrentWeather().getWeathercode();
      String dateTimeNow = weather.getCurrentWeather().getTime();
      tvGPSDataLat.setText(latitude.toString());
      tvGPSDataLong.setText(longitude.toString());
      tvTNowData.setText(tNow.toString());
      tvWindSpeedData.setText(windSpeedNow.toString());
      String[] arr = dateTimeNow.split("T");
      tvTime.setText(arr[1]);
      tvDate.setText(arr[0]);
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
      switch (weatherCodeNow) {
        case 0:
          Log.d(TAG, "weather now: Clear sky");
          break;
        case 1:
          Log.d(TAG, "weather now: Mainly clear");
          break;
        case 2:
          Log.d(TAG, "weather now: Partly cloudy");
          break;
        case 3:
          Log.d(TAG, "weather now: Overcast");
          break;
        case 45:
          Log.d(TAG, "weather now: Fog");
          break;
        case 48:
          Log.d(TAG, "weather now: Depositing rime fog");
          break;
        case 51:
          Log.d(TAG, "weather now: Light drizzle");
          break;
        case 53:
          Log.d(TAG, "weather now: Moderate drizzle");
          break;
        case 55:
          Log.d(TAG, "weather now: Dense intensity drizzle");
          break;
        case 56:
          Log.d(TAG, "weather now: Light freezing drizzle");
          break;
        case 57:
          Log.d(TAG, "weather now: Dense intensity freezing drizzle");
          break;
        case 61:
          Log.d(TAG, "weather now: Slight rain");
          break;
        case 63:
          Log.d(TAG, "weather now: Moderate rain");
          break;
        case 65:
          Log.d(TAG, "weather now: Heavy rain");
          break;
        case 66:
          Log.d(TAG, "weather now: Light freezing rain");
          break;
        case 67:
          Log.d(TAG, "weather now: Heavy freezing rain");
          break;
        case 71:
          Log.d(TAG, "weather now: Slight snow fall");
          break;
        case 73:
          Log.d(TAG, "weather now: Moderate snow fall");
          break;
        case 75:
          Log.d(TAG, "weather now: Heavy snow fall");
          break;
        case 77:
          Log.d(TAG, "weather now: Snow grains");
          break;
        case 80:
          Log.d(TAG, "weather now: Slight rain shower");
          break;
        case 81:
          Log.d(TAG, "weather now: Moderate rain shower");
          break;
        case 82:
          Log.d(TAG, "weather now: Violent rain shower");
          break;
        case 85:
          Log.d(TAG, "weather now: Slight snow shower");
          break;
        case 86:
          Log.d(TAG, "weather now: Heavy snow shower");
          break;
        case 95:
          Log.d(TAG, "weather now: Slight or moderate thunderstorm");
          break;
        case 96:
          Log.d(TAG, "weather now: Thunderstorm with slight hail");
          break;
        case 99:
          Log.d(TAG, "weather now: Thunderstorm with heavy hail");
          break;
      }
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
}