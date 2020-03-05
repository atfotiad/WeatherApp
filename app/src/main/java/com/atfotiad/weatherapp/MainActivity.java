package com.atfotiad.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.atfotiad.weatherapp.Model.OpenWeatherResponse;
import com.atfotiad.weatherapp.Model.Weather;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView clouds,temp,name,wind;
    private String apikey = "6af963e47d1784807c357dc6b378db95";
    private String s = "Thessaloniki,gr";
    private RequestInterface requestInterface;
    private List<Weather> weatherList;
    private EditText editText;
    private Button button;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        clouds = findViewById(R.id.clouds);
        temp = findViewById(R.id.temperature);
        name = findViewById(R.id.name);
        wind = findViewById(R.id.wind);
        button=findViewById(R.id.button);
        editText=findViewById(R.id.city);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s=editText.getText().toString();
                loadJSON();
            }
        });


    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<OpenWeatherResponse> call = request.getWeather(s,apikey,"metric");
        call.enqueue(new Callback<OpenWeatherResponse>() {
            @Override
            public void onResponse(Call<OpenWeatherResponse> call, Response<OpenWeatherResponse> response) {

                if (!response.isSuccessful()){
                    Log.e("Code:", String.valueOf(+ response.code()));
                    return;
                }
                OpenWeatherResponse jsonResponse = response.body();
                assert jsonResponse != null;
                weatherList=jsonResponse.getWeather();
                Log.d(TAG, "onResponse: "+weatherList.get(0).getDescription());
                clouds.setText("description: "+weatherList.get(0).getDescription());
                temp.setText("temperature: "+jsonResponse.getMain().getTemp().toString()+" Celsius");
                name.setText(jsonResponse.getName());
                wind.setText("Wind Speed:"+jsonResponse.getWind().getSpeed().toString()+" m/s");


            }

            @Override
            public void onFailure(Call<OpenWeatherResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }
}

