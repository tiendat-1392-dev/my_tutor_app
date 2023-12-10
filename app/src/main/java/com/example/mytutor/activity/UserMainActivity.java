package com.example.mytutor.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.mytutor.R;
import com.example.mytutor.activity.fragment.account.AccountFragment;
import com.example.mytutor.activity.fragment.personal.SchedulePersonalFragment;
import com.example.mytutor.activity.fragment.register.ScheduleRegisterFragment;
import com.example.mytutor.utils.SharedPreferenceUtil;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.Objects;

public class UserMainActivity extends AppCompatActivity {
    private final String TAG = "UserMainActivity";
    private final Context context = UserMainActivity.this;

    private Button btnLogout;
    private ChipNavigationBar chipNavigationBar;

    private int backButtonCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        initView();

        initListener();
    }

    private void initView() {
        btnLogout = findViewById(R.id.btnLogout);

        chipNavigationBar = findViewById(R.id.navigationBarTutor);

        chipNavigationBar.setItemSelected(R.id.itemScheduleRegister, true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameContainer, new ScheduleRegisterFragment()).commit();
    }

    @SuppressLint("NonConstantResourceId")
    private void initListener() {
        btnLogout.setOnClickListener(event -> {
            SharedPreferenceUtil.clearLoggedAccountInfo();

            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("notification", "Đăng xuất tài khoản thành công!");
            startActivity(intent);
            finish();
        });

        chipNavigationBar.setOnItemSelectedListener(i -> {
            Fragment fragment = null;
            switch (i) {
                case R.id.itemScheduleRegister:
                    fragment = new ScheduleRegisterFragment();
                    break;
                case R.id.itemSchedulePersonal:
                    fragment = new SchedulePersonalFragment();
                    break;
                case R.id.itemAccount:
                    fragment = new AccountFragment();
                    break;
                default:
                    Toast.makeText(context, "Màn không tồn tại", Toast.LENGTH_SHORT).show();
                    break;
            }

            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameContainer, fragment).commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backButtonCount >= 1) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.putExtra("notification", "Đăng xuất tài khoản thành công!");
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Nhấn lần nữa để đăng xuất", Toast.LENGTH_SHORT).show();
            backButtonCount++;

            new Handler().postDelayed(() -> backButtonCount = 0, 2000);
        }
    }
}
