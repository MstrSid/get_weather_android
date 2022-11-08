package by.kos.getweather.utils;

import by.kos.getweather.datamodels.Weather;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ApiService {

  @GET("forecast?latitude=53.1384&longitude=29.2214&daily=temperature_2m_max,temperature_2m_min&timezone=Europe%2FMoscow&current_weather=true")
  Single<Weather> loadWeather();

}
