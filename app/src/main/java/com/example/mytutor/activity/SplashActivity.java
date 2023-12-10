package com.example.mytutor.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mytutor.R;
import com.example.mytutor.api.ApiService;
import com.example.mytutor.api.request.LoginRequest;
import com.example.mytutor.api.response.ResponseAPI;
import com.example.mytutor.model.Account;
import com.example.mytutor.utils.AppConstants;
import com.example.mytutor.utils.FunctionUtils;
import com.example.mytutor.utils.SharedPreferenceUtil;
import com.google.gson.Gson;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private final String TAG = "SplashActivity";
    private final Context context = SplashActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Objects.requireNonNull(getSupportActionBar()).hide();

        if (SharedPreferenceUtil.getBoolean(AppConstants.REMEMBER)) {
            doLogin();
        } else {
            SharedPreferenceUtil.clearLoggedAccountInfo();

            new Handler().postDelayed(() -> {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }, 1500L);
        }
    }

    private void doLogin() {
        long time = System.currentTimeMillis();

        String username = SharedPreferenceUtil.getString(AppConstants.USERNAME);
        String password = SharedPreferenceUtil.getString(AppConstants.PASSWORD);
        LoginRequest request = new LoginRequest(username, password);
        Log.i(TAG, "Login " + time + " request: " + request);

        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra("notification", "Lỗi không xác định! \n Xin hãy đăng nhập lại");

        ApiService.retrofit.login(request).enqueue(new Callback<ResponseAPI>() {
            @Override
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                try {
                    if (response.isSuccessful()) {
                        String strAccount = FunctionUtils.gson.toJson(response.body().getData());
                        Account loggedAccount = FunctionUtils.gson.fromJson(strAccount, Account.class);

                        Log.i(TAG, "Login " + time + " success " + loggedAccount);

                        SharedPreferenceUtil.saveLoggedAccountInfo(loggedAccount, password, true);

                        new Handler().postDelayed(() -> {
                            startActivity(new Intent(context, UserMainActivity.class));
                            finish();
                        }, 1500L);
                    } else {
                        Toast.makeText(context, "Lỗi không xác định! \n Xin hãy thử lại sau", Toast.LENGTH_SHORT).show();

                        new Handler().postDelayed(() -> {
                            startActivity(intent);
                            finish();
                        }, 1500L);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Login " + time + " error: " + e.getMessage());
                    Log.getStackTraceString(e);

                    new Handler().postDelayed(() -> {
                        startActivity(intent);
                        finish();
                    }, 1500L);
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
                Log.e(TAG, "Login " + time + " error: " + t.getMessage());
                Log.getStackTraceString(t);

                new Handler().postDelayed(() -> {
                    startActivity(intent);
                    finish();
                }, 1500L);
            }
        });
    }
}
