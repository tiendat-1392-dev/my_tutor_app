package com.example.mytutor.activity.fragment.register;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.mytutor.R;
import com.example.mytutor.activity.fragment.register.sub.ScheduleListFragment;
import com.example.mytutor.databinding.FragmentScheduleRegisterBinding;

public class ScheduleRegisterFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    private static FragmentScheduleRegisterBinding binding;

    public ScheduleRegisterFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule_register, container, false);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameScheduleContainer, new ScheduleListFragment()).commit();

        return binding.getRoot();
    }
}
