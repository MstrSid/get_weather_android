package by.kos.getweather.utils;

import by.kos.getweather.datamodels.Weather;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

  @GET("forecast?daily=temperature_2m_max,temperature_2m_min&timezone=Europe%2FMoscow&current_weather=true")
  Single<Weather> loadWeather(
      @Query("latitude") Double lat,
      @Query("longitude") Double lon);
}
