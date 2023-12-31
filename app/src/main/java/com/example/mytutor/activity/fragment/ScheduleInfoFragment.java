package com.example.mytutor.activity.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.mytutor.R;
import com.example.mytutor.activity.fragment.personal.sub.ScheduleActiveFragment;
import com.example.mytutor.activity.fragment.personal.sub.ScheduleCreateFragment;
import com.example.mytutor.activity.fragment.personal.sub.ScheduleWaitFragment;
import com.example.mytutor.activity.fragment.register.sub.ScheduleListFragment;
import com.example.mytutor.api.ApiService;
import com.example.mytutor.api.request.AcceptScheduleRequest;
import com.example.mytutor.api.request.RemoveScheduleRequest;
import com.example.mytutor.api.response.ResponseAPI;
import com.example.mytutor.databinding.FragmentScheduleInfoBinding;
import com.example.mytutor.model.Account;
import com.example.mytutor.model.Schedule;
import com.example.mytutor.utils.AppConstants;
import com.example.mytutor.utils.FunctionUtils;
import com.example.mytutor.utils.SharedPreferenceUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleInfoFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static FragmentScheduleInfoBinding binding;

    private final String TAG = "ScheduleInfoFragment";

    private Schedule schedule;
    private String fromFragment;

    public ScheduleInfoFragment() {
    }

    public ScheduleInfoFragment(Schedule schedule, String fromFragment) {
        this.schedule = schedule;
        this.fromFragment = fromFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_info, container, false);

        initView();

        initListener();

        return binding.getRoot();
    }

    private void initView() {
        showPartnerInfo();

        binding.txtSubject.setText(schedule.getSubject().getName());

        binding.txtNumberSession.setText(schedule.getNumberSession() + " buổi");

        binding.txtPrice.setText(schedule.getPrice() + " VNĐ / 1 buổi");

        binding.txtAddress.setText(schedule.getAddress());

        binding.txtCreatedAt.setText(FunctionUtils.sdf.format(schedule.getCreatedAt()));

        String state = schedule.isAccepted() ? "Đã được nhận" : "Chờ người nhận";
        binding.txtState.setText(state);

        List<Integer> days = schedule.getDays();
        List<Integer> hours = schedule.getHours();
        for (int i = 0; i < days.size(); i++) {
            Integer day = days.get(i);
            switch (day) {
                case 2:
                    binding.txtMonday.setVisibility(View.VISIBLE);
                    binding.txtMonday.setText("Thứ 2 - " + FunctionUtils.getTimeStudy(hours.get(i)));
                    break;
                case 3:
                    binding.txtTuesday.setVisibility(View.VISIBLE);
                    binding.txtTuesday.setText("Thứ 3 - " + FunctionUtils.getTimeStudy(hours.get(i)));
                    break;
                case 4:
                    binding.txtWednesday.setVisibility(View.VISIBLE);
                    binding.txtWednesday.setText("Thứ 4 - " + FunctionUtils.getTimeStudy(hours.get(i)));
                    break;
                case 5:
                    binding.txtThursday.setVisibility(View.VISIBLE);
                    binding.txtThursday.setText("Thứ 5 - " + FunctionUtils.getTimeStudy(hours.get(i)));
                    break;
                case 6:
                    binding.txtFriday.setVisibility(View.VISIBLE);
                    binding.txtFriday.setText("Thứ 6 - " + FunctionUtils.getTimeStudy(hours.get(i)));
                    break;
                case 7:
                    binding.txtSaturday.setVisibility(View.VISIBLE);
                    binding.txtSaturday.setText("Thứ 7 - " + FunctionUtils.getTimeStudy(hours.get(i)));
                    break;
                case 8:
                    binding.txtSunday.setVisibility(View.VISIBLE);
                    binding.txtSunday.setText("Chủ nhật - " + FunctionUtils.getTimeStudy(hours.get(i)));
                    break;
                default:
                    break;
            }
        }
    }

    private void showPartnerInfo() {
        Account account;
        if (SharedPreferenceUtil.getInteger(AppConstants.ROLE) == 2) {
            account = schedule.getStudent();
        } else {
            account = schedule.getTutor();
        }

        switch (fromFragment) {
            case "ScheduleListFragment":
                binding.txtName.setText(account.getName());
                binding.txtGender.setText(FunctionUtils.getGenderName(account.getGender()));
                binding.txtPhone.setText(account.getPhone());
                binding.txtEmail.setText(account.getEmail());
                binding.btnDelete.setVisibility(View.GONE);
                break;
            case "ScheduleActiveFragment":
                if (SharedPreferenceUtil.getInteger(AppConstants.ROLE).equals(schedule.getType())) {
                    binding.titleName.setText("Người nhận:");
                }
                binding.txtName.setText(account.getName());
                binding.txtGender.setText(FunctionUtils.getGenderName(account.getGender()));
                binding.txtPhone.setText(account.getPhone());
                binding.txtEmail.setText(account.getEmail());
                binding.btnRegister.setVisibility(View.GONE);
                binding.btnDelete.setVisibility(View.GONE);
                break;
            default:
                binding.llName.setVisibility(View.GONE);
                binding.line1.setVisibility(View.GONE);
                binding.llGender.setVisibility(View.GONE);
                binding.line2.setVisibility(View.GONE);
                binding.llPhone.setVisibility(View.GONE);
                binding.line3.setVisibility(View.GONE);
                binding.llEmail.setVisibility(View.GONE);
                binding.line4.setVisibility(View.GONE);
                binding.btnRegister.setVisibility(View.GONE);
                break;
        }
    }

    private void initListener() {
        binding.btnRegister.setOnClickListener(event -> {
            registerSchedule();
        });

        binding.btnDelete.setOnClickListener(event -> {
            removeSchedule();
        });

        binding.btnBack.setOnClickListener(event -> {
            backToFragmentBefore();
        });
    }

    private void registerSchedule() {
        long time = System.currentTimeMillis();
        String accessToken = SharedPreferenceUtil.getString(AppConstants.ACCESS_TOKEN);

        AcceptScheduleRequest request = new AcceptScheduleRequest();
        request.setUser_id(SharedPreferenceUtil.getString(AppConstants.ACCOUNT_ID));
        request.setSchedule_id(schedule.getId());

        ApiService.retrofit.acceptSchedule(accessToken, request).enqueue(new Callback<ResponseAPI>() {
            @Override
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                try {
                    if (response.isSuccessful()) {
                        Log.e(TAG, "Accept schedule " + time + " success");

                        Toast.makeText(getContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();

                        binding.btnRegister.setVisibility(View.GONE);

                        binding.txtState.setText("Đã được nhận");

                        Integer oldMoney = SharedPreferenceUtil.getInteger(AppConstants.MONEY);
                        Integer newMoney = oldMoney - schedule.getPrice() * 3;
                        SharedPreferenceUtil.setInteger(AppConstants.MONEY, newMoney);
                    } else {
                        String error = Objects.requireNonNull(response.errorBody()).string();

                        JsonObject errorResponse = new Gson().fromJson(error, JsonObject.class);

                        String errorMessage = errorResponse.get("message").getAsString();
                        Log.e(TAG, "Accept schedule " + time + " failed: " + errorMessage);
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Accept schedule " + time + " error: " + e.getMessage());
                    Log.getStackTraceString(e);
                    Toast.makeText(getContext(), "Lỗi không xác định!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
                Log.e(TAG, "Accept schedule " + time + " error: " + t.getMessage());
                Log.getStackTraceString(t);
                Toast.makeText(getContext(), "Lỗi không xác định!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeSchedule() {
        long time = System.currentTimeMillis();
        String accessToken = SharedPreferenceUtil.getString(AppConstants.ACCESS_TOKEN);

        RemoveScheduleRequest request = new RemoveScheduleRequest(schedule.getId());

        ApiService.retrofit.removeSchedule(accessToken, request).enqueue(new Callback<ResponseAPI>() {
            @Override
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                try {
                    if (response.isSuccessful()) {
                        Log.e(TAG, "Remove schedule " + time + " success");

                        Toast.makeText(getContext(), "Xóa lịch thành công!", Toast.LENGTH_SHORT).show();

                        Integer oldMoney = SharedPreferenceUtil.getInteger(AppConstants.MONEY);
                        Integer newMoney = oldMoney + schedule.getPrice() * 3;
                        SharedPreferenceUtil.setInteger(AppConstants.MONEY, newMoney);

                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameScheduleContainer, new ScheduleWaitFragment())
                                .commit();
                    } else {
                        String error = Objects.requireNonNull(response.errorBody()).string();

                        JsonObject errorResponse = new Gson().fromJson(error, JsonObject.class);

                        String errorMessage = errorResponse.get("message").getAsString();
                        Log.e(TAG, "Remove schedule " + time + " failed: " + errorMessage);
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Remove schedule " + time + " error: " + e.getMessage());
                    Log.getStackTraceString(e);
                    Toast.makeText(getContext(), "Lỗi không xác định!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
                Log.e(TAG, "Remove schedule " + time + " error: " + t.getMessage());
                Log.getStackTraceString(t);
                Toast.makeText(getContext(), "Lỗi không xác định!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void backToFragmentBefore() {
        if (fromFragment.equals("ScheduleListFragment")) {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameScheduleContainer, new ScheduleListFragment())
                    .commit();
        } else if (fromFragment.equals("ScheduleActiveFragment")) {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameScheduleContainer, new ScheduleActiveFragment())
                    .commit();
        } else if (fromFragment.equals("ScheduleWaitFragment")) {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameScheduleContainer, new ScheduleWaitFragment())
                    .commit();
        } else if (fromFragment.equals("ScheduleCreateFragment")) {
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameScheduleContainer, new ScheduleCreateFragment())
                    .commit();
        }
    }
}
