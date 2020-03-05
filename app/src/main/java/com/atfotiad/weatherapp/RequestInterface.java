package com.atfotiad.weatherapp;

import com.atfotiad.weatherapp.Model.OpenWeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {

        @GET("weather")
        Call<OpenWeatherResponse> getWeather(@Query("q") String search
                , @Query("appid") String apikey, @Query("units") String units);
    }


