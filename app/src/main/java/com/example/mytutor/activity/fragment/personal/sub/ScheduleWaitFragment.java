package com.example.mytutor.activity.fragment.personal.sub;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytutor.R;
import com.example.mytutor.activity.adapter.ScheduleActiveAdapter;
import com.example.mytutor.activity.adapter.ScheduleRegisterAdapter;
import com.example.mytutor.activity.adapter.ScheduleWaitAdapter;
import com.example.mytutor.api.ApiService;
import com.example.mytutor.api.request.FindScheduleRequest;
import com.example.mytutor.api.request.MyScheduleRequest;
import com.example.mytutor.api.response.ResponseAPI;
import com.example.mytutor.databinding.FragmentScheduleListBinding;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleWaitFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static FragmentScheduleListBinding binding;

    private final String TAG = "ScheduleWaitFragment";

    private Dialog dialog;
    private Context context;
    private List<Subject> subjects;
    private List<Schedule> schedules;
    private EditText edtPrice, edtNumberSession;
    private ScheduleWaitAdapter scheduleWaitAdapter;
    private CheckBox cbMonday, cbTuesday, cbWednesday, cbThursday, cbFriday, cbSaturday, cbSunday;
    private Spinner spnSubject, spnTimeMonday, spnTimeTuesday, spnTimeWednesday, spnTimeThursday,
            spnTimeFriday, spnTimeSaturday, spnTimeSunday;

    public ScheduleWaitFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_list, container, false);

        binding.btnSearch.setOnClickListener(event -> createFormSearchSchedule());

        getListScheduleWait(new MyScheduleRequest(SharedPreferenceUtil.getString(AppConstants.ACCOUNT_ID)));

        String notification = requireActivity().getIntent().getStringExtra("notification");
        if (StringUtils.isNotBlank(notification)) {
            Toast.makeText(context, notification, Toast.LENGTH_LONG).show();
        }

        return binding.getRoot();
    }

    private void createFormSearchSchedule() {
        dialog = FunctionUtils.createDialog(context, R.layout.form_search_schedule);

        edtPrice = dialog.findViewById(R.id.edtPrice);
        edtNumberSession = dialog.findViewById(R.id.edtNumberSession);
        spnSubject = dialog.findViewById(R.id.spnSubject);

        cbMonday = dialog.findViewById(R.id.cbMonday);
        spnTimeMonday = dialog.findViewById(R.id.spnTimeMonday);
        cbTuesday = dialog.findViewById(R.id.cbTuesday);
        spnTimeTuesday = dialog.findViewById(R.id.spnTimeTuesday);
        cbWednesday = dialog.findViewById(R.id.cbWednesday);
        spnTimeWednesday = dialog.findViewById(R.id.spnTimeWednesday);
        cbThursday = dialog.findViewById(R.id.cbThursday);
        spnTimeThursday = dialog.findViewById(R.id.spnTimeThursday);
        cbFriday = dialog.findViewById(R.id.cbFriday);
        spnTimeFriday = dialog.findViewById(R.id.spnTimeFriday);
        cbSaturday = dialog.findViewById(R.id.cbSaturday);
        spnTimeSaturday = dialog.findViewById(R.id.spnTimeSaturday);
        cbSunday = dialog.findViewById(R.id.cbSunday);
        spnTimeSunday = dialog.findViewById(R.id.spnTimeSunday);

        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        Button btnReset = dialog.findViewById(R.id.btnReset);

        subjects = new ArrayList<>();
        FunctionUtils.getListSubject(context, subjects, spnSubject);

        ArrayAdapter<?> adapterTimeStudy =
                new ArrayAdapter<>(context, R.layout.item_spinner, FunctionUtils.getListTimeStudy());
        adapterTimeStudy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTimeMonday.setAdapter(adapterTimeStudy);
        spnTimeTuesday.setAdapter(adapterTimeStudy);
        spnTimeWednesday.setAdapter(adapterTimeStudy);
        spnTimeThursday.setAdapter(adapterTimeStudy);
        spnTimeFriday.setAdapter(adapterTimeStudy);
        spnTimeSaturday.setAdapter(adapterTimeStudy);
        spnTimeSunday.setAdapter(adapterTimeStudy);

        btnConfirm.setOnClickListener(event -> searchSchedule());

        btnReset.setOnClickListener(event -> resetForm());

        dialog.show();
    }

    private void searchSchedule() {
        MyScheduleRequest request = new MyScheduleRequest(SharedPreferenceUtil.getString(AppConstants.ACCOUNT_ID));

        int selectedSubject = spnSubject.getSelectedItemPosition();
        if (selectedSubject != 0) {
            request.setSubjectId(subjects.get(selectedSubject).getId());
        }

        String strPrice = edtPrice.getText().toString().trim();
        if (StringUtils.isNotBlank(strPrice)) {
            request.setPrice(Integer.parseInt(strPrice));
        }

        String strNumberSession = edtNumberSession.getText().toString().trim();
        if (StringUtils.isNotBlank(strNumberSession)) {
            request.setNumberSession(Integer.parseInt(strNumberSession));
        }

        List<Integer> days = new ArrayList<>();
        List<Integer> hours = new ArrayList<>();

        if (cbMonday.isChecked()) {
            int selectedTimeMonday = spnTimeMonday.getSelectedItemPosition();
            if (selectedTimeMonday == 0) {
                Toast.makeText(context, "Vui lòng chọn ca học thứ 2 muốn tìm", Toast.LENGTH_SHORT).show();
                return;
            }
            days.add(2);
            hours.add(selectedTimeMonday);
        }

        if (cbTuesday.isChecked()) {
            int selectedTimeTuesday = spnTimeTuesday.getSelectedItemPosition();
            if (selectedTimeTuesday == 0) {
                Toast.makeText(context, "Vui lòng chọn ca học thứ 3 muốn tìm", Toast.LENGTH_SHORT).show();
                return;
            }
            days.add(3);
            hours.add(selectedTimeTuesday);
        }

        if (cbWednesday.isChecked()) {
            int selectedTimeWednesday = spnTimeWednesday.getSelectedItemPosition();
            if (selectedTimeWednesday == 0) {
                Toast.makeText(context, "Vui lòng chọn ca học thứ 4 muốn tìm", Toast.LENGTH_SHORT).show();
                return;
            }
            days.add(4);
            hours.add(selectedTimeWednesday);
        }

        if (cbThursday.isChecked()) {
            int selectedTimeThursday = spnTimeThursday.getSelectedItemPosition();
            if (selectedTimeThursday == 0) {
                Toast.makeText(context, "Vui lòng chọn ca học thứ 5 muốn tìm", Toast.LENGTH_SHORT).show();
                return;
            }
            days.add(5);
            hours.add(selectedTimeThursday);
        }

        if (cbFriday.isChecked()) {
            int selectedTimeFriday = spnTimeFriday.getSelectedItemPosition();
            if (selectedTimeFriday == 0) {
                Toast.makeText(context, "Vui lòng chọn ca học thứ 6 muốn tìm", Toast.LENGTH_SHORT).show();
                return;
            }
            days.add(6);
            hours.add(selectedTimeFriday);
        }

        if (cbSaturday.isChecked()) {
            int selectedTimeSaturday = spnTimeSaturday.getSelectedItemPosition();
            if (selectedTimeSaturday == 0) {
                Toast.makeText(context, "Vui lòng chọn ca học thứ 7 muốn tìm", Toast.LENGTH_SHORT).show();
                return;
            }
            days.add(7);
            hours.add(selectedTimeSaturday);
        }

        if (cbSunday.isChecked()) {
            int selectedTimeSunday = spnTimeSunday.getSelectedItemPosition();
            if (selectedTimeSunday == 0) {
                Toast.makeText(context, "Vui lòng chọn ca học chủ nhật muốn tìm", Toast.LENGTH_SHORT).show();
                return;
            }
            days.add(8);
            hours.add(selectedTimeSunday);
        }

        request.setDays(days.isEmpty() ? null : days);
        request.setHours(hours.isEmpty() ? null : hours);

        getListScheduleWait(request);
    }

    private void getListScheduleWait(MyScheduleRequest request) {
        long time = System.currentTimeMillis();
        String accessToken = SharedPreferenceUtil.getString(AppConstants.ACCESS_TOKEN);

        schedules = new ArrayList<>();

        ApiService.retrofit.myRegister(accessToken, request).enqueue(new Callback<ResponseAPI>() {
            @Override
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                try {
                    if (response.isSuccessful()) {
                        Log.e(TAG, "My register " + time + " success");

                        String strListSchedule = FunctionUtils.gson.toJson(response.body().getData());
                        List<Map<String, Object>> tmp =
                                FunctionUtils.gson.fromJson(strListSchedule, List.class);
                        tmp.forEach(i -> schedules.add(FunctionUtils.convertToSchedule(i)));

                        binding.txtTotalSchedule.setText(schedules.size() + " lịch");

                        scheduleWaitAdapter = new ScheduleWaitAdapter(getContext(), schedules);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                        binding.revListSchedule.setLayoutManager(layoutManager);
                        binding.revListSchedule.setAdapter(scheduleWaitAdapter);

                        if (dialog != null) dialog.cancel();

                        scheduleWaitAdapter.setListener(schedule -> {
                            ScheduleInfoFragment fragment = new ScheduleInfoFragment(schedule, TAG);
                            requireActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.frameScheduleContainer, fragment)
                                    .commit();
                        });
                    } else {
                        String error = Objects.requireNonNull(response.errorBody()).string();

                        JsonObject errorResponse = new Gson().fromJson(error, JsonObject.class);

                        String errorMessage = errorResponse.get("message").getAsString();
                        Log.e(TAG, "My register " + time + " failed: " + errorMessage);
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "My register " + time + " error: " + e.getMessage());
                    Log.getStackTraceString(e);
                    Toast.makeText(getContext(), "Lỗi không xác định!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
                Log.e(TAG, "My register " + time + " error: " + t.getMessage());
                Log.getStackTraceString(t);
                Toast.makeText(getContext(), "Lỗi không xác định!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void resetForm() {
        edtPrice.setText("");
        edtNumberSession.setText("");
        spnSubject.setSelection(0);
        cbMonday.setChecked(false);
        spnTimeMonday.setSelection(0);
        cbTuesday.setChecked(false);
        spnTimeTuesday.setSelection(0);
        cbWednesday.setChecked(false);
        spnTimeWednesday.setSelection(0);
        cbThursday.setChecked(false);
        spnTimeThursday.setSelection(0);
        cbFriday.setChecked(false);
        spnTimeFriday.setSelection(0);
        cbSaturday.setChecked(false);
        spnTimeSaturday.setSelection(0);
        cbSunday.setChecked(false);
        spnTimeSunday.setSelection(0);
    }
}
