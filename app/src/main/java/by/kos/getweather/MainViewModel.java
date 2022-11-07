package by.kos.getweather;

import android.app.Application;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {

  private static final String TAG = "mainVM";
  private static final String BASE_URL = "https://api.open-meteo.com/";
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private MutableLiveData<ArrayList<? extends String>> weatherTimeList = new MutableLiveData<>();
  private MutableLiveData<ArrayList<? extends Number>> weatherTMinList = new MutableLiveData<>();
  private MutableLiveData<ArrayList<? extends Number>> weatherTMaxList = new MutableLiveData<>();
  private MutableLiveData<Number> weatherLatitude = new MutableLiveData<>();
  private MutableLiveData<Number> weatherLongitude = new MutableLiveData<>();
  private MutableLiveData<Boolean> isError = new MutableLiveData<>();
  private MutableLiveData<Boolean> isLoad = new MutableLiveData<>();


  public LiveData<ArrayList<? extends String>> getWeatherTimeList() {
    return weatherTimeList;
  }

  public LiveData<ArrayList<? extends Number>> getWeatherTMinList() {
    return weatherTMinList;
  }

  public LiveData<ArrayList<? extends Number>> getWeatherTMaxList() {
    return weatherTMaxList;
  }

  public LiveData<Number> getWeatherLatitude() {
    return weatherLatitude;
  }

  public LiveData<Number> getWeatherLongitude() {
    return weatherLongitude;
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

  public void loadWeather() {

    Disposable disposableTimeList = loadWeatherRx().
        subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(load -> isLoad.setValue(true))
        .doAfterTerminate(() -> isLoad.setValue(false))
        .doOnError(throwable -> isError.setValue(true))
        .subscribe(weather -> {
          weatherTimeList.setValue(weather.getDaily().getTime());
        }, throwable -> {
          Log.d(TAG, throwable.getMessage());
        });

    Disposable disposableTMax = loadWeatherRx().
        subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(load -> isLoad.setValue(true))
        .doAfterTerminate(() -> isLoad.setValue(false))
        .doOnError(throwable -> isError.setValue(true))
        .subscribe(weather -> {
          weatherTMaxList.setValue(weather.getDaily().getTemperature_2m_max());
        }, throwable -> {
          Log.d(TAG, throwable.getMessage());
        });

    Disposable disposableTMin = loadWeatherRx().
        subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(load -> isLoad.setValue(true))
        .doAfterTerminate(() -> isLoad.setValue(false))
        .doOnError(throwable -> isError.setValue(true))
        .subscribe(weather -> {
          weatherTMinList.setValue(weather.getDaily().getTemperature_2m_min());
        }, throwable -> {
          Log.d(TAG, throwable.getMessage());
        });

    Disposable disposableLongitude = loadWeatherRx().
        subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(load -> isLoad.setValue(true))
        .doAfterTerminate(() -> isLoad.setValue(false))
        .doOnError(throwable -> isError.setValue(true))
        .subscribe(weather -> {
          weatherLongitude.setValue(weather.getLongitude());
        }, throwable -> {
          Log.d(TAG, throwable.getMessage());
        });

    Disposable disposableLatitude = loadWeatherRx().
        subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(load -> isLoad.setValue(true))
        .doAfterTerminate(() -> isLoad.setValue(false))
        .doOnError(throwable -> isError.setValue(true))
        .subscribe(weather -> {
          weatherLatitude.setValue(weather.getLatitude());
        }, throwable -> {
          Log.d(TAG, throwable.getMessage());
        });

    compositeDisposable.add(disposableTimeList);
    compositeDisposable.add(disposableTMax);
    compositeDisposable.add(disposableTMin);
    compositeDisposable.add(disposableLongitude);
    compositeDisposable.add(disposableLatitude);
  }

  private Single<Weather> loadWeatherRx() {
    return ApiFactory.getApiService().loadWeather();
  }

  @Override
  protected void onCleared() {
    super.onCleared();
    compositeDisposable.dispose();
  }
}
