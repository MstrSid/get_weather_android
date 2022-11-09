package by.kos.getweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import by.kos.getweather.WeatherRVAdapter.WeatherViewHolder;
import by.kos.getweather.utils.UtilsStatic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeatherRVAdapter extends RecyclerView.Adapter<WeatherViewHolder> {

  List<String> timeList = new ArrayList<>();
  List<Double> tMinList = new ArrayList<>();
  List<Double> tMaxList = new ArrayList<>();
  List<Integer> weatherStateList = new ArrayList<>();

  public void setTimeList(List<String> timeList) {
    this.timeList = timeList;
  }

  public void settMinList(List<Double> tMinList) {
    this.tMinList = tMinList;
  }

  public void settMaxList(List<Double> tMaxList) {
    this.tMaxList = tMaxList;
  }

  public void setWeatherStateList(List<Integer> weatherStateList) {
    this.weatherStateList = weatherStateList;
  }

  @NonNull
  @Override
  public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.forecast_item, parent, false);
    return new WeatherViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
    String time = timeList.get(position);
    Double tMin = tMinList.get(position);
    Double tMax = tMaxList.get(position);
    Integer weatherStateCode = weatherStateList.get(position);

    holder.tvForecastDate.setText(UtilsStatic.formatTimeDate(time).get("date"));
    holder.tvTMinData.setText(tMin.toString());
    holder.tvTMaxData.setText(tMax.toString());
    HashMap<Integer, String> states = UtilsStatic.getWeatherState(holder.itemView.getContext());
    for (Integer key : states.keySet()) {
      if (weatherStateCode == key) {
        holder.tvWeatherStateDaily.setText(states.get(key));
      }
    }
  }

  @Override
  public int getItemCount() {
    return timeList.size();
  }

  class WeatherViewHolder extends RecyclerView.ViewHolder {

    private TextView tvForecastDate;
    private TextView tvWeatherStateDaily;
    private TextView tvTMinData;
    private TextView tvTMaxData;

    public WeatherViewHolder(@NonNull View itemView) {
      super(itemView);
      tvForecastDate = itemView.findViewById(R.id.tvForecastDate);
      tvWeatherStateDaily = itemView.findViewById(R.id.tvWeatherStateDaily);
      tvTMinData = itemView.findViewById(R.id.tvTMinData);
      tvTMaxData = itemView.findViewById(R.id.tvTMaxData);
    }
  }
}
