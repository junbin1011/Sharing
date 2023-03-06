package com.jkb.junbin.sharing.function.shell;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.jkb.junbin.sharing.function.shell.adapter.SectionsPagerAdapter;
import com.jkb.junbin.sharing.function.shell.interfaces.IFileAddClickListener;
import com.jkb.junbin.sharing.function.shell.interfaces.IMessageAddClickListener;


import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvAdd;
    private IFileAddClickListener iFileAddClickListener;
    private IMessageAddClickListener iMessageAddClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvAdd = findViewById(R.id.tv_add);
        List<Fragment> fragments = new ArrayList<>();
        try {
            fragments.add((Fragment) Class.forName("com.jkb.junbin.sharing.feature.message.MessageFragment").newInstance());
            fragments.add((Fragment) Class.forName("com.jkb.junbin.sharing.feature.file.FileFragment").newInstance());
            fragments.add((Fragment) Class.forName("com.jkb.junbin.sharing.feature.account.AccountFragment").newInstance());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), fragments);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 2) {
                    tvAdd.setVisibility(View.GONE);
                } else {
                    tvAdd.setVisibility(View.VISIBLE);
                }
                tvAdd.setOnClickListener(view -> {
                    if (position == 0) {
                        if (iMessageAddClickListener != null) {
                            iMessageAddClickListener.onClick();
                        }
                    }
                    if (position == 1) {
                        if (iFileAddClickListener != null) {
                            iFileAddClickListener.onClick();
                        }
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tvAdd.setOnClickListener(view -> {
            if (iMessageAddClickListener != null) {
                iMessageAddClickListener.onClick();
            }
        });
    }

    public void setMessageAddClickListener(IMessageAddClickListener iMessageAddClickListener) {
        this.iMessageAddClickListener = iMessageAddClickListener;
    }

    public void setFileAddClickListener(IFileAddClickListener iFileAddClickListener) {
        this.iFileAddClickListener = iFileAddClickListener;
    }
}