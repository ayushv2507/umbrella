package com.foo.umbrella.ui;

import com.foo.umbrella.R;

/**
 * Created by ayush on 9/12/2017.
 */

public class ImageUtils {
    public static int getIconResourceForWeatherCondition(String icon) {
            // Based on weather code data found at:
            // http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
            if (icon.equalsIgnoreCase("partlycloudy")) {
                return R.drawable.weather_partlycloudy;
            } else if (icon.equalsIgnoreCase("cloudy") || icon.equalsIgnoreCase("mostlycloudy")) {
                return R.drawable.weather_cloudy;
            } else if (icon.equalsIgnoreCase("clear")) {
                return R.drawable.weather_partlycloudy;
            } else if (icon.equalsIgnoreCase("fog")) {
                return R.drawable.weather_fog;
            } else if (icon.equalsIgnoreCase("hail")) {
                return R.drawable.weather_hail;
            } else if (icon.equalsIgnoreCase("lightning")) {
                return R.drawable.weather_lightning;
            } else if (icon.equalsIgnoreCase("lightning_rainy")) {
                return R.drawable.weather_lightning_rainy;
            } else if (icon.equalsIgnoreCase("rainy")) {
                return R.drawable.weather_rainy;
            } else if (icon.equalsIgnoreCase("snowy")) {
                return R.drawable.weather_snowy;
            } else if (icon.equalsIgnoreCase("snowy_rainy")) {
                return R.drawable.weather_snowy_rainy;
            } else if (icon.equalsIgnoreCase("sunny")) {
                return R.drawable.weather_sunny;
            } else if (icon.equalsIgnoreCase("windy_variant")) {
                return R.drawable.weather_windy_variant;
            }
            return -1;
    }
}
