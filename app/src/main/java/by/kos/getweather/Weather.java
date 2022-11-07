package by.kos.getweather;

import java.util.ArrayList;

public class Weather<N extends Number, S extends String> {

  private final N latitude;
  private final N longitude;
  private final Daily daily;

  public Weather(N latitude, N longitude, Daily daily) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.daily = daily;
  }

  public N getLatitude() {
    return latitude;
  }

  public Daily getDaily() {
    return daily;
  }

  public N getLongitude() {
    return longitude;
  }

  class Daily {

    ArrayList<S> time;
    ArrayList<N> temperature_2m_max;
    ArrayList<N> temperature_2m_min;

    public Daily(ArrayList<S> time, ArrayList<N> temperature_2m_max,
        ArrayList<N> temperature_2m_min) {
      this.time = time;
      this.temperature_2m_max = temperature_2m_max;
      this.temperature_2m_min = temperature_2m_min;
    }

    public ArrayList<S> getTime() {
      return time;
    }

    public ArrayList<N> getTemperature_2m_max() {
      return temperature_2m_max;
    }

    public ArrayList<N> getTemperature_2m_min() {
      return temperature_2m_min;
    }
  }

}
