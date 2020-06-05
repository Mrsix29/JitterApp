package com.example.jitterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SendJitterActivity extends AppCompatActivity {
    private static final String TAG = SendJitterActivity.class.getSimpleName();
    private EditText mNameEdit;
    private EditText mMessageEdit;
    private Button mSubmitButton;

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
        setContentView(R.layout.activity_send_jitter);

        //find all the views in the layout
        mNameEdit = findViewById(R.id.activity_send_jitter_name_edit);
        mMessageEdit = findViewById(R.id.activity_send_jitter_message_edit);
        mSubmitButton = findViewById(R.id.activity_send_jitter_send_button);

        //Add a click event handle to the submit button using a Java 8 lambda expression
/*        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mSubmitButton.setOnClickListener((View view) -> {

        });*/

        mSubmitButton.setOnClickListener((View view) -> {
            // Generate an implementation of the retrofit interface
            Retrofit retrofit = new Retrofit.Builder().baseUrl("http://www.youcode.ca").addConverterFactory(ScalarsConverterFactory.create()).build();
            YoucodeService youcodeService = retrofit.create(YoucodeService.class);

            // Call a method in your service
            String name = mNameEdit.getText().toString();
            String message = mMessageEdit.getText().toString();
            Call<String> getCall = youcodeService.postJitter(name, message);
            getCall.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Log.i(TAG, "Post was successful");

                    // Create all EditText fields
                    mNameEdit.setText("");
                    mMessageEdit.setText("");
                    Toast.makeText(SendJitterActivity.this, "Post was successful", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e(TAG, "Post was not successful");
                }
            });
        });


    }
}
