package com.example.mytutor.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.mytutor.R;
import com.example.mytutor.api.ApiService;
import com.example.mytutor.api.response.ResponseAPI;
import com.example.mytutor.model.Account;
import com.example.mytutor.model.Schedule;
import com.example.mytutor.model.Subject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FunctionUtils {
    private static final String TAG = "FunctionUtils";

    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public static Gson gson = new Gson();

    public static String getGenderName(Integer value) {
        if (value == null) return "Không xác định";
        return (value == 1) ? "Nam" : "Nữ";
    }

    public static Integer getGenderValue(String gender) {
        if (StringUtils.isBlank(gender)) return 0;
        return ("Nam".equalsIgnoreCase(gender)) ? 1 : 2;
    }

    public static String getRoleName(Integer value) {
        if (value == null) return "Không xác định";
        return (value == 1) ? "Người học" : "Gia sư";
    }

    public static Integer getRoleValue(String role) {
        if (StringUtils.isBlank(role)) return 0;
        return ("Người học".equalsIgnoreCase(role)) ? 1 : 2;
    }

    public static List<String> getListTimeStudy() {
        List<String> timeStudies = new ArrayList<>();
        timeStudies.add("Lựa chọn ca học");
        timeStudies.add("Ca 1 (9h - 10h30)");
        timeStudies.add("Ca 2 (9h30 - 11h)");
        timeStudies.add("Ca 3 (10h - 11h30)");
        timeStudies.add("Ca 4 (14h - 15h30)");
        timeStudies.add("Ca 5 (14h30 - 16h)");
        timeStudies.add("Ca 6 (15h - 16h30)");
        timeStudies.add("Ca 7 (15h30 - 17h)");
        timeStudies.add("Ca 8 (19h - 20h30)");
        timeStudies.add("Ca 9 (19h30 - 21h)");
        timeStudies.add("Ca 10 (20h - 21h30)");
        return timeStudies;
    }


    public static String getTimeStudy(int time) {
        String strTime = "";
        switch (time) {
            case 1:
                strTime = "(9h - 10h30)";
                break;
            case 2:
                strTime = "(9h30 - 11h)";
                break;
            case 3:
                strTime = "(10h - 11h30)";
                break;
            case 4:
                strTime = "(14h - 15h30)";
                break;
            case 5:
                strTime = "(14h30 - 16h)";
                break;
            case 6:
                strTime = "(15h - 16h30)";
                break;
            case 7:
                strTime = "(15h30 - 17h)";
                break;
            case 8:
                strTime = "(19h - 20h30)";
                break;
            case 9:
                strTime = "(19h30 - 21h)";
                break;
            case 10:
                strTime = "(20h - 21h30)";
                break;
            default:
                break;
        }
        return strTime;
    }

    public static Dialog createDialog(Context context, int idContentView) {
        // Khởi tạo Form
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(idContentView);
        dialog.setCancelable(true);

        // Khởi tạo background đằng sau
        Window window = dialog.getWindow();
        if (window == null) {
            return dialog;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER_VERTICAL;
        window.setAttributes(windowAttributes);

        return dialog;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static Schedule convertToSchedule(Map<String, Object> data) {
        Schedule schedule = new Schedule();

        String id = data.get("_id") == null ? "" : data.get("_id").toString();
        schedule.setId(id);

        if (data.get("tutor_id") != null) {
            String dataTutor = gson.toJson(data.get("tutor_id"));
            schedule.setTutor(gson.fromJson(dataTutor, Account.class));
        }

        if (data.get("student_id") != null) {
            String dataStudent = gson.toJson(data.get("student_id"));
            schedule.setStudent(gson.fromJson(dataStudent, Account.class));
        }

        if (data.get("subject_id") != null) {
            String dataSubject = gson.toJson(data.get("subject_id"));
            schedule.setSubject(gson.fromJson(dataSubject, Subject.class));
        }

        Integer numberSession = data.get("num_sessions") == null ? 0 : Double.valueOf(data.get("num_sessions").toString()).intValue();
        schedule.setNumberSession(numberSession);

        Integer price = data.get("price") == null ? 0 : Double.valueOf(data.get("price").toString()).intValue();
        schedule.setPrice(price);

        Integer type = data.get("type") == null ? 0 : Double.valueOf(data.get("type").toString()).intValue();
        schedule.setType(type);

        boolean isAccepted = data.get("is_accepted") != null && Boolean.parseBoolean(data.get("is_accepted").toString());
        schedule.setAccepted(isAccepted);

        if (data.get("createdAt") != null) {
            String dataCreateAt = gson.toJson(data.get("createdAt"));
            schedule.setCreatedAt(gson.fromJson(dataCreateAt, Date.class));
        }

        List<?> tmp;

        List<String> times = new ArrayList<>();
        if (data.get("time") != null) {
            tmp = gson.fromJson(data.get("time").toString(), List.class);
            tmp.forEach(i -> times.add(i.toString()));
        }
        schedule.setTimes(times);

        List<Integer> days = new ArrayList<>();
        if (data.get("day") != null) {
            tmp = gson.fromJson(data.get("day").toString(), List.class);
            tmp.forEach(i -> days.add(Double.valueOf(i.toString()).intValue()));
        }
        schedule.setDays(days);

        List<Integer> hours = new ArrayList<>();
        if (data.get("hour") != null) {
            tmp = gson.fromJson(data.get("hour").toString(), List.class);
            tmp.forEach(i -> hours.add(Double.valueOf(i.toString()).intValue()));
        }
        schedule.setHours(hours);

        return schedule;
    }

    public static void getListSubject(Context context, List<Subject> subjects, Spinner spnSubject) {
        // Lấy ra thời gian xử lý
        long time = System.currentTimeMillis();

        ApiService.retrofit.getListSubject().enqueue(new Callback<ResponseAPI>() {
            @Override
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onResponse(Call<ResponseAPI> call, Response<ResponseAPI> response) {
                try {
                    if (response.isSuccessful()) {
                        Log.e(TAG, "Get list subject " + time + " success");

                        // Tạo danh sách lưu môn học
                        subjects.add(new Subject("0", "Môn học"));

                        // Lấy ra kết quả danh sách môn học trả về
                        String strListSubject = FunctionUtils.gson.toJson(response.body().getData());
                        List<Map<String, String>> tmp =
                                FunctionUtils.gson.fromJson(strListSubject, List.class);
                        tmp.forEach(i -> subjects.add(new Subject(i.get("_id"), i.get("name"))));

                        // Lấy ra danh sách tên môn học
                        List<String> nameSubjects = new ArrayList<>();
                        subjects.forEach(i -> nameSubjects.add(i.getName()));

                        // Set danh sách môn học vào Spinner để lựa chọn
                        ArrayAdapter<?> adapterSubjects =
                                new ArrayAdapter<>(context, R.layout.item_spinner, nameSubjects);
                        adapterSubjects.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spnSubject.setAdapter(adapterSubjects);
                    } else {
                        // Lấy ra response lỗi
                        String error = Objects.requireNonNull(response.errorBody()).string();

                        // Convert từ String sang JsonObject
                        JsonObject errorResponse = new Gson().fromJson(error, JsonObject.class);

                        // Lấy ra message thông báo lỗi và hiển thị cho người dùng
                        String errorMessage = errorResponse.get("message").getAsString();
                        Log.e(TAG, "Get list subject " + time + " failed: " + errorMessage);
                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Get list subject " + time + " error: " + e.getMessage());
                    Log.getStackTraceString(e);
                    Toast.makeText(context, "Lỗi không xác định! \n Xin hãy thử lại sau", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAPI> call, Throwable t) {
                Log.e(TAG, "Get list subject " + time + " error: " + t.getMessage());
                Toast.makeText(context, "Lỗi không xác định! \n Xin hãy thử lại sau", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void createFormSearchSchedule(Context context, Dialog dialog, List<Subject> subjects) {
        // Khởi tạo FormSearchSchedule
        dialog = createDialog(context, R.layout.form_search_schedule);

        // Tìm kiếm và ánh xạ các thành phần trong FormSearchSchedule
        EditText edtPrice = dialog.findViewById(R.id.edtPrice);
        EditText edtNumberSession = dialog.findViewById(R.id.edtNumberSession);
        Spinner spnSubject = dialog.findViewById(R.id.spnSubject);
        CheckBox cbMonday = dialog.findViewById(R.id.cbMonday);
        Spinner spnTimeMonday = dialog.findViewById(R.id.spnTimeMonday);
        CheckBox cbTuesday = dialog.findViewById(R.id.cbTuesday);
        Spinner spnTimeTuesday = dialog.findViewById(R.id.spnTimeTuesday);
        CheckBox cbWednesday = dialog.findViewById(R.id.cbWednesday);
        Spinner spnTimeWednesday = dialog.findViewById(R.id.spnTimeWednesday);
        CheckBox cbThursday = dialog.findViewById(R.id.cbThursday);
        Spinner spnTimeThursday = dialog.findViewById(R.id.spnTimeThursday);
        CheckBox cbFriday = dialog.findViewById(R.id.cbFriday);
        Spinner spnTimeFriday = dialog.findViewById(R.id.spnTimeFriday);
        CheckBox cbSaturday = dialog.findViewById(R.id.cbSaturday);
        Spinner spnTimeSaturday = dialog.findViewById(R.id.spnTimeSaturday);
        CheckBox cbSunday = dialog.findViewById(R.id.cbSunday);
        Spinner spnTimeSunday = dialog.findViewById(R.id.spnTimeSunday);
        Button btnReset = dialog.findViewById(R.id.btnReset);

        // Lấy ra danh sách môn học và fill vào Spinner môn học để lựa chọn
        subjects = new ArrayList<>();
        getListSubject(context, subjects, spnSubject);

        // Lấy ra danh sách ca học và fill vào các Spinner lựa chọn ca học
        ArrayAdapter<?> adapterTimeStudy =
                new ArrayAdapter<>(context, R.layout.item_spinner, getListTimeStudy());
        adapterTimeStudy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTimeMonday.setAdapter(adapterTimeStudy);
        spnTimeTuesday.setAdapter(adapterTimeStudy);
        spnTimeWednesday.setAdapter(adapterTimeStudy);
        spnTimeThursday.setAdapter(adapterTimeStudy);
        spnTimeFriday.setAdapter(adapterTimeStudy);
        spnTimeSaturday.setAdapter(adapterTimeStudy);
        spnTimeSunday.setAdapter(adapterTimeStudy);

        // Khởi tạo sự kiện khi bấm nút nhập lại
        btnReset.setOnClickListener(event -> {
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
        });

        // Hiển thị FormSearchSchedule
        dialog.show();
    }
}
