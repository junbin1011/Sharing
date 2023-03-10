package com.jkb.junbin.sharing.feature.message;

import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jkb.junbin.sharing.function.shell.interfaces.IAccountState;
import com.jkb.junbin.sharing.function.transfer.FileInfo;
import com.jkb.junbin.sharing.function.shell.MainActivity;
import com.jkb.junbin.sharing.function.transfer.FileTransfer;

import java.util.List;

@Route(path = "/messageFeature/message")
public class MessageFragment extends Fragment {

    MessageController messageController;
    private RecyclerView dynamicListRecycleView;
    private TextView tvMessage;

    @Autowired
    IAccountState iAccountState;

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        messageController = new MessageController(getActivity());
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        dynamicListRecycleView = view.findViewById(R.id.file_list);
        tvMessage = view.findViewById(R.id.tv_message);
        tvMessage.setOnClickListener(v -> getDynamicList());
        getDynamicList();
        ((MainActivity) getActivity()).setMessageAddClickListener(() -> uploadDynamic());
        return view;
    }

    public void uploadDynamic() {
        //????????????
        FileInfo fileInfo = new FileTransfer(iAccountState.isLogin()).upload(iAccountState.getUsername(), "/data/data/user.png");
        Message message = new Message(0, iAccountState.getUsername() + "?????????????????????.", fileInfo.fileName, System.currentTimeMillis());
        boolean success = messageController.post(message, fileInfo);
        if (success) {
            MessageListAdapter messageListAdapter = (MessageListAdapter) dynamicListRecycleView.getAdapter();
            if (messageListAdapter != null) {
                messageListAdapter.infoList.add(0, message);
                //????????????
                messageController.saveMessageToCache(messageListAdapter.infoList);
                messageListAdapter.notifyDataSetChanged();
            }
        }
    }

    public void getDynamicList() {
        new Thread(() -> {
            android.os.Message message = new android.os.Message();
            try {
                List<Message> messageList = messageController.getMessageList();
                message.what = 1;
                message.obj = messageList;
            } catch (NetworkErrorException e) {
                message.what = 0;
                message.obj = "?????????????????????????????????";
                e.printStackTrace();
            }
            mHandler.sendMessage(message);
        }).start();
    }

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull android.os.Message msg) {
            if (msg.what == 1) {
                showTip(false);
                //??????????????????
                List<Message> messageList = (List<Message>) msg.obj;
                if (messageList == null || messageList.size() == 0) {
                    showTip(true);
                    //???????????????
                    tvMessage.setText("?????????????????????????????????");

                } else {
                    MessageListAdapter fileListAdapter = new MessageListAdapter(messageList, getActivity());
                    dynamicListRecycleView.addItemDecoration(new DividerItemDecoration(
                            getActivity(), DividerItemDecoration.VERTICAL));
                    //????????????????????????
                    dynamicListRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    dynamicListRecycleView.setAdapter(fileListAdapter);
                    //????????????????????????????????????????????????
                    messageController.saveMessageToCache(messageList);
                }
            } else if (msg.what == 0) {
                //??????????????????????????????
                List<Message> messageList = messageController.getMessageListFromCache();
                if (messageList == null || messageList.size() == 0) {
                    showTip(true);
                    //????????????????????????
                    tvMessage.setText(msg.obj.toString());
                } else {
                    MessageListAdapter fileListAdapter = new MessageListAdapter(messageList, getActivity());
                    dynamicListRecycleView.addItemDecoration(new DividerItemDecoration(
                            getActivity(), DividerItemDecoration.VERTICAL));
                    //????????????????????????
                    dynamicListRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    dynamicListRecycleView.setAdapter(fileListAdapter);
                }

            }
            return false;
        }
    });

    public void showTip(boolean show) {
        if (show) {
            tvMessage.setVisibility(View.VISIBLE);
            dynamicListRecycleView.setVisibility(View.GONE);
        } else {
            tvMessage.setVisibility(View.GONE);
            dynamicListRecycleView.setVisibility(View.VISIBLE);
        }
    }
}