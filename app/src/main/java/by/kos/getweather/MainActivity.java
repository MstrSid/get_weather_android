package by.kos.getweather;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import by.kos.getweather.utils.GpsTracker;
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
  private RecyclerView rvForecast;
  private WeatherRVAdapter weatherRVAdapter;
  private ImageView ivRefresh;

  private HashMap<Integer, String> weatherStates;
  private Double latitude;
  private Double longitude;
  private ArrayList<String> timeListDaily;
  private ArrayList<Double> tMinListDaily;
  private ArrayList<Double> tMaxListDaily;
  private ArrayList<Integer> weatherCodeDaily;
  private Double tNow;
  private Double windSpeedNow;
  private Integer weatherCodeNow;

  private String dateTimeNow;
  private String time;
  private String date;
  private GpsTracker gpsTracker;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initViews();
    weatherRVAdapter = new WeatherRVAdapter();
    rvForecast.setAdapter(weatherRVAdapter);
    rvForecast.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
    viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    weatherStates = UtilsStatic.getWeatherState(getApplication());
    getPermissions();
    setGPS();
    setObservers();
    ivRefresh.setOnClickListener(view -> {
      getPermissions();
      setGPS();
    });
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
    rvForecast = findViewById(R.id.rvForecast);
    ivRefresh = findViewById(R.id.ivRefresh);
  }

  private void getPermissions(){
    try {
      if (ContextCompat.checkSelfPermission(getApplicationContext(),
          android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this,
            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void setGPS(){
    gpsTracker = new GpsTracker(MainActivity.this);
    if (gpsTracker.canGetLocation()) {
      double latitude = gpsTracker.getLatitude();
      double longitude = gpsTracker.getLongitude();
      viewModel.loadWeather(latitude, longitude);
      gpsTracker.stopUsingGPS();
    } else {
      viewModel.loadWeather(0.0, 0.0);
      gpsTracker.showSettingsAlert();
      gpsTracker.stopUsingGPS();
    }
  }

  private void setObservers() {

    viewModel.getWeather().observe(this, weather -> {
      latitude = weather.getLatitude();
      longitude = weather.getLongitude();

      timeListDaily = weather.getDaily().getTime();
      tMinListDaily = weather.getDaily().getTemperature_2m_min();
      tMaxListDaily = weather.getDaily().getTemperature_2m_max();
      weatherCodeDaily = weather.getDaily().getWeathercodeD();

      tNow = weather.getCurrentWeather().getTemperature();
      windSpeedNow = weather.getCurrentWeather().getWindspeed();
      weatherCodeNow = weather.getCurrentWeather().getWeathercode();
      dateTimeNow = weather.getCurrentWeather().getTime();

      tvGPSDataLat.setText(latitude.toString());
      tvGPSDataLong.setText(longitude.toString());
      tvTNowData.setText(tNow.toString());
      tvWindSpeedData.setText(windSpeedNow.toString());
      tvTime.setText(UtilsStatic.formatTimeDate(dateTimeNow).get("time"));
      tvDate.setText(UtilsStatic.formatTimeDate(dateTimeNow).get("date"));
      tvWeatherCodeData.setText(getWeatherStateText());
      weatherRVAdapter.setTimeList(timeListDaily);
      weatherRVAdapter.settMinList(tMinListDaily);
      weatherRVAdapter.settMaxList(tMaxListDaily);
      weatherRVAdapter.setWeatherStateList(weatherCodeDaily);
      weatherRVAdapter.notifyDataSetChanged();
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
    for (Object o : timeListDaily) {
      Log.d(TAG, o.toString());
    }
    for (Object o : tMinListDaily) {
      Log.d(TAG, "t. min: " + o.toString());
    }
    for (Object o : tMaxListDaily) {
      Log.d(TAG, "t. max: " + o.toString());
    }
    Log.d(TAG, "On time now: " + dateTimeNow);
    Log.d(TAG, "t. now: " + tNow);
    Log.d(TAG, "wind speed now:  " + windSpeedNow);
  }
}