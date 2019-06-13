package com.example.warehouse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.warehouse.Model.AccessToken;
import com.example.warehouse.Network.RetrofitBuilder;
import com.example.warehouse.Network.UserService;
import com.example.warehouse.TokenManager.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    EditText edit_email, edit_password;
    Button btn_login;
    TokenManager tokenManager;
    UserService service;
    Call<AccessToken> call;
    SharedPreferences preferences;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);
        btn_login = findViewById(R.id.btn_login);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        tokenManager = TokenManager.getInstance(getSharedPreferences("preferences", MODE_PRIVATE));
        service = RetrofitBuilder.getRetrofit().create(UserService.class);

        btn_login.setOnClickListener(v -> {
                String email = edit_email.getText().toString();
                String password = edit_password.getText().toString();
                call = service.login(email, password);
                call.enqueue(new Callback<AccessToken>() {
                    @Override
                    public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                        if(response.isSuccessful()){
                            if(response.body().getCode() == 401){
                                Log.d(TAG, "Error: " + response.errorBody());
                                Toast.makeText(MainActivity.this, "Unauthorized", Toast.LENGTH_LONG).show();
                            }else{
                                tokenManager.saveToken(response.body());
                                Toast.makeText(MainActivity.this, "Success Login", Toast.LENGTH_LONG).show();
                                Log.w(TAG, "onResponse: " + response.body().getRole() );
                                preferences.edit().putString("role", response.body().getRole()).commit();
                                Intent i = new Intent(MainActivity.this, ChooseWarehouseActivity.class);
                                startActivity(i);
                                Log.d(TAG, "Token: " + response.body().getAccessToken());
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<AccessToken> call, Throwable t) {
                        Log.d(TAG, "onError: " + t.getMessage());
                        Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        });
    }
}
