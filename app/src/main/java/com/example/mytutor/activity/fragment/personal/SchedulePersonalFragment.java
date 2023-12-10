package com.example.mytutor.activity.fragment.personal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.mytutor.R;
import com.example.mytutor.activity.fragment.personal.sub.ScheduleActiveFragment;
import com.example.mytutor.activity.fragment.personal.sub.ScheduleCreateFragment;
import com.example.mytutor.activity.fragment.personal.sub.ScheduleWaitFragment;
import com.example.mytutor.databinding.FragmentSchedulePersonalBinding;

public class SchedulePersonalFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static FragmentSchedulePersonalBinding binding;

    private Context context;

    public SchedulePersonalFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContext();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_personal, container, false);

        binding.navigationBarCalender.setItemSelected(R.id.itemScheduleActive, true);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameScheduleContainer, new ScheduleActiveFragment()).commit();

        initListener();

        return binding.getRoot();
    }

    @SuppressLint("NonConstantResourceId")
    private void initListener() {
        binding.navigationBarCalender.setOnItemSelectedListener(i -> {
            Fragment fragment = null;
            switch (i) {
                case R.id.itemScheduleActive:
                    fragment = new ScheduleActiveFragment();
                    break;
                case R.id.itemScheduleWait:
                    fragment = new ScheduleWaitFragment();
                    break;
                case R.id.itemScheduleCreate:
                    fragment = new ScheduleCreateFragment();
                    break;
                default:
                    Toast.makeText(context, "Màn không tồn tại", Toast.LENGTH_LONG).show();
                    break;
            }

            if (fragment != null) {
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameScheduleContainer, fragment).commit();
            }
        });
    }
}
