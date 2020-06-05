package com.example.jitterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView mJitterText;
    private static final String TAG = MainActivity.class.getSimpleName(); // simpleName = "MainActivity"


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_list_jitters:
                Intent listJittersIntent = new Intent(this, MainActivity.class);
                startActivity(listJittersIntent);
                return  true;
            case R.id.menu_item_post_jitter:
                Intent postJittersIntent = new Intent(this, SendJitterActivity.class);
                startActivity(postJittersIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find the textview in the layout
        mJitterText = findViewById(R.id.jitter_text);

        // Generate an implementation of the retrofit interface
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.youcode.ca").addConverterFactory(ScalarsConverterFactory.create()).build();
        YoucodeService youcodeService = retrofit.create(YoucodeService.class);

        // Call a method in your service
        Call<String> getCall = youcodeService.listJSONServlet();
        getCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i(TAG, "success getting data");
                mJitterText.setText(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "failure to get data");
            }
        });
    }
}
