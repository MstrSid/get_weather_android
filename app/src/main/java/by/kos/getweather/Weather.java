package by.kos.getweather;

import java.util.ArrayList;

public class Weather {

  private double latitude;
  private double longitude;
  private Daily daily;

  public Weather(Double latitude, Double longitude, Daily daily) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.daily = daily;
  }

  public Double getLatitude() {
    return latitude;
  }

  public Daily getDaily() {
    return daily;
  }

  public double getLongitude() {
    return longitude;
  }

  class Daily {

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
