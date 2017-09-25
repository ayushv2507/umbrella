package com.foo.umbrella.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.foo.umbrella.R;
import com.foo.umbrella.data.model.ForecastCondition;
import com.foo.umbrella.data.model.WeatherData;
import com.foo.umbrella.ui.ImageUtils;
import com.squareup.picasso.Picasso;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ayush on 9/21/2017.
 */

public class WeatherDataAdapter extends RecyclerView.Adapter<WeatherDataAdapter.ViewHolder> {
    private List<ForecastCondition> itemList;
    private Context context;
    private LocalDate today;
    private int count = 0;
    private List<ForecastCondition> todaylist, tomlist;
    private String unit;

    public WeatherDataAdapter(Context context, List<ForecastCondition> itemList, String unit) {
        this.itemList = itemList;
        this.context = context;
        this.unit = unit;
        todaylist = new ArrayList<>();
        tomlist = new ArrayList<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView1 = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_view, null, false);
        convert(itemList);
        return new ViewHolder(layoutView1);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setGridAdapter(holder);
    }

    private void setGridAdapter(ViewHolder holder) {
        GridAdapter booksAdapter;
        if (count <= 1) {

            booksAdapter = new GridAdapter(context, todaylist, unit);
            holder.day.setText(R.string.Today);
        } else {
            booksAdapter = new GridAdapter(context, tomlist, unit);
            holder.day.setText(R.string.Tomorrow);
        }
        holder.gridView.setAdapter(booksAdapter);

    }

    private void convert(List<ForecastCondition> itemList) {
        count++;
        today = itemList.get(0).getDateTime().toLocalDate();
        if (count <= 1) {
            for (int i = 0; i < itemList.size(); i++) {
                if (itemList.get(i).getDateTime().toLocalDate().isEqual(today)) {
                    todaylist.add(itemList.get(i));
                } else
                    tomlist.add(itemList.get(i));
            }
        }
    }


    private void findMinMaxTemp(List<ForecastCondition> itemList) {
        ForecastCondition minItem = null, maxItem;
        for (ForecastCondition item : itemList) {

            if (Float.parseFloat(item.getTempFahrenheit()) <= Float.parseFloat(minItem.getTempFahrenheit())) {
                minItem = item;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (itemList != null && !itemList.isEmpty())
            return 2;
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        GridView gridView;
        TextView day;

        ViewHolder(View itemView) {
            super(itemView);
            gridView = (GridView) itemView.findViewById(R.id.gridview);
            day = (TextView) itemView.findViewById(R.id.day);
        }
    }
}
