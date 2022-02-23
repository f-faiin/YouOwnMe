package com.jnu.youownme;

import android.os.Bundle;
import com.haibin.calendarview.Calendar;
import androidx.fragment.app.FragmentActivity;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.widget.TextView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    Calendar calendar=new Calendar();
    private int position=0;
    private TextView time;
    private OutListFragment outListFragment;
    private CalendarFragment calendarFragment;
    private InListFragment inListFragment;
    FragmentManager fragmentManager;
    private List<View> bottomTabs;

    private View outlistLayout;
    private View calendarLayout;
    private View inlistLayout;

    private ImageView calendarImage;
    private TextView outlistText;
    private TextView inlistText;


    private void initViews() {
        outlistLayout = findViewById(R.id.outlist_layout);
        calendarLayout = findViewById(R.id.calendar_layout);
        inlistLayout = findViewById(R.id.inlist_layout);

        calendarLayout = (ImageView) findViewById(R.id.calendar_image);
        outlistLayout = (TextView) findViewById(R.id.outlist_text);
        inlistLayout = (TextView) findViewById(R.id.inlist_text);

        outlistLayout.setOnClickListener(this);
        calendarLayout.setOnClickListener(this);
        inlistLayout.setOnClickListener(this);

        bottomTabs = new ArrayList<View>(3);

        bottomTabs.add(outlistLayout);
        bottomTabs.add(calendarImage);
        bottomTabs.add(inlistLayout);

    }

    private void setSelectTab(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (outListFragment == null) {
                    outListFragment = new OutListFragment();
                    transaction.add(R.id.content, outListFragment);
                } else {
                    transaction.show(outListFragment);
                }
                break;

            case 1:
                if (calendarFragment == null) {
                    calendarFragment = new CalendarFragment();
                    transaction.add(R.id.content, calendarFragment);
                } else {
                    transaction.show(calendarFragment);
                }
                break;
            case 2:
                if (inListFragment == null) {
                    inListFragment = new InListFragment();
                    transaction.add(R.id.content, inListFragment);
                } else {
                    transaction.show(inListFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }
    //@SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }
        setContentView(R.layout.activity_main);

        initViews();
        fragmentManager = getSupportFragmentManager();
        setSelectTab(1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.outlist_text:
                setSelectTab(0);
                break;
            case R.id.calendar_image:
                setSelectTab(1);
                break;
            case R.id.inlist_text:
                setSelectTab(2);
                break;
            default:
                break;
        }
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (outListFragment != null) {
            transaction.hide(outListFragment);
        }
        if (calendarFragment != null) {
            transaction.hide(calendarFragment);
        }
        if (inListFragment != null) {
            transaction.hide(inListFragment);
        }
    }

    public void indata(String person_name,String in_number,String in_reason,String date) {
        inListFragment.indatabank(person_name,in_number,in_reason,date);
    }

    public void outdata(String person_name,String out_number,String out_reason,String date) {
        outListFragment.outdatabank(person_name,out_number,out_reason,date);
    }
}
