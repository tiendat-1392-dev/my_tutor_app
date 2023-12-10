package com.example.mytutor.activity.fragment.personal.sub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.mytutor.R;
import com.example.mytutor.api.ApiService;
import com.example.mytutor.api.request.CreateScheduleRequest;
import com.example.mytutor.api.response.ResponseAPI;
import com.example.mytutor.databinding.FragmentScheduleCreateBinding;
import com.example.mytutor.activity.fragment.ScheduleInfoFragment;
import com.example.mytutor.model.Schedule;
import com.example.mytutor.model.Subject;
import com.example.mytutor.utils.AppConstants;
import com.example.mytutor.utils.FunctionUtils;
import com.example.mytutor.utils.SharedPreferenceUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleCreateFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static FragmentScheduleCreateBinding binding;

    private final String TAG = "ScheduleCreateFragment";

    private Context context;
    private List<Subject> subjects;

    public ScheduleCreateFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_create, container, false);

        initView();

        initListener();

        return binding.getRoot();
    }

    private void initView() {
        subjects = new ArrayList<>();
        FunctionUtils.getListSubject(context, subjects, binding.spnSubject);

        ArrayAdapter<?> adapterTimeStudy =
                new ArrayAdapter<>(context, R.layout.item_spinner, FunctionUtils.getListTimeStudy());
        adapterTimeStudy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spnTimeMonday.setAdapter(adapterTimeStudy);
        binding.spnTimeTuesday.setAdapter(adapterTimeStudy);
        binding.spnTimeWednesday.setAdapter(adapterTimeStudy);
        binding.spnTimeThursday.setAdapter(adapterTimeStudy);
        binding.spnTimeFriday.setAdapter(adapterTimeStudy);
        binding.spnTimeSaturday.setAdapter(adapterTimeStudy);
        binding.spnTimeSunday.setAdapter(adapterTimeStudy);
    }

    private void initListener() {
        binding.btnConfirm.setOnClickListener(event -> {
            createSchedule();
        });

        binding.btnReset.setOnClickListener(event -> {
            resetFormInfo();
        });
    }

    private void createSchedule() {
        int selectedSubject = binding.spnSubject.getSelectedItemPosition();
        if (selectedSubject == 0) {
            Toast.makeText(context, "Vui lòng chọn môn học", Toast.LENGTH_SHORT).show();
            return;
        }
        String idSelectedSubject = subjects.get(selectedSubject).getId();

        String strPrice = binding.edtPrice.getText().toString().trim();
        if (StringUtils.isBlank(strPrice)) {
            Toast.makeText(context, "Vui lòng nhập học phí", Toast.LENGTH_SHORT).show();
            return;
        }
        Integer price = Integer.valueOf(strPrice);

        String strNumberSession = binding.edtNumberSession.getText().toString().trim();
        if (StringUtils.isBlank(strNumberSession)) {
            Toast.makeText(context, "Vui lòng nhập số buổi học", Toast.LENGTH_SHORT).show();
            return;
        }
        Integer numberSession = Integer.valueOf(strNumberSession);

        List<Integer> days = new ArrayList<>();
        List<Integer> hours = new ArrayList<>();

        if (binding.cbMonday.isChecked()) {
            int selectedTimeMonday = binding.spnTimeMonday.getSelectedItemPosition();
            if (selectedTimeMonday == 0) {
                Toast.makeText(context, "Vui lòng chọn ca học thứ 2", Toast.LENGTH_SHORT).show();
                return;
            }
            days.add(2);
            hours.add(selectedTimeMonday);
        }

        if (binding.cbTuesday.isChecked()) {
            int selectedTimeTuesday = binding.spnTimeTuesday.getSelectedItemPosition();
            if (selectedTimeTuesday == 0) {
                Toast.makeText(context, "Vui lòng chọn ca học thứ 3", Toast.LENGTH_SHORT).show();
                return;
            }
            days.add(3);
            hours.add(selectedTimeTuesday);
        }

        if (binding.cbWednesday.isChecked()) {
            int selectedTimeWednesday = binding.spnTimeWednesday.getSelectedItemPosition();
            if (selectedTimeWednesday == 0) {
                Toast.makeText(context, "Vui lòng chọn ca học thứ 4", Toast.LENGTH_SHORT).show();
                return;
            }
            days.add(4);
            hours.add(selectedTimeWednesday);
        }

        if (binding.cbThursday.isChecked()) {
            int selectedTimeThursday = binding.spnTimeThursday.getSelectedItemPosition();
            if (selectedTimeThursday == 0) {
                Toast.makeText(context, "Vui lòng chọn ca học thứ 5", Toast.LENGTH_SHORT).show();
                return;
            }
            days.add(5);
            hours.add(selectedTimeThursday);
        }

        if (binding.cbFriday.isChecked()) {
            int selectedTimeFriday = binding.spnTimeFriday.getSelectedItemPosition();
            if (selectedTimeFriday == 0) {
                Toast.makeText(context, "Vui lòng chọn ca học thứ 6", Toast.LENGTH_SHORT).show();
                return;
            }
            days.add(6);
            hours.add(selectedTimeFriday);
        }

        if (binding.cbSaturday.isChecked()) {
            int selectedTimeSaturday = binding.spnTimeSaturday.getSelectedItemPosition();
            if (selectedTimeSaturday == 0) {
                Toast.makeText(context, "Vui lòng chọn ca học thứ 7", Toast.LENGTH_SHORT).show();
                return;
            }
            days.add(7);
            hours.add(selectedTimeSaturday);
        }

        if (binding.cbSunday.isChecked()) {
            int selectedTimeSunday = binding.spnTimeSunday.getSelectedItemPosition();
            if (selectedTimeSunday == 0) {
                Toast.makeText(context, "Vui lòng chọn ca học chủ nhật", Toast.LENGTH_SHORT).show();
                return;
            }
            days.add(8);
            hours.add(selectedTimeSunday);
        }

        if (days.isEmpty()) {
            Toast.makeText(context, "Vui lòng chọn ngày học", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = SharedPreferenceUtil.getString(AppConstants.ACCOUNT_ID);
        CreateScheduleRequest request =
                new CreateScheduleRequest(userId, idSelectedSubject, price, numberSession, days, hours);

        long time = System.currentTimeMillis();
        String accessToken = SharedPreferenceUtil.getString(AppConstants.ACCESS_TOKEN);

        ApiService.retrofit.createSchedule(accessToken, request).enqueue(new Callback<ResponseAPI>() {
            @Override
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                try {
                    if (response.isSuccessful()) {
                        Log.e(TAG, "Create Schedule " + time + " success");

                        Integer oldMoney = SharedPreferenceUtil.getInteger(AppConstants.MONEY);
                        Integer newMoney = oldMoney - price * 3;
                        SharedPreferenceUtil.setInteger(AppConstants.MONEY, newMoney);

                        Schedule schedule = new Schedule();
                        schedule.setSubject(subjects.get(selectedSubject));
                        schedule.setNumberSession(numberSession);
                        schedule.setPrice(price);
                        schedule.setDays(days);
                        schedule.setHours(hours);
                        schedule.setAccepted(false);
                        schedule.setType(SharedPreferenceUtil.getInteger(AppConstants.ROLE));
                        schedule.setCreatedAt(new Date());

                        ScheduleInfoFragment fragment = new ScheduleInfoFragment(schedule, TAG);
                        requireActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.frameScheduleContainer, fragment)
                                .commit();
                    } else {
                        String error = Objects.requireNonNull(response.errorBody()).string();

                        JsonObject errorResponse = new Gson().fromJson(error, JsonObject.class);

                        String errorMessage = errorResponse.get("message").getAsString();
                        Log.e(TAG, "Create Schedule " + time + " failed: " + errorMessage);
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Create Schedule " + time + " error: " + e.getMessage());
                    Log.getStackTraceString(e);
                    Toast.makeText(context, "Lỗi không xác định! \n Xin hãy thử lại sau", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
                Log.e(TAG, "Create Schedule " + time + " error: " + t.getMessage());
                Toast.makeText(context, "Lỗi không xác định! \n Xin hãy thử lại sau", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetFormInfo() {
        binding.edtPrice.setText("");
        binding.edtNumberSession.setText("");
        binding.spnSubject.setSelection(0);
        binding.cbMonday.setChecked(false);
        binding.spnTimeMonday.setSelection(0);
        binding.cbTuesday.setChecked(false);
        binding.spnTimeTuesday.setSelection(0);
        binding.cbWednesday.setChecked(false);
        binding.spnTimeWednesday.setSelection(0);
        binding.cbThursday.setChecked(false);
        binding.spnTimeThursday.setSelection(0);
        binding.cbFriday.setChecked(false);
        binding.spnTimeFriday.setSelection(0);
        binding.cbSaturday.setChecked(false);
        binding.spnTimeSaturday.setSelection(0);
        binding.cbSunday.setChecked(false);
        binding.spnTimeSunday.setSelection(0);
    }
}
