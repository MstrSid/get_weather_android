package by.kos.getweather;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

  private MainViewModel viewModel;
  private static final String TAG = "mainACTV";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    viewModel.loadWeather();

    viewModel.getIsError().observe(this, isError -> {
      if (isError) {
        Snackbar.make(getWindow().getDecorView().getRootView(), R.string.txt_error_loading,
            Snackbar.LENGTH_SHORT).show();
      }

      viewModel.getIsLoad().observe(this, isLoading -> {
        if (isLoading) {
          Log.d(TAG, "Loading");
//          progressBar.setVisibility(View.VISIBLE);
        } else {
          Log.d(TAG, "Loaded");
//          progressBar.setVisibility(View.INVISIBLE);
        }
      });
    });

    viewModel.getWeatherLatitude().observe(this, latitude -> {
      Log.d(TAG, "latitude: " + latitude);
    });

    viewModel.getWeatherLongitude().observe(this, longitude -> {
      Log.d(TAG, "longitude: " + longitude);
    });

    viewModel.getWeatherTimeList().observe(this, timeList -> {
      for (Object o : timeList) {
        Log.d(TAG, o.toString());
      }
    });

    viewModel.getWeatherTMinList().observe(this, tMinList -> {
      for (Object o : tMinList) {
        Log.d(TAG, "t. min: " + o.toString());
      }
    });

    viewModel.getWeatherTMaxList().observe(this, tMaxList -> {
      for (Object o : tMaxList) {
        Log.d(TAG, "t. max: " + o.toString());
      }
    });
  }
}