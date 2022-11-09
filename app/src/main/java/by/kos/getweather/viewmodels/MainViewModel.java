package by.kos.getweather.viewmodels;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import by.kos.getweather.R;
import by.kos.getweather.utils.ApiFactory;
import by.kos.getweather.datamodels.Weather;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.LinkedHashMap;

public class MainViewModel extends AndroidViewModel {

  private static final String TAG = "mainVM";
  private static final String BASE_URL = "https://api.open-meteo.com/";
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private MutableLiveData<Weather> weather = new MutableLiveData<>();
  private MutableLiveData<Boolean> isError = new MutableLiveData<>();
  private MutableLiveData<Boolean> isLoad = new MutableLiveData<>();


  public LiveData<Weather> getWeather() {
    return weather;
  }

  public LiveData<Boolean> getIsError() {
    return isError;
  }

  public LiveData<Boolean> getIsLoad() {
    return isLoad;
  }

  public MainViewModel(@NonNull Application application) {
    super(application);
  }

  public void loadWeather(Double lat, Double lon) {

    Disposable disposableWeather = loadWeatherRx(lat, lon).
        subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(load -> isLoad.setValue(true))
        .doAfterSuccess(success -> isLoad.setValue(false))
        .doOnError(throwable -> {
          isError.setValue(true);
        })
        .subscribe(weatherData -> {
          weather.setValue(weatherData);
        }, throwable -> {
          Log.d(TAG, throwable.getMessage());
        });
    compositeDisposable.add(disposableWeather);
  }

  private Single<Weather> loadWeatherRx(Double lat, Double lon) {
    return ApiFactory.getApiService().loadWeather(lat, lon);
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    compositeDisposable.dispose();
  }
}
