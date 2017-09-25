package com.foo.umbrella.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.foo.umbrella.R;
import com.foo.umbrella.data.model.ForecastCondition;
import com.foo.umbrella.ui.ImageUtils;

import java.util.List;

/**
 * Created by ayush on 9/25/2017.
 */

public class GridAdapter extends BaseAdapter {

    private final Context mContext;
    private List<ForecastCondition> itemList;
    private String unit;

    // 1
    public GridAdapter(Context context, List<ForecastCondition> itemList, String unit) {
        this.itemList = itemList;
        this.mContext = context;
        this.unit = unit;
    }

    // 2
    @Override
    public int getCount() {
        if(itemList!=null && !itemList.isEmpty())
            return this.itemList.size();
        else return 0;
    }

    // 3
    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return null;
    }

    // 5
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.card_view_item, null);
        }

        ImageView forecastItemIcon = (ImageView) convertView.findViewById(R.id.weather_icon);
        TextView  forecastItemTime = (TextView) convertView.findViewById(R.id.weather_time);
        TextView   forecastItemTemperature = (TextView) convertView.findViewById(R.id.weather_temp);
        int icon = ImageUtils.getIconResourceForWeatherCondition(itemList.get(position).getIcon());
        forecastItemIcon.setImageDrawable(mContext.getDrawable(icon));
        if(unit.equalsIgnoreCase("Fahrenheit"))
            forecastItemTemperature.setText(itemList.get(position).getTempFahrenheit() + (char) 0x00B0);
        else forecastItemTemperature.setText(itemList.get(position).getTempCelsius() + (char) 0x00B0);
        forecastItemTime.setText(itemList.get(position).getDisplayTime());
        return convertView;
    }

}