package com.example.mytutor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";
    private final Context context = LoginActivity.this;

    private Button btnLogin;
    private CheckBox cbRemember;
    private TextView txtRegister;
    private EditText edtUsername, edtPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();

        initView();

        initListener();

        String notification = getIntent().getStringExtra("notification");
        if (StringUtils.isNotBlank(notification)) {
            Toast.makeText(context, notification, Toast.LENGTH_LONG).show();
        }
    }

    private void initView() {
        btnLogin = findViewById(R.id.btnLogin);

        cbRemember = findViewById(R.id.cbRemember);
        Boolean remember = SharedPreferenceUtil.getBoolean(AppConstants.REMEMBER);

        txtRegister = findViewById(R.id.txtRegister);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
    }

    private void initListener() {
        btnLogin.setOnClickListener(v -> {
            long time = System.currentTimeMillis();

            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            boolean autoLogin = cbRemember.isChecked();

            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                Toast.makeText(context, "Vui lòng nhập đầy đủ username và password", Toast.LENGTH_SHORT).show();
            } else {
                LoginRequest request = new LoginRequest(username, password);
                Log.i(TAG, "Login " + time + " request: " + request);

                ApiService.retrofit.login(request).enqueue(new Callback<ResponseAPI>() {
                    @Override
                    public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                        try {
                            if (response.isSuccessful()) {
                                String strAccount = FunctionUtils.gson.toJson(response.body().getData());
                                Account loggedAccount = FunctionUtils.gson.fromJson(strAccount, Account.class);

                                Log.i(TAG, "Login " + time + " success " + loggedAccount);

                                SharedPreferenceUtil.saveLoggedAccountInfo(loggedAccount, password, autoLogin);

                                startActivity(new Intent(context, UserMainActivity.class));
                            } else {
                                String error = Objects.requireNonNull(response.errorBody()).string();

                                JsonObject errorResponse = new Gson().fromJson(error, JsonObject.class);

                                String errorMessage = errorResponse.get("message").getAsString();
                                Log.e(TAG, "Login " + time + " failed: " + errorMessage);
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Login " + time + " error: " + e.getMessage());
                            Log.getStackTraceString(e);
                            Toast.makeText(context, "Lỗi không xác định! \n Xin hãy thử lại sau", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAPI> call, Throwable t) {
                        Log.e(TAG, "Login " + time + " error: " + t.getMessage());
                        Log.getStackTraceString(t);
                        Toast.makeText(context, "Lỗi không xác định! \n Xin hãy thử lại sau", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        txtRegister.setOnClickListener(view -> {
            Intent intent = new Intent(context, RegisterActivity.class);
            startActivity(intent);
        });
    }
}
