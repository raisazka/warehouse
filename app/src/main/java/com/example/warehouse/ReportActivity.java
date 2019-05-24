package com.example.warehouse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.warehouse.Adapter.ViewPagerAdapter;
import com.example.warehouse.Fragment.MonthlyReportFragment;
import com.example.warehouse.Fragment.StockInReportFragment;
import com.example.warehouse.Fragment.StockOutReportFragment;
import com.example.warehouse.Fragment.WeeklyReportFragment;


public class ReportActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MonthlyReportFragment(), "Monthly Report");
        adapter.addFragment(new WeeklyReportFragment(), "Weekly Report");
        adapter.addFragment(new StockInReportFragment(), "Stock In Report");
        adapter.addFragment(new StockOutReportFragment(), "Stock Out Report");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
