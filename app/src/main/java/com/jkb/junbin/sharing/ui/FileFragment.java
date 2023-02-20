package com.jkb.junbin.sharing.ui;

import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jkb.junbin.sharing.adapter.FileListAdapter;
import com.jkb.junbin.sharing.controller.FileController;
import com.jkb.junbin.sharing.model.FileInfo;
import com.sharing.R;

import java.util.List;


public class FileFragment extends Fragment {

    FileController fileController = new FileController();
    private RecyclerView fileListRecycleView;
    private TextView tvMessage;

    public static FileFragment newInstance() {
        FileFragment fragment = new FileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_file, container, false);
        fileListRecycleView = view.findViewById(R.id.file_list);
        tvMessage = view.findViewById(R.id.tv_message);
        tvMessage.setOnClickListener(v -> getFileList());
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFileList();
    }

    public void uploadFile() {
        //上传文件
        FileInfo fileInfo = fileController.upload("/data/data/user.png");
        if (fileInfo != null) {
            FileListAdapter fileListAdapter = (FileListAdapter) fileListRecycleView.getAdapter();
            if (fileListAdapter != null) {
                fileListAdapter.infoList.add(0, fileInfo);
                fileListAdapter.notifyDataSetChanged();
            }
        }
    }


    private void getFileList() {
        new Thread(() -> {
            Message message = new Message();
            try {
                List<FileInfo> infoList = fileController.getFileList();
                message.what = 1;
                message.obj = infoList;
            } catch (NetworkErrorException e) {
                message.what = 0;
                message.obj = "网络异常，请点击重试。";
                e.printStackTrace();
            }
            mHandler.sendMessage(message);
        }).start();
    }

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                showTip(false);
                //显示网络数据
                List<FileInfo> infoList = (List<FileInfo>) msg.obj;
                FileListAdapter fileListAdapter = new FileListAdapter(infoList, getActivity());
                fileListRecycleView.addItemDecoration(new DividerItemDecoration(
                        getActivity(), DividerItemDecoration.VERTICAL));
                //设置布局显示格式
                fileListRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
                fileListRecycleView.setAdapter(fileListAdapter);
            } else if (msg.what == 0) {
                showTip(true);
                //显示异常提醒数据
                tvMessage.setText(msg.obj.toString());
            } else {
                showTip(true);
                //显示空数据
                tvMessage.setText("没有数据，请点击重试。");
            }
            return false;
        }
    });

    public void showTip(boolean show) {
        if (show) {
            tvMessage.setVisibility(View.VISIBLE);
            fileListRecycleView.setVisibility(View.GONE);
        } else {
            tvMessage.setVisibility(View.GONE);
            fileListRecycleView.setVisibility(View.VISIBLE);
        }
    }
}