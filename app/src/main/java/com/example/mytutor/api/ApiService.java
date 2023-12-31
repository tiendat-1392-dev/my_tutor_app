package com.example.mytutor.api;

import com.example.mytutor.api.request.AcceptScheduleRequest;
import com.example.mytutor.api.request.AccountRegisterRequest;
import com.example.mytutor.api.request.ChangeInfoRequest;
import com.example.mytutor.api.request.ChangePasswordRequest;
import com.example.mytutor.api.request.CreateScheduleRequest;
import com.example.mytutor.api.request.FindScheduleRequest;
import com.example.mytutor.api.request.LoginRequest;
import com.example.mytutor.api.request.MyScheduleRequest;
import com.example.mytutor.api.request.RechargeRequest;
import com.example.mytutor.api.request.RemoveScheduleRequest;
import com.example.mytutor.api.response.ResponseAPI;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface ApiService {
    String DOMAIN = "http://192.168.13.123:5000/api/";

    ApiService retrofit = new Retrofit.Builder()
            .baseUrl(DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .build()
            .create(ApiService.class);

    @POST("auth/login")
    Call<ResponseAPI> login(@Body LoginRequest request);

    @POST("auth/register")
    Call<ResponseAPI> register(@Body AccountRegisterRequest account);

    @PATCH("users/cash")
    Call<ResponseAPI> recharge(@Header("Authorization") String accessToken, @Body RechargeRequest request);

    @PATCH("users/change-password")
    Call<ResponseAPI> changePassword(@Header("Authorization") String accessToken, @Body ChangePasswordRequest request);

    @PATCH("users/change-info")
    Call<ResponseAPI> changeInfo(@Header("Authorization") String accessToken, @Body ChangeInfoRequest request);

    @GET("subjects")
    Call<ResponseAPI> getListSubject();

    @POST("schedules/create")
    Call<ResponseAPI> createSchedule(@Header("Authorization") String accessToken, @Body CreateScheduleRequest request);

    @POST("schedules/my-register")
    Call<ResponseAPI> myRegister(@Header("Authorization") String accessToken, @Body MyScheduleRequest request);

    @POST("schedules/my-schedule")
    Call<ResponseAPI> mySchedule(@Header("Authorization") String accessToken, @Body MyScheduleRequest request);

    @POST("schedules/find")
    Call<ResponseAPI> findSchedule(@Body FindScheduleRequest request);

    @POST("schedules/accept")
    Call<ResponseAPI> acceptSchedule(@Header("Authorization") String accessToken, @Body AcceptScheduleRequest request);

    @POST("schedules/remove-schedule")
    Call<ResponseAPI> removeSchedule(@Header("Authorization") String accessToken, @Body RemoveScheduleRequest request);
}
