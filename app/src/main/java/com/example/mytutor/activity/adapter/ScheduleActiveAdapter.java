package com.example.mytutor.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytutor.R;
import com.example.mytutor.model.Schedule;
import com.example.mytutor.utils.AppConstants;
import com.example.mytutor.utils.FunctionUtils;
import com.example.mytutor.utils.SharedPreferenceUtil;

import java.util.List;

public class ScheduleActiveAdapter extends RecyclerView.Adapter<ScheduleActiveAdapter.ViewHolder> {

    private final Context context;
    private List<Schedule> schedules;
    private IOnClickItemSchedule listener;

    public ScheduleActiveAdapter(Context context, List<Schedule> schedules) {
        this.context = context;
        this.schedules = schedules;
    }

    public void setListener(IOnClickItemSchedule listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ScheduleActiveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleActiveAdapter.ViewHolder holder, int position) {
        Schedule schedule = schedules.get(position);

        // Nếu lịch do tài khoản đăng thì hiển thị thông tin người nhận
        // Thay đổi tiêu đề từ Đăng bởi thành Nhận bởi
        if (SharedPreferenceUtil.getInteger(AppConstants.ROLE).equals(schedule.getType())) {
            holder.titleName.setText("Nhận bởi:");
        }
        // Hiển thị tên người đăng hoặc người nhận
        if (SharedPreferenceUtil.getInteger(AppConstants.ROLE) == 2) {
            holder.txtName.setText(schedule.getStudent().getName());
        } else {
            holder.txtName.setText(schedule.getTutor().getName());
        }

        // Hiển thị tên môn học
        holder.txtSubject.setText(schedule.getSubject().getName());

        // Hiển thị số buổi học
        holder.txtNumberSession.setText(schedule.getNumberSession() + " buổi");

        // Hiển thị học phí
        holder.txtPrice.setText(schedule.getPrice() + " VNĐ / 1 buổi");

        // Hiển thị lịch học
        List<Integer> days = schedule.getDays();
        List<Integer> hours = schedule.getHours();
        for (int i = 0; i < days.size(); i++) {
            Integer day = days.get(i);
            switch (day) {
                case 2:
                    holder.txtMonday.setVisibility(View.VISIBLE);
                    holder.txtMonday.setText("Thứ 2 - " + FunctionUtils.getTimeStudy(hours.get(i)));
                    break;
                case 3:
                    holder.txtTuesday.setVisibility(View.VISIBLE);
                    holder.txtTuesday.setText("Thứ 3 - " + FunctionUtils.getTimeStudy(hours.get(i)));
                    break;
                case 4:
                    holder.txtWednesday.setVisibility(View.VISIBLE);
                    holder.txtWednesday.setText("Thứ 4 - " + FunctionUtils.getTimeStudy(hours.get(i)));
                    break;
                case 5:
                    holder.txtThursday.setVisibility(View.VISIBLE);
                    holder.txtThursday.setText("Thứ 5 - " + FunctionUtils.getTimeStudy(hours.get(i)));
                    break;
                case 6:
                    holder.txtFriday.setVisibility(View.VISIBLE);
                    holder.txtFriday.setText("Thứ 6 - " + FunctionUtils.getTimeStudy(hours.get(i)));
                    break;
                case 7:
                    holder.txtSaturday.setVisibility(View.VISIBLE);
                    holder.txtSaturday.setText("Thứ 7 - " + FunctionUtils.getTimeStudy(hours.get(i)));
                    break;
                case 8:
                    holder.txtSunday.setVisibility(View.VISIBLE);
                    holder.txtSunday.setText("Chủ nhật - " + FunctionUtils.getTimeStudy(hours.get(i)));
                    break;
                default:
                    break;
            }
        }

        holder.llItemMySchedule.setOnClickListener(event -> listener.onItemClick(schedule));
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setSchedules(List<Schedule> list) {
        schedules = list;
        notifyDataSetChanged();
    }

    public interface IOnClickItemSchedule {
        void onItemClick(Schedule schedule);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button btnRegister;
        LinearLayout llItemMySchedule;
        TextView titleName, txtName, txtSubject, txtNumberSession, txtPrice, txtMonday, txtTuesday, txtWednesday,
                txtThursday, txtFriday, txtSaturday, txtSunday;

        public ViewHolder(View itemView) {
            super(itemView);

            // Ẩn nút đăng ký
            btnRegister = itemView.findViewById(R.id.btnRegister);
            btnRegister.setVisibility(View.GONE);

            titleName = itemView.findViewById(R.id.titleName);
            txtName = itemView.findViewById(R.id.txtName);
            txtSubject = itemView.findViewById(R.id.txtSubject);
            txtNumberSession = itemView.findViewById(R.id.txtNumberSession);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtMonday = itemView.findViewById(R.id.txtMonday);
            txtTuesday = itemView.findViewById(R.id.txtTuesday);
            txtWednesday = itemView.findViewById(R.id.txtWednesday);
            txtThursday = itemView.findViewById(R.id.txtThursday);
            txtFriday = itemView.findViewById(R.id.txtFriday);
            txtSaturday = itemView.findViewById(R.id.txtSaturday);
            txtSunday = itemView.findViewById(R.id.txtSunday);
            llItemMySchedule = itemView.findViewById(R.id.llItemMySchedule);
        }
    }
}
