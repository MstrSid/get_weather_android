package by.kos.getweather.datamodels;

import java.util.ArrayList;

public class Weather {

  private final Double latitude;
  private final Double longitude;
  private final Daily daily;
  private final CurrentWeather current_weather;

  public Weather(Double latitude, Double longitude, Daily daily,
      CurrentWeather current_weather) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.daily = daily;
    this.current_weather = current_weather;
  }

  public CurrentWeather getCurrentWeather() {
    return current_weather;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Daily getDaily() {
    return daily;
  }

  public Double getLongitude() {
    return longitude;
  }


  public class CurrentWeather {

    private Double temperature;
    private Double windspeed;
    private Integer weathercode;
    private String time;

    public CurrentWeather(Double temperature, Double windspeed, Integer weathercode,
        String time) {
      this.temperature = temperature;
      this.windspeed = windspeed;
      this.weathercode = weathercode;
      this.time = time;
    }

    public Double getTemperature() {
      return temperature;
    }

    public Double getWindspeed() {
      return windspeed;
    }

    public Integer getWeathercode() {
      return weathercode;
    }

    public String getTime() {
      return time;
    }
  }

  public class Daily {

    ArrayList<String> time;
    ArrayList<Double> temperature_2m_max;
    ArrayList<Double> temperature_2m_min;

    public Daily(ArrayList<String> time, ArrayList<Double> temperature_2m_max,
        ArrayList<Double> temperature_2m_min) {
      this.time = time;
      this.temperature_2m_max = temperature_2m_max;
      this.temperature_2m_min = temperature_2m_min;
    }

    public ArrayList<String> getTime() {
      return time;
    }

    public ArrayList<Double> getTemperature_2m_max() {
      return temperature_2m_max;
    }

    public ArrayList<Double> getTemperature_2m_min() {
      return temperature_2m_min;
    }
  }

}
