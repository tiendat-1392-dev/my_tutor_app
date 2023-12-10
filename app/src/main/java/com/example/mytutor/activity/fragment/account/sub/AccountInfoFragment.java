package com.example.mytutor.activity.fragment.account.sub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.mytutor.R;
import com.example.mytutor.databinding.FragmentAccountInfoBinding;
import com.example.mytutor.utils.AppConstants;
import com.example.mytutor.utils.SharedPreferenceUtil;

public class AccountInfoFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    public static TextView txtBalance;
    @SuppressLint("StaticFieldLeak")
    private static FragmentAccountInfoBinding binding;

    public AccountInfoFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account_info, container, false);

        initView();

        return binding.getRoot();
    }

    private void initView() {
        TextView txtName = binding.txtName;
        txtName.setText(SharedPreferenceUtil.getString(AppConstants.NAME));

        TextView txtGender = binding.txtGender;
        txtGender.setText(SharedPreferenceUtil.getString(AppConstants.GENDER));

        TextView txtDob = binding.txtDob;
        txtDob.setText((SharedPreferenceUtil.getString(AppConstants.DOB)));

        TextView txtPhone = binding.txtPhone;
        txtPhone.setText(SharedPreferenceUtil.getString(AppConstants.PHONE));

        TextView txtEmail = binding.txtEmail;
        txtEmail.setText(SharedPreferenceUtil.getString(AppConstants.EMAIL));

        TextView txtRole = binding.txtRole;
        txtRole.setText(SharedPreferenceUtil.getString(AppConstants.ROLE_NAME));

        txtBalance = binding.txtBalance;
        txtBalance.setText(SharedPreferenceUtil.getInteger(AppConstants.MONEY) + " VNĐ");

        TextView txtCreatedAt = binding.txtCreatedAt;
        txtCreatedAt.setText(SharedPreferenceUtil.getString(AppConstants.CREATED_AT));
    }
}
