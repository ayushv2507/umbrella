package com.foo.umbrella.ui;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.foo.umbrella.R;
import com.foo.umbrella.data.ApiServicesProvider;
import com.foo.umbrella.data.ForecastConditionAdapter;
import com.foo.umbrella.data.api.WeatherService;
import com.foo.umbrella.data.model.CurrentObservation;
import com.foo.umbrella.data.model.ForecastCondition;
import com.foo.umbrella.data.model.WeatherData;
import com.foo.umbrella.ui.adapter.GridAdapter;
import com.foo.umbrella.ui.adapter.WeatherDataAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private WeatherDataAdapter mWeatherDataAdapter;
    private FrameLayout current_observation;
    private TextView current_city, current_temperature, current_detail;
    float temperature;
    private String temp_unit, zipCode;
    private ArrayList<ForecastCondition> flist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        current_observation = (FrameLayout) findViewById(R.id.current_observation);
        current_city = (TextView) findViewById(R.id.city_country);
        current_temperature = (TextView) findViewById(R.id.current_temp);
        current_detail = (TextView) findViewById(R.id.current_condition);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        temp_unit = sharedPrefs.getString("unit", "Fahrenheit");
        zipCode = sharedPrefs.getString("zip_code", null);
        Log.d(TAG, "Zipdode: " + zipCode);
        if (zipCode != null) {
            // fetch weather data
            initRecyclerView();
            retrieveWeatherData(zipCode);
        } else {
            // redirect to preference screen
            Log.d(TAG, "starting preference activity");
            Intent pref = new Intent(this, PreferenceActivity.class);
            startActivity(pref);
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        retrieveWeatherData(zipCode);
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) this.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mWeatherDataAdapter = new WeatherDataAdapter(getApplicationContext(), flist, temp_unit);
        mRecyclerView.setAdapter(mWeatherDataAdapter);
    }

    private void retrieveWeatherData(String zipcode) {
        //TODO figure out how to get this from API
        ApiServicesProvider obj = new ApiServicesProvider((Application) getApplicationContext());
        WeatherService weatherService = obj.getWeatherService();
        Call<WeatherData> call = weatherService.forecastForZipCallable(zipcode);
        call.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {

                WeatherData weatherData = response.body();
                CurrentObservation obs = weatherData.getCurrentObservation();
                List<ForecastCondition> list = new ArrayList<>();
                list = weatherData.getForecast();
                displayCurrentObservation(obs);
                displayForecast(list);
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(getApplicationContext(), "On Failure called", Toast.LENGTH_SHORT).show();
                Log.d("Failure", "error: " + t.getCause());
                Log.d("Failure", "error: " + Arrays.toString(t.getStackTrace()));
            }
        });
    }

    private void displayForecast(List<ForecastCondition> list) {
        mWeatherDataAdapter = new WeatherDataAdapter(this, list, temp_unit);

        mWeatherDataAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mWeatherDataAdapter);
    }

    private void displayCurrentObservation(CurrentObservation obs) {

        current_city.setText(obs.getDisplayLocation().getFullName());
        temperature = Float.parseFloat((obs.getTempFahrenheit()));
        if(temperature<60) {
            current_observation.setBackgroundColor(ContextCompat.getColor(this, R.color.weather_cool));
        }

        if(temp_unit.equalsIgnoreCase("Celsius")){
        current_temperature.setText(obs.getTempCelsius() + (char) 0x00B0);
        }
        else  current_temperature.setText(obs.getTempFahrenheit() + (char) 0x00B0);
        current_detail.setText(obs.getWeatherDescription() );
    }

    public void setPreferences(View view) {
        Intent pref = new Intent(this, PreferenceActivity.class);
        startActivity(pref);
    }
}