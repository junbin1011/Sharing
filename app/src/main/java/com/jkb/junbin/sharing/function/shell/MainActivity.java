package com.jkb.junbin.sharing.function.shell;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.jkb.junbin.sharing.function.shell.adapter.SectionsPagerAdapter;
import com.jkb.junbin.sharing.feature.account.AccountFragment;
import com.jkb.junbin.sharing.feature.file.FileFragment;
import com.jkb.junbin.sharing.feature.message.MessageFragment;
import com.sharing.R;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvAdd;
    MessageFragment messageFragment;
    private FileFragment fileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvAdd = findViewById(R.id.tv_add);
        List<Fragment> fragments = new ArrayList<>();
        messageFragment = MessageFragment.newInstance();
        fragments.add(messageFragment);
        fileFragment = FileFragment.newInstance();
        fragments.add(fileFragment);
        fragments.add(AccountFragment.newInstance());
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
                        messageFragment.uploadDynamic();
                    }
                    if (position == 1) {
                        fileFragment.uploadFile();
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tvAdd.setOnClickListener(view -> {
            messageFragment.uploadDynamic();
        });
    }
}