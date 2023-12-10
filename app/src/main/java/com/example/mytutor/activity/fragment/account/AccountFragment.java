package com.example.mytutor.activity.fragment.account;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.mytutor.R;
import com.example.mytutor.activity.LoginActivity;
import com.example.mytutor.activity.fragment.account.sub.AccountEditFragment;
import com.example.mytutor.activity.fragment.account.sub.AccountInfoFragment;
import com.example.mytutor.api.ApiService;
import com.example.mytutor.api.request.ChangePasswordRequest;
import com.example.mytutor.api.request.RechargeRequest;
import com.example.mytutor.api.response.ResponseAPI;
import com.example.mytutor.databinding.FragmentAccountBinding;
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

public class AccountFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static FragmentAccountBinding binding;

    private final String TAG = "AccountFragment";

    private Dialog dialog;
    private Context context;

    public AccountFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);

        binding.navigationBarAccount.setItemSelected(R.id.itemAccountInfo, true);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameAccountContainer, new AccountInfoFragment()).commit();

        initListener();

        return binding.getRoot();
    }

    @SuppressLint("NonConstantResourceId")
    private void initListener() {
        binding.navigationBarAccount.setOnItemSelectedListener(i -> {
            Fragment fragment = null;
            switch (i) {
                case R.id.itemAccountInfo:
                    fragment = new AccountInfoFragment();
                    break;
                case R.id.itemAccountEdit:
                    fragment = new AccountEditFragment();
                    break;
                case R.id.itemChangePassword:
                    changePassword();
                    break;
                case R.id.itemRecharge:
                    recharge();
                    break;
                default:
                    Toast.makeText(context, "Màn không tồn tại", Toast.LENGTH_LONG).show();
                    break;
            }

            if (fragment != null) {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameAccountContainer, fragment).commit();
            }
        });
    }

    private void changePassword() {
        dialog = FunctionUtils.createDialog(context, R.layout.form_change_password);

        EditText edtOldPassword = dialog.findViewById(R.id.edtOldPassword);
        EditText edtNewPassword = dialog.findViewById(R.id.edtNewPassword);
        EditText edtConfirmNewPassword = dialog.findViewById(R.id.edtConfirmNewPassword);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        Button btnReset = dialog.findViewById(R.id.btnReset);

        btnConfirm.setOnClickListener(event -> {
            String oldPassword = edtOldPassword.getText().toString().trim();
            String newPassword = edtNewPassword.getText().toString().trim();
            String confirmPassword = edtConfirmNewPassword.getText().toString().trim();

            if (!SharedPreferenceUtil.getString(AppConstants.PASSWORD).equals(oldPassword)) {
                Toast.makeText(context, "Mật khẩu cũ không đúng!", Toast.LENGTH_SHORT).show();
            } else if (newPassword.equals(oldPassword)) {
                Toast.makeText(context, "Mật khẩu mới giống mật khẩu cũ!", Toast.LENGTH_SHORT).show();
            } else if (!confirmPassword.equals(newPassword)) {
                Toast.makeText(context, "Xác nhận mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
            } else {
                long time = System.currentTimeMillis();
                String accessToken = SharedPreferenceUtil.getString(AppConstants.ACCESS_TOKEN);

                String accountId = SharedPreferenceUtil.getString(AppConstants.ACCOUNT_ID);
                ChangePasswordRequest request = new ChangePasswordRequest(accountId, oldPassword, newPassword);
                Log.i(TAG, "Change password " + time + " request: " + request);

                ApiService.retrofit.changePassword(accessToken, request).enqueue(new Callback<ResponseAPI>() {
                    @Override
                    public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.i(TAG, "Change password " + time + " success");

                                SharedPreferenceUtil.clearLoggedAccountInfo();

                                dialog.cancel();

                                Intent intent = new Intent(context, LoginActivity.class);
                                intent.putExtra("notification", "Đổi mật khẩu thành công! \n Xin đăng nhập lại");
                                startActivity(intent);
                                requireActivity().finish();
                            } else {
                                String error = Objects.requireNonNull(response.errorBody()).string();

                                JsonObject errorResponse = new Gson().fromJson(error, JsonObject.class);

                                String errorMessage = errorResponse.get("message").getAsString();
                                Log.e(TAG, "Change password " + time + " failed: " + errorMessage);
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Change password " + time + " error: " + e.getMessage());
                            Log.getStackTraceString(e);
                            Toast.makeText(context, "Lỗi không xác định! \n Xin hãy thử lại sau", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAPI> call, Throwable t) {
                        Log.e(TAG, "Change password " + time + " error: " + t.getMessage());
                        Log.getStackTraceString(t);
                        Toast.makeText(context, "Lỗi không xác định! \n Xin hãy thử lại sau", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnReset.setOnClickListener(event -> {
            edtOldPassword.setText("");
            edtNewPassword.setText("");
            edtConfirmNewPassword.setText("");
        });

        dialog.show();
    }

    private void recharge() {
        dialog = FunctionUtils.createDialog(context, R.layout.form_recharge);

        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        EditText edtNumberMoney = dialog.findViewById(R.id.edtNumberMoney);

        btnConfirm.setOnClickListener(event -> {
            String strMoney = edtNumberMoney.getText().toString().trim();
            if (StringUtils.isBlank(strMoney)) {
                Toast.makeText(context, "Số tiền không hợp lệ!", Toast.LENGTH_SHORT).show();
            } else {
                long time = System.currentTimeMillis();

                String accessToken = SharedPreferenceUtil.getString(AppConstants.ACCESS_TOKEN);
                String accountId = SharedPreferenceUtil.getString(AppConstants.ACCOUNT_ID);
                Integer money = Integer.parseInt(strMoney);
                RechargeRequest request = new RechargeRequest(accountId, money);
                Log.i(TAG, "Recharge " + time + " request: " + request);

                ApiService.retrofit.recharge(accessToken, request).enqueue(new Callback<ResponseAPI>() {
                    @Override
                    public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                        try {
                            if (response.isSuccessful()) {
                                Log.i(TAG, "Recharge " + time + " success");

                                Integer oldMoney = SharedPreferenceUtil.getInteger(AppConstants.MONEY);
                                Integer newMoney = oldMoney + money;
                                SharedPreferenceUtil.setInteger(AppConstants.MONEY, newMoney);

                                AccountInfoFragment.txtBalance.setText(newMoney + " VNĐ");

                                dialog.cancel();
                            } else {
                                String error = Objects.requireNonNull(response.errorBody()).string();

                                JsonObject errorResponse = new Gson().fromJson(error, JsonObject.class);


                                String errorMessage = errorResponse.get("message").getAsString();
                                Log.e(TAG, "Recharge " + time + " failed: " + errorMessage);
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Recharge " + time + " error: " + e.getMessage());
                            Log.getStackTraceString(e);
                            Toast.makeText(context, "Lỗi không xác định! \n Xin hãy thử lại sau", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAPI> call, Throwable t) {
                        Log.e(TAG, "Recharge " + time + " error: " + t.getMessage());
                        Log.getStackTraceString(t);
                        Toast.makeText(context, "Lỗi không xác định! \n Xin hãy thử lại sau", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();
    }
}
