package com.jkb.junbin.sharing.function.shell;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
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
    List<Integer> tabTitles = new ArrayList<>();
    private int mPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvAdd = findViewById(R.id.tv_add);
        List<Fragment> fragments = getFragmentList(tabTitles);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), fragments, tabTitles);
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
                mPosition =position;
                if (currentPositionIsFile(position) || currentPositionIsMessage(position)) {
                    tvAdd.setVisibility(View.VISIBLE);
                } else {
                    tvAdd.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tvAdd.setOnClickListener(view -> {
            if (currentPositionIsMessage(mPosition)) {
                if (iMessageAddClickListener != null) {
                    iMessageAddClickListener.onClick();
                }
            }
            if (currentPositionIsFile(mPosition)) {
                if (iFileAddClickListener != null) {
                    iFileAddClickListener.onClick();
                }
            }
        });
    }

    @NonNull
    private List<Fragment> getFragmentList(List<Integer> tabTitles) {
        List<Fragment> fragments = new ArrayList<>();
        Fragment messageFragment = (Fragment) ARouter.getInstance().build("/messageFeature/message").navigation();
        if (messageFragment != null) {
            fragments.add(messageFragment);
            tabTitles.add(R.string.tab_message);
        }
        Fragment fileFragment = (Fragment) ARouter.getInstance().build("/fileFeature/file").navigation();
        if (fileFragment != null) {
            fragments.add(fileFragment);
            tabTitles.add(R.string.tab_file);
        }
        Fragment accountFragment = (Fragment) ARouter.getInstance().build("/accountFeature/account").navigation();
        if (accountFragment != null) {
            fragments.add(accountFragment);
            tabTitles.add(R.string.tab_user);
        }
        return fragments;
    }

    private boolean currentPositionIsFile(int position) {
        if (tabTitles.get(position) == R.string.tab_file) {
            return true;
        } else {
            return false;
        }
    }

    private boolean currentPositionIsMessage(int position) {
        if (tabTitles.get(position) == R.string.tab_message) {
            return true;
        } else {
            return false;
        }
    }

    public void setMessageAddClickListener(IMessageAddClickListener iMessageAddClickListener) {
        this.iMessageAddClickListener = iMessageAddClickListener;
    }

    public void setFileAddClickListener(IFileAddClickListener iFileAddClickListener) {
        this.iFileAddClickListener = iFileAddClickListener;
    }
}