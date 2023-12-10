package com.example.mytutor.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytutor.R;
import com.example.mytutor.api.ApiService;
import com.example.mytutor.api.request.AccountRegisterRequest;
import com.example.mytutor.api.response.ResponseAPI;
import com.example.mytutor.utils.FunctionUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private final String TAG = "RegisterActivity";
    private final Context context = RegisterActivity.this;

    private Button btnRegister;
    private RadioGroup rdgRole, rdgGender;
    private EditText edtUsername, edtPassword, edtConfirmPassword, edtName, edtDob, edtPhoneNumber, edtEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Objects.requireNonNull(getSupportActionBar()).hide();

        initView();

        initListener();
    }

    private void initView() {
        btnRegister = findViewById(R.id.btnRegister);

        rdgRole = findViewById(R.id.rdgRole);
        rdgGender = findViewById(R.id.rdgGender);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        edtName = findViewById(R.id.edtName);
        edtDob = findViewById(R.id.edtDob);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtEmail = findViewById(R.id.edtEmail);
    }

    private void initListener() {
        btnRegister.setOnClickListener(event -> {
            int selectedRole = rdgRole.getCheckedRadioButtonId();
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();
            String confirmPassword = edtConfirmPassword.getText().toString().trim();
            String name = edtName.getText().toString().trim();
            int selectedGender = rdgGender.getCheckedRadioButtonId();
            String dob = edtDob.getText().toString().trim();
            String phone = edtPhoneNumber.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();

            String error = validateRegisterRequest(
                    username, password, confirmPassword, selectedRole, name, selectedGender, dob, phone, email);
            if (StringUtils.isNotBlank(error)) {
                Toast.makeText(context, error, Toast.LENGTH_LONG).show();
            } else {
                long time = System.currentTimeMillis();

                RadioButton roleName = findViewById(selectedRole);
                Integer role = FunctionUtils.getRoleValue(roleName.getText().toString());

                RadioButton genderName = findViewById(selectedGender);
                Integer gender = FunctionUtils.getGenderValue(genderName.getText().toString());

                AccountRegisterRequest request = new AccountRegisterRequest(username, password, role, name, gender, dob, phone, email);
                Log.i(TAG, "Register " + time + " request: " + request);

                ApiService.retrofit.register(request).enqueue(new Callback<ResponseAPI>() {
                    @Override
                    public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.i(TAG, "Register " + time + " success ");

                                Intent intent = new Intent(context, LoginActivity.class);
                                intent.putExtra("notification", "Đăng ký tài khoản thành công!");
                                startActivity(intent);
                                finish();
                            } else {
                                String error = Objects.requireNonNull(response.errorBody()).string();

                                JsonObject errorResponse = new Gson().fromJson(error, JsonObject.class);

                                String errorMessage = errorResponse.get("message").getAsString();
                                Log.e(TAG, "Register " + time + " failed: " + errorMessage);
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Register " + time + " error: " + e.getMessage());
                            Log.getStackTraceString(e);
                            Toast.makeText(context, "Lỗi không xác định! \n Xin hãy thử lại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAPI> call, Throwable t) {
                        Log.e(TAG, "Register " + time + " error: " + t.getMessage());
                        Log.getStackTraceString(t);
                        Toast.makeText(context, "Lỗi không xác định! \n Xin hãy thử lại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private String validateRegisterRequest(
            String username, String password, String confirmPassword, int selectedRole, String name,
            int selectedGender, String dob, String phoneNumber, String email) {
        Pattern patternPhone = Pattern.compile("^[0-9]*$");
        Pattern patternEmail = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Pattern patternPassword = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d@$!%*#?&]{6,}$");

        if (StringUtils.isBlank(username)
                || StringUtils.isBlank(password)
                || StringUtils.isBlank(confirmPassword)
                || selectedRole == -1
                || StringUtils.isBlank(name)
                || selectedGender == -1
                || StringUtils.isBlank(dob)
                || StringUtils.isBlank(phoneNumber)
                || StringUtils.isBlank(email)) {
            return "Vui lòng điền đẩy đủ thông tin trên form";
        }

        if (username.length() < 5) {
            return "Tên đăng nhập không hợp lệ!";
        }

        if (!patternPassword.matcher(password).find()) {
            return "Mật khẩu không hợp lệ!";
        }

        if (!password.equals(confirmPassword)) {
            return "Xác nhận mật khẩu không đúng!";
        }

        if (phoneNumber.length() < 10 || !patternPhone.matcher(phoneNumber).find()) {
            return "Số điện thoại không hợp lệ!";
        }

        if (!patternEmail.matcher(email).find()) {
            return "Email không hợp lệ!";
        }

        return "";
    }
}
