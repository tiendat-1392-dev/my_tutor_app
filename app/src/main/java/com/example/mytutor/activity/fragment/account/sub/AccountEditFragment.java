package com.example.mytutor.activity.fragment.account.sub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.mytutor.R;
import com.example.mytutor.api.ApiService;
import com.example.mytutor.api.request.ChangeInfoRequest;
import com.example.mytutor.api.response.ResponseAPI;
import com.example.mytutor.databinding.FragmentAccountEditBinding;
import com.example.mytutor.model.Account;
import com.example.mytutor.utils.AppConstants;
import com.example.mytutor.utils.FunctionUtils;
import com.example.mytutor.utils.SharedPreferenceUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountEditFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static FragmentAccountEditBinding binding;

    private final String TAG = "AccountEditFragment";
    private Context context;

    public AccountEditFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_edit, container, false);

        binding.edtName.setHint(SharedPreferenceUtil.getString(AppConstants.NAME));
        binding.edtDob.setHint((SharedPreferenceUtil.getString(AppConstants.DOB)));
        binding.edtPhone.setHint(SharedPreferenceUtil.getString(AppConstants.PHONE));
        if (SharedPreferenceUtil.getString(AppConstants.GENDER).equalsIgnoreCase("Nam")) {
            binding.radMale.setChecked(true);
        } else {
            binding.radFemale.setChecked(true);
        }

        initListener();

        return binding.getRoot();
    }

    private void initListener() {
        binding.btnConfirm.setOnClickListener(event -> {
            long time = System.currentTimeMillis();

            ChangeInfoRequest request = new ChangeInfoRequest();
            request.set_id(SharedPreferenceUtil.getString(AppConstants.ACCOUNT_ID));

            String newName = binding.edtName.getText().toString().trim();
            if (StringUtils.isNotBlank(newName)) {
                request.setName(newName);
            }

            String newPhone = binding.edtPhone.getText().toString().trim();
            if (StringUtils.isNotBlank(newPhone)) {
                Pattern patternPhone = Pattern.compile("^[0-9]*$");
                if (newPhone.length() < 10 || !patternPhone.matcher(newPhone).find()) {
                    Toast.makeText(context, "Số điện thoại mới không hợp lệ", Toast.LENGTH_SHORT).show();
                } else {
                    request.setPhone(newPhone);
                }
            }

            String newDob = binding.edtDob.getText().toString().trim();
            if (StringUtils.isNotBlank(newDob)) {
                request.setDob(newDob);
            }

            int selectedGender = binding.rdgGender.getCheckedRadioButtonId();
            RadioButton genderName = requireActivity().findViewById(selectedGender);
            request.setGender(FunctionUtils.getGenderValue(genderName.getText().toString()));

            Log.i(TAG, "Change info " + time + " request: " + request);

            String accessToken = SharedPreferenceUtil.getString(AppConstants.ACCESS_TOKEN);

            ApiService.retrofit.changeInfo(accessToken, request).enqueue(new Callback<ResponseAPI>() {
                @Override
                public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                    try {
                        if (response.isSuccessful()) {
                            String strAccount = FunctionUtils.gson.toJson(response.body().getData());
                            Account loggedAccount = FunctionUtils.gson.fromJson(strAccount, Account.class);
                            Log.i(TAG, "Change info " + time + " success: " + loggedAccount);

                            SharedPreferenceUtil.updateLoggedAccountInfo(loggedAccount);

                            requireActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frameAccountContainer, new AccountInfoFragment()).commit();
                        } else {
                            String error = Objects.requireNonNull(response.errorBody()).string();

                            JsonObject errorResponse = new Gson().fromJson(error, JsonObject.class);

                            String errorMessage = errorResponse.get("message").getAsString();
                            Log.e(TAG, "Change info " + time + " failed: " + errorMessage);
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Change info " + time + " error: " + e.getMessage());
                        Log.getStackTraceString(e);
                        Toast.makeText(context, "Lỗi không xác định! \n Xin hãy thử lại sau", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseAPI> call, Throwable t) {
                    Log.e(TAG, "Change info " + time + " error: " + t.getMessage());
                    Log.getStackTraceString(t);
                    Toast.makeText(context, "Lỗi không xác định! \n Xin hãy thử lại sau", Toast.LENGTH_SHORT).show();
                }
            });
        });

        binding.btnReset.setOnClickListener(event -> {
            binding.edtName.setText("");
            binding.edtDob.setText("");
            binding.edtPhone.setText("");
            if (SharedPreferenceUtil.getString(AppConstants.GENDER).equalsIgnoreCase("Nam")) {
                binding.radMale.setChecked(true);
            } else {
                binding.radFemale.setChecked(true);
            }
        });
    }
}
