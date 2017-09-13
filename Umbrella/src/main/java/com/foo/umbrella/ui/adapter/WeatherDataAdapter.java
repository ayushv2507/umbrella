package com.foo.umbrella.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.foo.umbrella.R;
import com.foo.umbrella.data.model.ForecastCondition;
import com.foo.umbrella.data.model.WeatherData;
import com.foo.umbrella.ui.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayush on 9/10/2017.
 */

public class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataAdapter.ViewHolder> {
    private List<ForecastCondition> itemList;
    private Context context;
    private String unit;

    public WeatherDataAdapter(Context context, List<ForecastCondition> itemList, String unit) {
        this.itemList = itemList;
        this.context = context;
        this.unit = unit;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, null);
        return new ViewHolder(layoutView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       initConvertView(itemList.get(position),holder);
        findMinMaxTemp(itemList);
    }

    private void findMinMaxTemp(List<ForecastCondition> itemList) {
        ForecastCondition minItem = null, maxItem;
        for (ForecastCondition item: itemList) {

            if( Float.parseFloat(item.getTempFahrenheit()) <=  Float.parseFloat(minItem.getTempFahrenheit())){
                minItem = item;
            }
        }
    }

    @Override
    public int getItemCount() {
        if(itemList!=null && !itemList.isEmpty())
        return this.itemList.size();
        else return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initConvertView(ForecastCondition item, ViewHolder vHolder) {
        initTimeValue(item, vHolder);
        initTemperatureValue(item, vHolder);
        initIconDrawable(item, vHolder);
    }

    private void initTimeValue(ForecastCondition item, ViewHolder vHolder) {
        vHolder.forecastItemTime.setText(item.getDisplayTime());
    }

    private void initTemperatureValue(ForecastCondition item, ViewHolder vHolder) {
        if(unit.equalsIgnoreCase("Fahrenheit"))
        vHolder.forecastItemTemperature.setText(item.getTempFahrenheit());
        else
            vHolder.forecastItemTemperature.setText(item.getTempCelsius());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initIconDrawable(ForecastCondition item, ViewHolder vHolder) {

        int icon = ImageUtils.getIconResourceForWeatherCondition(item.getIcon());
        vHolder.forecastItemIcon.setImageDrawable(context.getDrawable(icon));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView forecastItemIcon;
        TextView forecastItemTime;
        TextView forecastItemTemperature;


        ViewHolder(View itemView) {
            super(itemView);
            forecastItemIcon = (ImageView) itemView.findViewById(R.id.weather_icon);
            forecastItemTime = (TextView) itemView.findViewById(R.id.weather_time);
            forecastItemTemperature = (TextView) itemView.findViewById(R.id.weather_temp);
        }
    }
}
