package com.jnu.youownme;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;


public class CalendarFragment extends Fragment {

    Calendar calendar=new Calendar();
    String person_name,in_number,in_reason,date;
    String out_number,out_reason;
    public static final int RESULT_OK_IN = 101;
    public static final int RESULT_OK_OUT = 100;
    private CalendarView calendarView;

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_calendar, container, false);

        //获取点击当天的时间，但是接口还未配置好
        //View time_layout = findViewById(R.id.day_text);
        //TextView time_layout;
        //timeutils.setCalendarTime(calendar,day.getTargetDate());
        //time_layout = view.findViewById(R.id.day_text);
        //time_layout.setText(timeutils.getFormatDate(getString(R.string.current_day_format), calendar.getTimeInMillis()));

        //实现那个悬浮的加号功能，跳转到addActivity
        FloatingActionButton fab = view.findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),AddActivity.class);
                startActivityForResult(intent,1);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK_IN) {
            person_name = data.getStringExtra("person_name");
            in_number = data.getStringExtra("in_number");
            in_reason = data.getStringExtra("in_reason");
            date = data.getStringExtra("date");
            ((MainActivity) getActivity()).indata(person_name,in_number,in_reason,date);
        }

        if (resultCode == RESULT_OK_OUT) {
            person_name = data.getStringExtra("person_name");
            out_number = data.getStringExtra("out_number");
            out_reason = data.getStringExtra("out_reason");
            date = data.getStringExtra("date");
            ((MainActivity) getActivity()).outdata(person_name,out_number,out_reason,date);
        }
    }
}
