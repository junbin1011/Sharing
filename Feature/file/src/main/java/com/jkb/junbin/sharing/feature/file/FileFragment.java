package com.jkb.junbin.sharing.feature.file;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jkb.junbin.sharing.function.shell.MainActivity;
import com.jkb.junbin.sharing.function.shell.interfaces.IAccountState;
import com.jkb.junbin.sharing.function.transfer.FileInfo;
import com.jkb.junbin.sharing.function.transfer.FileTransfer;

import java.util.List;

@Route(path = "/fileFeature/file")
public class FileFragment extends Fragment implements FileListContract.FileView {
    private RecyclerView fileListRecycleView;
    private TextView tvMessage;
    private FileListContract.FilePresenter filePresenter = new FilePresenterImpl(new FileRemoteDataSource(),this);
    @Autowired
    IAccountState iAccountState;

    public static FileListContract.FileView newInstance() {
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
        tvMessage.setOnClickListener(v -> filePresenter.getFileList());
        if(getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).setFileAddClickListener(() -> uploadFile());
        }
        filePresenter.getFileList();
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
    }

    public void uploadFile() {
        //上传文件
        FileInfo fileInfo = new FileTransfer(iAccountState.isLogin()).upload(iAccountState.getUsername(), "/data/data/user.png");
        if (fileInfo != null) {
            FileListAdapter fileListAdapter = (FileListAdapter) fileListRecycleView.getAdapter();
            if (fileListAdapter != null) {
                fileListAdapter.infoList.add(0, fileInfo);
                fileListAdapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    public void showFileList(Object fileList) {
        showTip(false);
        //显示网络数据
        List<FileInfo> infoList = (List<FileInfo>) fileList;
        FileListAdapter fileListAdapter = new FileListAdapter(infoList, getActivity());
        fileListRecycleView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL));
        //设置布局显示格式
        fileListRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fileListRecycleView.setAdapter(fileListAdapter);
    }

    @Override
    public void showNetWorkException(String msg) {
        showTip(true);
        //显示异常提醒数据
        tvMessage.setText(msg);
    }

    @Override
    public void showEmptyData() {
        showNetWorkException("empty data");
    }

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