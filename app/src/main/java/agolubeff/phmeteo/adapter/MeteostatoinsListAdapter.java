package agolubeff.phmeteo.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import agolubeff.phmeteo.R;
import agolubeff.phmeteo.model.Meteostation;

public class MeteostatoinsListAdapter extends RecyclerView.Adapter<MeteostationViewHolder>
{
    private Context context;
    private ArrayList<Meteostation> meteostation_list;

    public MeteostatoinsListAdapter(Context context, ArrayList<Meteostation> meteostation_list)
    {
        this.context = context;
        this.meteostation_list = meteostation_list;
    }

    @Override
    public int getItemCount()
    {
        return meteostation_list.size();
    }

    @Override
    public MeteostationViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meteostation_cardview_item, parent, false);
        MeteostationViewHolder pvh = new MeteostationViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(MeteostationViewHolder holder, final int position)
    {
        final Meteostation meteostation = meteostation_list.get(position);

        holder.station_name.setText(meteostation.GetName());
        holder.station_temperature.setText("temperature: " + String.valueOf(meteostation.GetTemperature()));
        holder.station_time.setText("time: " + meteostation.GetTime());
        holder.station_humidity.setText("humidity: " + String.valueOf(meteostation.GetHumidity()));
        holder.station_atmosphere_pressure.setText("atmosphere pressure: " + String.valueOf(meteostation.GetAtmospherePressure()));
    }
}

class MeteostationViewHolder extends RecyclerView.ViewHolder
{
    CardView card_view;
    TextView station_name;
    TextView station_temperature;
    TextView station_time;
    TextView station_humidity;
    TextView station_atmosphere_pressure;

    MeteostationViewHolder(View item)
    {
        super(item);

        card_view = item.findViewById(R.id.card_view);
        station_name = item.findViewById(R.id.station_name);
        station_temperature = item.findViewById(R.id.station_temperature);
        station_time = item.findViewById(R.id.station_time);
        station_humidity = item.findViewById(R.id.station_humidity);
        station_atmosphere_pressure = item.findViewById(R.id.station_atmosphere_pressure);

    }
}